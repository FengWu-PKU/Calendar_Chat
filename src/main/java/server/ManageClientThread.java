package server;

import java.util.HashMap;
import java.util.Iterator;
import java.lang.Integer;

public class ManageClientThread {
    public static HashMap<Integer, ServerConClientThread> hm = new HashMap<Integer, ServerConClientThread>();

    public static void addClientThread(int uid, ServerConClientThread ct) {
        System.out.println("用户 "+uid+" 已上线");
        hm.put(uid,ct);
    }

    public static void removeClientThread(int uid) {
        System.out.println("用户 "+uid+" 已离线");
        hm.remove(uid);
    }

    public static ServerConClientThread getClientThread(int uid) {
        return (ServerConClientThread) hm.get(uid);
    }
}