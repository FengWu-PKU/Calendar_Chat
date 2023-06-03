package server;

import common.*;
import server.ManageClientThread;
import server.ServerConClientThread;
import server.utils.Account;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Discussion {
    ArrayList<Integer> users;
    ArrayList<Draw> draws;
    ArrayList<UserMessage> messages;
    ReentrantLock lock;

    public Discussion(int id) {
        users = new ArrayList<>();
        users.add(id);
        draws = new ArrayList<>();
        messages = new ArrayList<>();
        lock = new ReentrantLock();
    }

    public DiscussionInfo GetDiscussionInfo() {
        ArrayList<UserDiscussion> UDList = new ArrayList<>();
        for (Integer i : users) UDList.add(new UserDiscussion(i, Account.getUsernameByID(i)));
        return new DiscussionInfo(UDList, draws, messages);
    }

    public void EraseUser(int id) throws IOException {
        lock.lock();
        users.remove((Integer) id);
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_EXIT_DISCUSSION, id));
        }
    }

    public void InsertUser(int id) throws IOException {
        lock.lock();
        users.add(id);
        System.out.println("user list: ");
        String dh = "";
        for (Integer i : users) {
            System.out.print(dh+i);
            dh = ",";
        }
        System.out.print('\n');
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            UserDiscussion tmp = new UserDiscussion(id, Account.getUsernameByID(id));
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_JOIN_DISCUSSION, tmp));
        }
    }

    public void AddDraw(Draw draw) throws IOException {
        lock.lock();
        draws.add(draw);
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_DRAW, draw));
        }
    }

    public void AddMessage(UserMessage um) throws IOException {
        lock.lock();
        messages.add(um);
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_SEND_MESSAGE, um));
        }
    }

    public void ClearPaint() throws IOException {
        lock.lock();
        draws.clear();
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_CLEAR_PAINT, null));
        }
    }

    public void ClearMessage() throws IOException {
        lock.lock();
        messages.clear();
        lock.unlock();
        for (Integer i : users) {
            ServerConClientThread iThread = ManageClientThread.getClientThread(i);
            ObjectOutputStream oos = new ObjectOutputStream((iThread.GetSocket()).getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_CLEAR_MESSAGE, null));
        }
    }
}