package common;

import java.util.Date;

public class TodoItem implements java.io.Serializable {
    Date deadline=new Date();
    String title=" ";
    String content=" ";

    public Boolean getPub() {
        return pub;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    Boolean pub=Boolean.FALSE;
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
    public TodoItem(String a,String b,Date d,boolean p){
        title = a;
        content = b;
        deadline = d;
        pub = p;
    }

}
