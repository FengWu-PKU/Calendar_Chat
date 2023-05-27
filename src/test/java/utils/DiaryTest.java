package utils;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class DiaryTest {

    @Test
    void insertDiaryTest() {
        int account_id=1;
        String name="Alice";
        Timestamp date_t=new Timestamp(System.currentTimeMillis());
        String content="这是日记内容";
        System.out.println(date_t);
        Diary.InsertDiary(account_id, name, date_t, content);
    }
}