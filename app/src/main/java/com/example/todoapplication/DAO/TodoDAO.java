package com.example.todoapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoapplication.database.TodoDBHelper;
import com.example.todoapplication.pojos.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends DAO {

    public TodoDAO(Context context) {
        super(new TodoDBHelper(context));
    }

    public Todo find (int id){

        //déclare une variable qui stockera l'objet créé
        Todo todo = null;

        //ouvre la base de données
        open();

        //éxécute la requete et renvoue un Cursor contenant les données
        Cursor cursor = db.rawQuery("select * from " + TodoDBHelper.TODO_TABLE_NAME +
                " where " + TodoDBHelper.TODO_KEY + " = ?",
                new String[] {String.valueOf(id)});

        // positionne le cursor sur le premier enregistrement
        if (cursor != null && cursor.moveToFirst()){
            todo = new Todo();
            todo.setId(cursor.getLong(TodoDBHelper.TODO_KEY_COLUMN_INDEX));
            todo.setName(cursor.getString(TodoDBHelper.TODO_NAME_COLUMN_INDEX));
            todo.setUrgency(cursor.getString(TodoDBHelper.TODO_URGENCY_COLUMN_INDEX));
        }

        //ferme la bdd
        close();

        return todo;
    }

    public List<Todo> list (){
        //déclare une variable qui stockera la liste d'objets
        List<Todo> todos = new ArrayList<>();

        //ouvre la base de données
        open();

        //éxécute la requete et renvoue un Cursor contenant les données
        Cursor cursor = db.rawQuery("select * from " + TodoDBHelper.TODO_TABLE_NAME, null);

        // positionne le cursor sur le premier enregistrement
        if (cursor != null && cursor.moveToFirst()){
            // boucle tant que le cursor n'est pas arrivé sur le dernier enregistrement
            while (!cursor.isAfterLast()) {
                Todo todo = new Todo();
                todo.setId(cursor.getLong(TodoDBHelper.TODO_KEY_COLUMN_INDEX));
                todo.setName(cursor.getString(TodoDBHelper.TODO_NAME_COLUMN_INDEX));
                todo.setUrgency(cursor.getString(TodoDBHelper.TODO_URGENCY_COLUMN_INDEX));

                //ajoute le todo créé dans la liste
                todos.add(todo);

                // passe à l'enregisstrement suivant
                cursor.moveToNext();
            }
            // ferme le cursor
            cursor.close();
        }

        //ferme la bdd
        close();

        return todos;
    }

    public void add (Todo todo){

        //ouvre la base de données
        open();

        ContentValues values = new ContentValues();

        values.put(TodoDBHelper.TODO_NAME, todo.getName());
        values.put(TodoDBHelper.TODO_URGENCY, todo.getUrgency());

        //effectue une insertion des données et récupère l'id généré
        long id = db.insert(TodoDBHelper.TODO_TABLE_NAME, null, values);

        // met à jour l'id de l'objet
        todo.setId(id);

        //ferme la bdd
        close();
    }

    public void update (Todo todo){

        //ouvre la base de données
        open();

        ContentValues values = new ContentValues();

        values.put(TodoDBHelper.TODO_NAME, todo.getName());
        values.put(TodoDBHelper.TODO_URGENCY, todo.getUrgency());

        //exécute la requete update avec la claure where id = ?
        db.update(TodoDBHelper.TODO_TABLE_NAME, values, TodoDBHelper.TODO_KEY + " = ?",
                new String[] {
                        String.valueOf(todo.getId())
                });

        //ferme la bdd
        close();
    }

    public void delete (Todo todo){

        //ouvre la base de données
        open();

        //exécute la requete update avec la claure where id = ?
        db.delete(TodoDBHelper.TODO_TABLE_NAME, TodoDBHelper.TODO_KEY + " = ?",
                new String[] {
                        String.valueOf(todo.getId())
                });

        //ferme la bdd
        close();
    }
}
