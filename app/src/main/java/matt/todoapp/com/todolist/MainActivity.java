package matt.todoapp.com.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText todoEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setMessage("What do you want to remember")
                        .setView(todoEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                String task = String.valueOf(todoEditText.getText());
                                Todo todo = new Todo(task);
                                todo.save();
                                updateUI();

                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        final ListView listview = (ListView) findViewById(R.id.list_todo);
        final List<Todo> todos = Todo.listAll(Todo.class);
        final ArrayList<String> list = new ArrayList<>();
        ListIterator itr = todos.listIterator();
        while(itr.hasNext()){
            Todo elem = (Todo)itr.next();
            list.add(elem.title);
        }
        if(todoAdapter == null){
            todoAdapter = new ArrayAdapter<>(this,R.layout.item_todo,R.id.title, list);
            listview.setAdapter(todoAdapter);
        } else {
            todoAdapter.clear();
            todoAdapter.addAll(list);
            todoAdapter.notifyDataSetChanged();
        }
    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView textview = (TextView) parent.findViewById(R.id.title);
        String task = String.valueOf(textview.getText());
        Todo.deleteAll(Todo.class, "title=?",task);
        updateUI();
    }

}
