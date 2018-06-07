package com.user.stak.stak.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.util.ui.ImeHelper;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.user.stak.stak.R;
import com.user.stak.stak.util.SignInResultNotifier;

public class ChatActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private static final String TAG = "ChatActivity";

    private static final CollectionReference sChatCollection =
            FirebaseFirestore.getInstance().collection("chats");

    /** Get the last 50 chat messages ordered by timestamp . */
    private static Query sChatQuery;

    private RecyclerView mRecyclerView;
    private Button mSendButton;
    private EditText mMessageEdit;
    private TextView mEmptyListMessage;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRecyclerView = (RecyclerView) findViewById(R.id.messagesList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendClick();
            }
        });

        mEmptyListMessage = (TextView) findViewById(R.id.emptyTextView);

        mMessageEdit = (EditText) findViewById(R.id.messageEdit);
        mMessageEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onSendClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        sChatQuery = sChatCollection.document(mCurrentUser.getEmail())
                .collection("messages").orderBy("timestamp").limit(50);
        if (isSignedIn()) { attachRecyclerViewAdapter(); }
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        mSendButton.setEnabled(isSignedIn());
        mMessageEdit.setEnabled(isSignedIn());

        if (isSignedIn()) {
            attachRecyclerViewAdapter();
        } else {
            Toast.makeText(this, R.string.action_sign_in, Toast.LENGTH_SHORT).show();
            auth.signInAnonymously().addOnCompleteListener(new SignInResultNotifier(this));
        }
    }

    private boolean isSignedIn() {
        return mCurrentUser != null;
    }

    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newAdapter();

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        mRecyclerView.setAdapter(adapter);
    }

    public void onSendClick() {

        String uid = mCurrentUser.getUid();
        String name = mCurrentUser.getDisplayName();

        onAddMessage(new Chat(name, mMessageEdit.getText().toString(), uid));

        mMessageEdit.setText("");
    }

    protected RecyclerView.Adapter newAdapter() {
        FirestoreRecyclerOptions<Chat> options =
                new FirestoreRecyclerOptions.Builder<Chat>()
                        .setQuery(sChatQuery, Chat.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirestoreRecyclerAdapter<Chat, ChatHolder>(options) {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ChatHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull Chat model) {
                holder.bind(model);
            }

            @Override
            public void onDataChanged() {
                // If there are no chat messages, show a view that invites the user to add a message.
                mEmptyListMessage.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        };
    }

    protected void onAddMessage(Chat chat) {
        if(mCurrentUser != null) {
            sChatCollection.document(mCurrentUser.getEmail()).collection("messages").add(chat).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Failed to write message", e);
                }
            });
        }
    }
}
