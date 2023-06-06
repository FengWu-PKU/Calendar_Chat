package common;

import java.util.Date;

public class TodoInfoRequest {
    public int my_uid;
    public int show_uid;
    public Date date;

    public TodoInfoRequest(int my_uid, int show_uid, Date date) {
        this.my_uid = my_uid;
        this.show_uid = show_uid;
        this.date = date;
    }

    public int getMy_uid() {
        return my_uid;
    }

    public void setMy_uid(int my_uid) {
        this.my_uid = my_uid;
    }

    public int getShow_uid() {
        return show_uid;
    }

    public void setShow_uid(int show_uid) {
        this.show_uid = show_uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
