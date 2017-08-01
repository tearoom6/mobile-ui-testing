package biz.tearoom6.mobile_ui_testing_android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import biz.tearoom6.mobile_ui_testing_android.MainActivity;
import biz.tearoom6.mobile_ui_testing_android.models.MainPage;
import biz.tearoom6.mobile_ui_testing_android.models.Note;
import biz.tearoom6.mobile_ui_testing_android.adapters.NotesAdapter;
import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.widgets.EditTextWithIcon;
import biz.tearoom6.mobile_ui_testing_android.widgets.TextInputLayoutAlignRight;

@EFragment
public class NotesFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private enum State {
        NOT_LOGINED,
        LOGINED,
    }

    private enum FormState {
        CREATE,
        VIEW,
        CLOSE,
    }

    private static final String NOTES_DB_NAMESPACE = "notes";

    private MainActivity mParentActivity;
    private NotesAdapter mNotesAdapter;
    private int mPosition;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // Not Login
    @ViewById(R.id.notes_linear_unauthorized)
    LinearLayout layerUnauthorized;

    // Login
    @ViewById(R.id.notes_linear_authorized)
    LinearLayout layerAuthorized;
    @ViewById(R.id.notes_include_note)
    View layer_note;
    @ViewById(R.id.notes_list_title)
    ListView listNotes;
    @ViewById(R.id.note_inputLayout_title)
    TextInputLayoutAlignRight inputLayoutTitle;
    @ViewById(R.id.note_input_title)
    EditTextWithIcon editTitle;
    @ViewById(R.id.note_input_content)
    EditText editContent;
    @ViewById(R.id.notes_button_create)
    Button btnCreate;
    @ViewById(R.id.note_button_save)
    Button btnSave;

    // Strings
    @StringRes(R.string.error_required)
    String strEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentActivity = (MainActivity) getActivity();
        View view = mParentActivity.getLayoutInflater().inflate(R.layout.fragment_notes, null);
        mParentActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        configureFirebase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        editTitle.addTextChangedListener(new EditTextWithIcon.GenericTextWatcher(editTitle));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                updateView(State.LOGINED);
            } else {
                updateView(State.NOT_LOGINED);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note note = (Note) parent.getItemAtPosition(position);
        mPosition = parent.getCount() - 1 - position;
        setFormState(FormState.VIEW);
        editTitle.setText(note.getTitle());
        editContent.setText(note.getContent());
    }

    @Click(R.id.notes_button_redirectToLogin)
    void goLogin() {
        mParentActivity.changePage(MainPage.USER);
    }

    @Click(R.id.notes_button_create)
    void createNote() {
        setFormState(FormState.CREATE);
    }

    @Click(R.id.note_button_close)
    void closeNote(View v) {
        setFormState(FormState.CLOSE);
    }

    @Click(R.id.note_button_clear)
    void clearNote(View v) {
        editTitle.setText(null);
        editContent.setText(null);
    }

    @Click(R.id.note_button_save)
    void saveNote(View v) {
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        hideKeyboard(v);
        if (!validateForm()) {
            return;
        }

        mNotesAdapter.addNote(title, content);

        setFormState(FormState.CLOSE);
    }

    private void configureFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    updateView(State.LOGINED);

                    mNotesAdapter = new NotesAdapter(getRefDB(), getActivity());
                    listNotes.setAdapter(mNotesAdapter);
                    listNotes.setOnItemClickListener(NotesFragment.this);
                } else {
                    updateView(State.NOT_LOGINED);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void updateView(State state) {
        layerAuthorized.setVisibility(View.INVISIBLE);
        layerUnauthorized.setVisibility(View.INVISIBLE);

        switch (state) {
            case NOT_LOGINED:
                layerUnauthorized.setVisibility(View.VISIBLE);
                break;

            case LOGINED:
                layerAuthorized.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setFormState(FormState action) {
        switch (action) {
            case CREATE:
                layer_note.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                break;

            case VIEW:
                layer_note.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);
                btnSave.setVisibility(View.INVISIBLE);
                break;

            case CLOSE:
                layer_note.setVisibility(View.GONE);
                btnCreate.setVisibility(View.VISIBLE);
                editTitle.getText().clear();
                editContent.getText().clear();
                break;
        }
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(editTitle.getText())) {
            inputLayoutTitle.setError(strEmpty);
            editTitle.setError(strEmpty);
            return false;
        }

        inputLayoutTitle.setError(null);
        editTitle.setError(null);
        return true;
    }

    private DatabaseReference getRefDB() {
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(NOTES_DB_NAMESPACE).child(uid);
    }
}
