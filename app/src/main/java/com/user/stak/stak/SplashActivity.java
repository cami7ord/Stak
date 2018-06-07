package com.user.stak.stak;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.appsee.Appsee;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.user.stak.stak.util.NavigationUtils;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1230;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Appsee.start(BuildConfig.APPSEE_KEY);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            NavigationUtils.startApp(this);
        } else {

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG , true)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    //new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    //new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.FacebookBuilder().build()))
                            .build(),
                    RC_SIGN_IN);

        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //startActivity(SignedInActivity.createIntent(this, response));
                NavigationUtils.startApp(this);
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar("Cancelled");
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("No Internet connection");
                    return;
                }

                showSnackbar("Unknown error");
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    private void showSnackbar(int string) {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.splash_layout),
                string, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    private void showSnackbar(String string) {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.splash_layout),
                string, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

}
