package client.gui;

import common.OnedayInfo;
import common.TodoItem;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class TodoPanel extends JPanel  {
    private OneDayPanel[][] gridLabels;
    private Date startdate;
    private Calendar calendar= Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("M.dd");

    public TodoPanel() {
        setLayout(new GridLayout(4, 7));
        //setMaximumSize(new Dimension(this.getMaximumSize().width, 60));
        setPreferredSize(new Dimension(100*7, 150*4));
        //setMinimumSize(new Dimension(this.getMinimumSize().width, 60));
        //setBorder(new EmptyBorder(5, 10, 5, 10));

        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startdate = calendar.getTime();

        // 创建日期标签
        gridLabels = new OneDayPanel[4][7];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                calendar.setTime(startdate);
                calendar.add(Calendar.DAY_OF_WEEK,7*row+col);
                gridLabels[row][col] = new OneDayPanel(calendar.getTime());

                //gridLabels[row][col].setColumnHeader(date);
                gridLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //gridLabels[row][col].setPreferredSize(new Dimension(150, 150));
                add(gridLabels[row][col]);
            }
        }
    }

    public void update_date(Date select_date){
        calendar.setTime(select_date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startdate = calendar.getTime();

        removeAll();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                calendar.setTime(startdate);
                calendar.add(Calendar.DAY_OF_WEEK,7*row+col);
                gridLabels[row][col] = new OneDayPanel(calendar.getTime());

                //gridLabels[row][col].setColumnHeader(date);
                gridLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //gridLabels[row][col].setPreferredSize(new Dimension(150, 150));
                add(gridLabels[row][col]);
            }
        }
        revalidate();
        repaint();
    }

    public void updateOneDay(OnedayInfo info){

        Calendar cal2 = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        ArrayList<TodoItem> todoList=info.getTodoList();
        if(todoList.size()==0){
            return;
        }
        cal1.setTime(todoList.get(0).getDeadline());
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                cal2.setTime(gridLabels[row][col].date);
                if(cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
                    for(TodoItem tmp:todoList){
                        gridLabels[row][col].addTodoItem(tmp);
                    }
                }
            }
        }

    }
}




