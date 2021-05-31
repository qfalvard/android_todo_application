package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.todoapplication.DAO.TodoDAO;
import com.example.todoapplication.pojos.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Add extends AppCompatActivity {

    private Context context;
    private Spinner edtUrgency;
    private EditText edtName;
    private Button add;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        context = getApplicationContext();

        // récupère la barre d'action
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get reference of widgets from XML layout
        edtUrgency = findViewById(R.id.spnUrgency);
        edtName = findViewById(R.id.edtName);
        add = findViewById(R.id.btnAdd);
        cancel = findViewById(R.id.btnCancel);

        // Initializing a String Array
        String[] urgencys = new String[]{
                "Low urgency",
                "Normal urgency",
                "High urgency"
        };

        final List<String> urgencyList = new ArrayList<>(Arrays.asList(urgencys));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,urgencyList
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        edtUrgency.setAdapter(spinnerArrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String urgency = edtUrgency.getSelectedItem().toString();

/*

                String message = String.format("%s // %s \n", name, urgency);
                Intent intent = new Intent();
*/

                TodoDAO todoDAO = new TodoDAO(context);
                Todo todo = new Todo();
                todo.setName(name);
                todo.setUrgency(urgency);
                todoDAO.add(todo);

/*
                // Sans pojo
                intent.putExtra("MESSAGE", message);
                setResult(RESULT_OK, intent);
                */

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // créé un intent pour revenir sur la main activity
                // avec RESULT_CANCELED
                // Intent resultIntent = new Intent();
                // setResult(RESULT_CANCELED, resultIntent);
                // termine cette activity -> revient sur main activity
                finish();

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        // termine une activité
        finish();
        return super.onSupportNavigateUp();
    }
}