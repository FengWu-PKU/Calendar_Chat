package utils;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {

    @Test
    void insertEntry() {
        ToDoList toDoList=new ToDoList(1, "Alice", "study", "写完数据库操作", new Timestamp(System.currentTimeMillis()), true);
        ToDoList.insertEntry(toDoList);
    }
}