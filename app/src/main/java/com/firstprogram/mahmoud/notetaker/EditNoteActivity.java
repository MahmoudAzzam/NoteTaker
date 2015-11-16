package com.firstprogram.mahmoud.notetaker;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {

    private boolean isInEditMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button saveButton = (Button)findViewById(R.id.button);
        final EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
        final EditText noteEditText = (EditText)findViewById(R.id.noteEditText);

        Serializable extra = getIntent().getSerializableExtra("Note");
        if (extra != null)
        {
            Note note = (Note)extra;
            titleEditText.setText(note.getTitle());
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
