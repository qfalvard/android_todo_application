package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.todoapplication.DAO.TodoDAO;
import com.example.todoapplication.adapters.TodoAdapter;
import com.example.todoapplication.pojos.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TODOS = "todos";
    private final String TAG = "TodosApplications";
    private TextView tvTodo;
    private String todosSaved;
    private Context context;
    private TodoDAO todoDao = null;
    private String todosBDD;

    private List<Todo> todos;
    private RecyclerView rvTodo;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.recyclerview_main);

//        tvTodo = findViewById(R.id.tvTodo);
        rvTodo = findViewById(R.id.rvTodo);

        // On créait le context
        context = getApplicationContext();
        // On initialise la liste de Todo
        todos = new ArrayList<>();

        todoDao = new TodoDAO(context);

        /*
        // Récupération de la question en cours du Bundle
        if(savedInstanceState != null){
            todosSaved = savedInstanceState.getString(TODOS);
        }
        if (todosSaved == null) {
            todosSaved = "pas de todos";
        }

        // On affiche la liste des Todos
        tvTodo.setText(todosSaved);
        */
        // Créer le layoutManager. Au choix LinearLayoutManager ou GridLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvTodo.setHasFixedSize(true);
        // affecte au RecyclerView son LayoutManager
        rvTodo.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
        StringBuilder sb = new StringBuilder();
        todosBDD = "";
        todos = todoDao.list();
        for (Todo todo : todos) {
            Log.d("Request", todo.getName());
            sb.append(String.format("%s // %s \n", todo.getName(), todo.getUrgency()));
        }
        tvTodo.setText(sb.toString());
        */

        ToDoAsyncTasks toDoAsyncTasks  = new ToDoAsyncTasks();
        toDoAsyncTasks.execute();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString(TODOS,todosSaved);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        // effectue une action suivant l'item sélectionné
        // on test avec un switch l'id de l'item
        switch (item.getItemId()){
            case R.id.itmAdd:
                // Créer un Intent pour ouvrir la page Add
                intent = new Intent(MainActivity.this, Add.class);

                /*
                *On fonctionne désormais avec la base de données, on attend plus de résultat du AddActivity
                startActivityForResult(intent, 2);
                */

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if (resultCode == RESULT_OK) {
                if (todosSaved.contains("pas de todos")){
                    todosSaved = "";
                }

                String message = data.getStringExtra("MESSAGE");

                todosSaved += message;
                tvTodo.setText(todosSaved);
            }
        }
    }

    public class ToDoAsyncTasks extends AsyncTask<String, String, List<Todo>>{

        @Override
        protected List<Todo> doInBackground(String... strings) {

            List<Todo> responseTodo = new ArrayList<>();

            try {
                responseTodo = todoDao.list();
                /*
                for (int i = 0 ; i < 10 ; i++){
                    Thread.sleep(1000);
                }
                */
            } catch (Exception e){
                e.printStackTrace();
            }

            return responseTodo;
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {

/*            StringBuilder sb = new StringBuilder();

            for (Todo todo : todos){
                sb.append(String.format("%s // %s \n", todo.getName(), todo.getUrgency()));
            }

            tvTodo.setText(sb.toString());*/

            todoAdapter = new TodoAdapter(todos);
            rvTodo.setAdapter(todoAdapter);
        }
    }
}