package com.example.todoapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.pojos.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    // sotck la list qui contient les données à adapter
    private List<Todo> todos;

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvUrgency;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvUrgency = itemView.findViewById(R.id.tvUrgency);
        }
    }

    public TodoAdapter(List<Todo> todos) {
        this.todos = todos;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // créer l'objet vue à partir du layout servant à affficher un item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        // récupère l'objet todo qui est à l'index en cours
        Todo todo = todos.get(position);

        // bind les données qui proviennent de l'objet todo dans les éléments de la vue
        holder.tvName.setText(todo.getName());
        holder.tvUrgency.setText(todo.getUrgency());
    }

    @Override
    public int getItemCount() {
        //retourne le nombre d'éléments de la liste de todos
        return todos.size();
    }

}
