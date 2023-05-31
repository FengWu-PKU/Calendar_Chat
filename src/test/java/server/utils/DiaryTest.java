package server.utils;

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
        Diary diary=new Diary(account_id, name, date_t, content);
        Diary.InsertDiary(diary);
    }
    @Test
    void findDiaryTest() {
    }

    @Test
    void searchDiaryTest() {
        String s="日记";
        Diary[] diaries=Diary.searchDiary(1, s);
        for(int i=0;i<Diary.MAXDIARYNUM&&diaries[i]!=null; i++) {
            System.out.print(diaries[i].writer_name);
            System.out.print(", ");
            System.out.println(diaries[i].content);
        }
    }

    @Test
    void countDiaryTest() {
        String s="日记";
        int cnt=Diary.countDiary(1,s);
        System.out.println(cnt);
    }
}