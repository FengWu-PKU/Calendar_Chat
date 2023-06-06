package common;

import java.util.ArrayList;
import java.util.Date;

public class OnedayInfo extends TodoInfoRequest implements java.io.Serializable {

    public ArrayList<TodoItem> todoList;

    public OnedayInfo(int my_uid, int show_uid, Date date) {
        super(my_uid, show_uid, date);
        todoList=new ArrayList<>();
    }


    public ArrayList<TodoItem> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<TodoItem> todoList) {
        this.todoList = todoList;
    }



}
