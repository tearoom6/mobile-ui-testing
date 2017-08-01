package biz.tearoom6.mobile_ui_testing_android.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.models.Note;

public class NotesAdapter extends FirebaseListAdapter<Note> {

    private DatabaseReference refDB;

    public NotesAdapter(DatabaseReference ref, Activity activity) {
        super(activity, Note.class, R.layout.list_item, ref);
        refDB = ref;
    }

    @Override
    protected void populateView(View v, Note note, int position) {
        TextView text = (TextView) v.findViewById(R.id.item_view);
        text.setText(note.getTitle());
        text.setHeight(100);
    }

    public void addNote(String title, String content) {
        refDB.push().setValue(new Note(title, content));
    }

    @Override
    public Note getItem(int position) {
        return super.getItem(getCount() - 1 - position);  // To show in reverse order.
    }
}
