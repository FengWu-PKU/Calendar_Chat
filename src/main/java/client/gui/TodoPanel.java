package client.gui;

import common.TodoItem;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TodoPanel extends JPanel {
    private JScrollPane[][] gridLabels;
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
        gridLabels = new JScrollPane[4][7];


        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                gridLabels[row][col] = new JScrollPane();
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                calendar.setTime(startdate);
                calendar.add(Calendar.DAY_OF_WEEK,7*row+col);
                String date = dateFormat.format(calendar.getTime());
                contentPanel.add(new JLabel(date));
                gridLabels[row][col].setViewportView(contentPanel);


                //gridLabels[row][col].setColumnHeader(date);
                gridLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //gridLabels[row][col].setPreferredSize(new Dimension(150, 150));
                add(gridLabels[row][col]);
            }
        }
    }

    public void update(ArrayList<TodoItem> todoList){
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                calendar.setTime(startdate);
                calendar.add(Calendar.DAY_OF_WEEK, 7 * row + col);
                String date = dateFormat.format(calendar.getTime());
                contentPanel.add(new JLabel(date));

                Calendar cal2 = Calendar.getInstance();

                for(TodoItem tmp_item:todoList){
                    cal2.setTime(tmp_item.getDeadline());
                    if(calendar.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                            calendar.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                        contentPanel.add(new JLabel(tmp_item.getTitle()));
                        System.out.println(tmp_item.getTitle());
                    }
                }
                gridLabels[row][col].setViewportView(contentPanel);

            }
        }

    }
}




