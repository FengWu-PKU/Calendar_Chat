package server;

import java.time.LocalDate;

public class database {
    static class node {
        String name, pw, phone, email, intro;
        java.sql.Date birth;
    }
    node[] a = new node[100010];
    int cnt;
    int find(String name) {
        for (int i = 1; i <= cnt; i++) if (a[i].name.equals(name)) return i;
        return -1;
    }
    int find(String name, String pw) {
        for (int i = 1; i <= cnt; i++) if (a[i].name.equals(name) && a[i].pw.equals(pw)) return i;
        return -1;
    }
    int signUp(String name, String pw) {
        for (int i = 1; i <= cnt; i++) if (a[i].name.equals(name)) return -1;
        ++cnt;
        a[cnt] = new node();
        a[cnt].name = name;
        a[cnt].pw = pw;
        return cnt;
    }
    void insert(int id, String ph, String em, String intro, java.sql.Date bir) {
        a[id].phone = ph;
        a[id].email = em;
        a[id].intro = intro;
        a[id].birth = bir;
    }
}