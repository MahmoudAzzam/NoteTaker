package com.firstprogram.mahmoud.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    private List<Note> notes = new ArrayList<Note>();
    private ListView notesListView;
    private int editingNoteId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Serializable extra = data.getSerializableExtra("Note");
        if (extra != null)
        {
            Note newNote = (Note)extra;

            if(editingNoteId > -1)
            {
                notes.set(editingNoteId, newNote);
                editingNoteId = -1;
            }
            else
            {
                notes.add(newNote);
            }
            populateList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        notesListView = (ListView)findViewById(R.id.notesListView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int itemNumber, long id) {

                Intent editNoteIntent = new Intent(view.getContext(), EditNoteActivity.class);
                editNoteIntent.putExtra("Note", notes.get(itemNumber));
                editingNoteId = itemNumber;
                startActivityForResult(editNoteIntent, 1);

            }
        });

        notes.add(new Note("First note", ":", new Date()));
        notes.add(new Note("Second note", ":", new Date()));
        notes.add(new Note("Third note", ":", new Date()));
        notes.add(new Note("Fourth note", ":", new Date()));
        notes.add(new Note("Fifth note", ":", new Date()));
        notes.add(new Note("Sixth note", ":", new Date()));
        notes.add(new Note("Seventh note", ":", new Date()));

        populateList();
    }

    private void populateList() {

        List<String> values = new ArrayList<String>();

        for (Note note:notes)
        {
            values.add(note.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_list_item_1, android.R.id.text1, values);
        notesListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(editNoteIntent, 1);

        return true;
    }
}
