package com.firstprogram.mahmoud.notetaker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {

    public static final int RESULT_DELETE = -500;
    private boolean isInEditMode = true;
    private boolean isAddingNote = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button saveButton = (Button)findViewById(R.id.button);
        final EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
        final EditText noteEditText = (EditText)findViewById(R.id.noteEditText);
        final TextView dateTextView = (TextView)findViewById(R.id.dateEditText);

        Serializable extra = getIntent().getSerializableExtra("Note");
        if (extra != null)
        {
            Note note = (Note)extra;
            titleEditText.setText(note.getTitle());
            noteEditText.setText(note.getNote());

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = dateFormat.format(note.getDate());

            dateTextView.setText(date);

            isInEditMode = false;
            titleEditText.setEnabled(false);
            noteEditText.setEnabled(false);
            saveButton.setText("Edit");

            isAddingNote = false;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isInEditMode)
                {

                    Intent returnIntent = new Intent();
                    Note note = new Note(titleEditText.getText().toString(), noteEditText.getText
                            ().toString(), Calendar.getInstance().getTime());
                    returnIntent.putExtra("Note", note);
                    setResult(RESULT_OK, returnIntent);
                    isInEditMode = false;
                    titleEditText.setEnabled(false);
                    noteEditText.setEnabled(false);
                    saveButton.setText("Edit");
                }
                else
                {
                    isInEditMode = true;
                    saveButton.setText("Save");
                    titleEditText.setEnabled(true);
                    noteEditText.setEnabled(true);
                }



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);

        if(isAddingNote){
            menu.removeItem(R.id.deleteItem);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?");
        builder.setTitle("Confirmation");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnIntent = new Intent();
                setResult(RESULT_DELETE, returnIntent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteItem) {
            return true;
        }

        return true;
    }
}
