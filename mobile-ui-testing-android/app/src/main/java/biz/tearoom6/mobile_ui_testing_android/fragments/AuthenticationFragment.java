package biz.tearoom6.mobile_ui_testing_android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import biz.tearoom6.mobile_ui_testing_android.R;

@EFragment
public class AuthenticationFragment extends BaseFragment {

    private enum State {
        LOGIN,
        REGISTER,
        STATUS,
    }

    private static final String TAG = AuthenticationFragment.class.getSimpleName();

    private static final int MIN_PASSWORD_LENGTH = 6;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // Layouts
    @ViewById(R.id.auth_linear_login)
    LinearLayout layerLogin;
    @ViewById(R.id.auth_linear_register)
    LinearLayout layerRegister;
    @ViewById(R.id.auth_linear_overall)
    LinearLayout layerStatus;

    // Login
    @ViewById(R.id.auth_input_loginEmail)
    EditText mLoginEmail;
    @ViewById(R.id.auth_input_loginPassword)
    EditText mLoginPassword;
    @ViewById(R.id.auth_label_loginErrors)
    TextView mLoginErr;

    // Status
    @ViewById(R.id.auth_label_nickname)
    TextView mTxtNickName;
    @ViewById(R.id.auth_label_email)
    TextView mTxtEmail;

    // Signup
    @ViewById(R.id.auth_input_registerNickname)
    EditText mSignupNickname;
    @ViewById(R.id.auth_input_registerEmail)
    EditText mSignupEmail;
    @ViewById(R.id.auth_input_registerPassword)
    EditText mSignupPassword;
    @ViewById(R.id.auth_label_registerErrors)
    TextView mSignupErr;

    // Validation error messages
    @StringRes(R.string.error_required)
    String strEmpty;
    @StringRes(R.string.invalid_email)
    String strInvalidEmail;
    @StringRes(R.string.invalid_password)
    String strInvalidPwd;

    // Error messages
    String errorUsedEmail;
    String errorFailedLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final ViewGroup nullParent = null;
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_authentication, nullParent);

        configureFirebase();
        loadErrorMessages();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        hideProgressDialog();
    }

    @Click(R.id.auth_button_openRegister)
    void goRegister() {
        layerLogin.setVisibility(View.INVISIBLE);
        layerRegister.setVisibility(View.VISIBLE);
    }

    @Click(R.id.auth_button_openLogin)
    void goLogin() {
        layerLogin.setVisibility(View.VISIBLE);
        layerRegister.setVisibility(View.INVISIBLE);
    }

    @Click(R.id.auth_button_login)
    void doLogin() {
        final String email = mLoginEmail.getText().toString();
        final String password = mLoginPassword.getText().toString();

        if (!validateLoginForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        clearLoginForm();
                        updateView(State.STATUS);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLoginErr.setText(getLoginErrorMessage(e));
                    }
                });
    }

    @Click(R.id.auth_button_logout)
    void doLogout() {
        mAuth.signOut();
        updateView(State.LOGIN);
    }

    @Click(R.id.auth_button_register)
    void doRegister(View v) {
        hideKeyboard(v);

        if (!validateRegisterForm()) {
            return;
        }

        final String nickname = mSignupNickname.getText().toString();
        final String email    = mSignupEmail.getText().toString();
        final String password = mSignupPassword.getText().toString();

        mSignupErr.setText(null);
        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        clearRegisterForm();
                        updateNickname(authResult.getUser(), nickname); // This results to hide progress dialog.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        mSignupErr.setText(getRegisterErrorMessage(e));
                    }
                });
    }

    private void configureFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    showUserInfo(user);
                    updateView(State.STATUS);
                } else {
                    updateView(State.LOGIN);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void loadErrorMessages() {
        errorUsedEmail = getResources().getString(R.string.error_used_email);
        errorFailedLogin = getResources().getString(R.string.failed_login);
    }

    private void updateNickname(final FirebaseUser user, String nickname) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .build();

        user.updateProfile(request)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "updateNickname failed.", task.getException());
                        }
                        updateView(State.STATUS);
                        showUserInfo(user);
                        hideProgressDialog();
                    }
                });
    }

    private void clearLoginForm() {
        mLoginEmail.setText(null);
        mLoginPassword.setText(null);
        mLoginErr.setText("");
    }

    private void clearRegisterForm() {
        mSignupNickname.setText(null);
        mSignupEmail.setText(null);
        mSignupPassword.setText(null);
        mSignupErr.setText(null);
    }

    private void updateView(State state) {
        layerStatus.setVisibility(View.INVISIBLE);
        layerLogin.setVisibility(View.INVISIBLE);
        layerRegister.setVisibility(View.INVISIBLE);

        LinearLayout visibleLayer = null;

        switch (state) {
            case LOGIN:
                visibleLayer = layerLogin;
                break;
            case REGISTER:
                visibleLayer = layerRegister;
                break;
            case STATUS:
                visibleLayer = layerStatus;
                break;
            default:
                throw new IllegalStateException("");
        }

        visibleLayer.setVisibility(View.VISIBLE);
    }

    private boolean validateLoginForm() {
        final EditText[] requiredTexts = new EditText[] { mLoginEmail, mLoginPassword };

        // Clear errors.
        for (EditText editText : requiredTexts) {
            editText.setError(null);
        }

        // Check required.
        for (EditText editText : requiredTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError(strEmpty);
                return false;
            }
        }

        boolean valid = true;

        // Check email.
        if (!isEmailValid(mLoginEmail.getText())) {
            mLoginEmail.setError(strInvalidEmail);
            valid = false;
        }

        // Check password.
        if (!isPasswordValid(mLoginPassword.getText())) {
            mLoginPassword.setError(strInvalidPwd);
            valid = false;
        }

        return valid;
    }

    private boolean validateRegisterForm() {
        final EditText[] requiredTexts = new EditText[] { mSignupNickname, mSignupEmail, mSignupPassword };

        // Clear errors.
        for (EditText editText : requiredTexts) {
            editText.setError(null);
        }

        // Check required.
        for (EditText editText : requiredTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError(strEmpty);
                return false;
            }
        }

        boolean valid = true;

        // Check email.
        if (!isEmailValid(mSignupEmail.getText())) {
            mSignupEmail.setError(strInvalidEmail);
            valid = false;
        }

        // Check password.
        if (!isPasswordValid(mSignupPassword.getText())) {
            mSignupPassword.setError(strInvalidPwd);
            valid = false;
        }

        return valid;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(CharSequence password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    /**
     * Parse Firebase error to app error message.
     *
     * @param e Firebase error
     * @return app error message or null
     */
    private String getFirebaseErrorMessage(Exception e) {
        if (e instanceof FirebaseAuthException) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                return errorFailedLogin;
            } else if (e instanceof FirebaseAuthInvalidUserException) {
                return errorFailedLogin;
            } else if (e instanceof FirebaseAuthUserCollisionException) {
                return errorUsedEmail;
            }
        }
        return null;
    }

    private String getLoginErrorMessage(Exception e) {
        String errorMessage = getFirebaseErrorMessage(e);
        return (errorMessage != null)
                ? errorMessage
                : getResources().getString(R.string.error_other_login_error);
    }

    private String getRegisterErrorMessage(Exception e) {
        String errorMessage = getFirebaseErrorMessage(e);
        return (errorMessage != null)
                ? errorMessage
                : getResources().getString(R.string.error_other_register_error);
    }

    private void showUserInfo(FirebaseUser user) {
        mTxtNickName.setText(user.getDisplayName());
        mTxtEmail.setText(user.getEmail());
    }
}
