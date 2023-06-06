package common;

import java.util.ArrayList;
import java.util.Date;

public class OneMonthInfo extends TodoInfoRequest implements java.io.Serializable {

    public ArrayList<TodoItem>[][] todoLists=new ArrayList[4][7];;

    public OneMonthInfo(int my_uid, int show_uid, Date date) {
        super(my_uid, show_uid, date);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <7; j++) {
                todoLists[i][j] = new ArrayList<TodoItem>();
            }
        }

    }


    public ArrayList<TodoItem>[][] getTodoLists() {
        return todoLists;
    }

    public void setTodoList(ArrayList<TodoItem>[][] todoLists) {
        this.todoLists = todoLists;
    }


}
