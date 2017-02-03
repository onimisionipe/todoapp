package matt.todoapp.com.todolist;

import com.orm.SugarRecord;

/**
 * Created by matt on 2/3/17.
 */
public class Todo extends SugarRecord {
    public String title;
    public Todo(){

    }
    public Todo(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
