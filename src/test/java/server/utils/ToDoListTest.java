package server.utils;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {

    @Test
    void insertEntry() {
        ToDoList toDoList=new ToDoList(1, "Alice", "study", "写完数据库操作", new Timestamp(System.currentTimeMillis()), true);
        ToDoList.insertEntry(toDoList);
    }

    @Test
    void deleteEntry() {
        ToDoList.deleteEntry(1, "study");
    }

    @Test
    void modifyEntry() {
        ToDoList tmp=new ToDoList(1, "Alice", "labs", "更新的内容", new Timestamp(System.currentTimeMillis()), true);
        ToDoList.modifyEntry(1, "study", tmp);
    }

    @Test
    void findNotCompletedEntry() {
        ToDoList[] ans=ToDoList.findNotCompletedEntry(1);
        for(int i=0;i<ToDoList.MAXTODOLISTNUM&&ans[i]!=null; i++) {
            System.out.print(ans[i].usr_name);
            System.out.print(", ");
            System.out.print(ans[i].entry_name);
            System.out.print(", ");
            System.out.print(ans[i].content);
            System.out.print(", ");
            System.out.print(ans[i].ddl);
        }
    }

}