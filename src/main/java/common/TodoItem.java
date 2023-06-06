package common;

import java.util.Date;

public class TodoItem {
    Date deadline=new Date();
    String title=" ";
    String content=" ";
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TodoItem() {}

    public TodoItem(Date d, String t) {
        title = t;
        deadline = d;
    }
}
