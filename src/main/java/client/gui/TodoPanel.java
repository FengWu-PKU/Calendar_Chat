package client.gui;

import client.model.Connection;
import common.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TodoPanel extends JPanel  {
    private OneDayPanel[][] gridLabels;
    private Date startdate;
    private Calendar calendar= Calendar.getInstance();
    private int show_uid,my_uid;


    public TodoPanel(int my_uid) {
        this.show_uid=my_uid;
        this.my_uid=my_uid;
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(100*7, 150*4));

        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startdate = calendar.getTime();

        add(new LoadingLabel());
        Connection.writeObject(new Message(MessageType.CLIENT_REQUEST_ONEMONTH,new TodoInfoRequest(my_uid,show_uid,startdate)));

    }

    public void update(){
        removeAll();
        setLayout(new GridLayout());
        add(new LoadingLabel());
        revalidate();
        repaint();
        Connection.writeObject(new Message(MessageType.CLIENT_REQUEST_ONEMONTH,new TodoInfoRequest(my_uid,show_uid,startdate)));
    }

    public void update_date(Date select_date){
        calendar.setTime(select_date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startdate = calendar.getTime();
        update();
    }

    public void update_show_uid(int show_uid){
        this.show_uid=show_uid;
        update();
    }

    public void updateOneMonth(OneMonthInfo info){
        removeAll();

        setLayout(new GridLayout(4, 7));
        gridLabels = new OneDayPanel[4][7];
        ArrayList<TodoItem>[][] todoList=info.getTodoLists();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                calendar.setTime(info.getDate());
                calendar.add(Calendar.DAY_OF_WEEK,7*row+col);
                gridLabels[row][col] = new OneDayPanel(calendar.getTime(),info.getShow_uid(),info.getMy_uid(),todoList[row][col]);
                // gridLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(gridLabels[row][col]);
            }
        }
        revalidate();
        repaint();
    }


}




