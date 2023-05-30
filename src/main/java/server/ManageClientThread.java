package server;

import java.util.HashMap;
import java.util.Iterator;
import java.lang.Integer;

public class ManageClientThread {
    public static HashMap<Integer, ServerConClientThread> hm = new HashMap<Integer, ServerConClientThread>();

    public static void addClientThread(int uid, ServerConClientThread ct) {
        System.out.println("在hashmap中已添加"+uid);
        hm.put(uid,ct);
    }

    public static void removeClientThread(int uid) {
        hm.remove(uid);
    }

    public static ServerConClientThread getClientThread(int uid) {
        return (ServerConClientThread) hm.get(uid);
    }
}