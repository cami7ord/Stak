package com.user.stak.stak.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.user.stak.stak.R;
import com.user.stak.stak.util.NavigationUtils;

import static android.Manifest.permission.READ_CONTACTS;

public class RegistrationActivity extends LoginActivity {

    private static final String TAG = "RegistrationActivity";

    // UI references.
    private EditText mNameView;
    private EditText mLastView;
    private EditText mEmailView;
    private EditText mPasswordView;

    // Dependencies.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mNameView = findViewById(R.id.registration_name);
        mLastView = findViewById(R.id.registration_last);
        mEmailView = findViewById(R.id.registration_email);
        mPasswordView = findViewById(R.id.registration_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        Button mRegistrationButton = findViewById(R.id.registration_button);
        mRegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void attemptRegistration() {
        if(validateFields()) {
            createUser(mEmailView.getText().toString(),
                    mPasswordView.getText().toString());
        }
    }

    private boolean validateFields() {
        // Reset errors.
        mNameView.setError(null);
        mLastView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String last = mLastView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(last)) {
            mLastView.setError(getString(R.string.error_field_required));
            focusView = mLastView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void createUser(final String email, final String password) {
        showProgress(true);
        mAuth.createUserWithEmailAndPassword(email,
                password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    updateUserInfo();
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void updateUserInfo() {

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(mNameView.getText().toString() + " " +
                            mLastView.getText().toString())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                                NavigationUtils.startApp(RegistrationActivity.this);
                            }
                        }
                    });
        }
    }

}
