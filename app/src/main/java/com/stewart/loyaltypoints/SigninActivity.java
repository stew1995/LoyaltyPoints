package com.stewart.loyaltypoints;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.visible;
import static com.google.android.gms.analytics.internal.zzy.o;
import static com.google.android.gms.analytics.internal.zzy.s;
import static com.google.android.gms.analytics.internal.zzy.v;
import static com.stewart.loyaltypoints.R.color.white;
import static com.stewart.loyaltypoints.R.id.text;

public class SigninActivity extends AppCompatActivity {
    //Google Sign in
    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "GoogleSignin";
    //Firebase

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Normal Email Sign In
    //Button
    private Button mLoginButton, mRegister;
    private EditText mEmail, mPassword;
    private TextView mPasswordChecker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Assigning the vairables
        mEmail = (EditText) findViewById( R.id.etEmailSignIn );
        mPassword = (EditText) findViewById( R.id.etPasswordSignIn );
        mLoginButton = (Button) findViewById( R.id.btnLogIn );
        mRegister = (Button) findViewById( R.id.btnCreateAccount );
        mPasswordChecker = (TextView) findViewById( R.id.tvPasswordChecker );

        //TextWatcher
        mPassword.addTextChangedListener(textWatcher);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(SigninActivity.this, NavigationActivity.class));
                }

            }
        };

        mLoginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();

            }
        } );

        mRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        } );

        //Google
        /*
        mGoogleBtn = (SignInButton) findViewById(R.id.googleButton);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SigninActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

       mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
*/


    }

    //TextWatcher for password to tell user if password is too short or if it needs certain characters
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mPasswordChecker.setText( "Not Entered" );
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //For special symbols to check agaisnt
            String char1 = "!";
            String char2 = "@";
            String char3 = "#";
            String char4 = "$";
            String char5 = "%";
            String char6 = "*";
            String text = mPassword.getText().toString();

            if(s.length() <= 6) {
                mPasswordChecker.setText( "Password not long enough" );
                mPasswordChecker.setVisibility( View.VISIBLE );
                mPasswordChecker.setTextColor(getResources().getColor( R.color.passwordError ));
            } else if (s.length() <= 8) {
                mPasswordChecker.setText( "Weak" );
                mPasswordChecker.setVisibility( View.VISIBLE );
                mPasswordChecker.setTextColor( getResources().getColor( R.color.passwordWeak ) );
            } else if (s.length() <= 10) {
                mPasswordChecker.setText( "Medium" );
                mPasswordChecker.setVisibility( View.VISIBLE );
                mPasswordChecker.setTextColor( getResources().getColor( R.color.passwordMedium )
                );
            } else if (s.length() <= 14) {
                mPasswordChecker.setText( "Strong" );
                mPasswordChecker.setVisibility( View.VISIBLE );
                mPasswordChecker.setTextColor( getResources().getColor( R.color.passwordStrong ) );
            } else {
                mPasswordChecker.setText( "Very Strong" );
                mPasswordChecker.setVisibility( View.VISIBLE );
                mPasswordChecker.setTextColor( getResources().getColor( R.color.passwordVeryStrong ) );
            }
            //Needs all of it to be put in, needs looking over to see why it does that
            if (!text.contains (char1)||!text.contains( char2 )||!text.contains( char3 )||
                    !text.contains (char4)||!text.contains( char5 )||!text.contains( char6 )) {
                mPasswordChecker.setText( "Special symbols required !@#$%*" );
            }
        }
    };

    private void startRegister() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty( email )) {
            Toast.makeText( SigninActivity.this, "Email field is required", Toast.LENGTH_LONG ).show();
            return;
        }
        if (TextUtils.isEmpty( password )) {
            Toast.makeText( SigninActivity.this, "Password field is required", Toast.LENGTH_LONG ).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getApplicationContext(), DetailsActivity.class));

                        } else {
                            Toast.makeText(SigninActivity.this, "Could not regsiter.. please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startSignIn() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty( email ) || TextUtils.isEmpty( password )) {
            Toast.makeText( SigninActivity.this, "Fields are empty", Toast.LENGTH_LONG ).show();

        } else {
            mAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        //Shows error
                        Toast.makeText( SigninActivity.this, "Sign In Failed", Toast.LENGTH_LONG ).show();
                    }
                }
            } );
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //Password validator
    //One provided only needs the user to have more than 6 characters so improved security
    private boolean isPasswordValid(String password) {
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%*]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //email valiator
    //doesnt work needs pattern looking over
    private boolean isEmailValid(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[myport.ac.uk]";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Google
    /*
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    */

}
