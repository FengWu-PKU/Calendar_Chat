package client.gui;


import client.model.Connection;
import common.Message;
import common.MessageType;
import common.OnedayInfo;
import common.TodoItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OneDayPanel extends JPanel {
    private ArrayList<TodoItem> todoList;
    private JPanel contentPanel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("M.d");
    public Date date;

    private int show_uid;

    private int my_uid;

    public OneDayPanel(Date date,int show_uid,int my_uid,ArrayList<TodoItem> todoList) {
        this.date=date;
        this.show_uid=show_uid;
        this.my_uid=my_uid;

        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(100,150));

        // 创建Todolist
        this.todoList = todoList;

        // 创建内容面板
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        //添加日期
        JButton dateButton = new JButton(dateFormat.format(date));
        dateButton.setBorderPainted(false);
        dateButton.setFocusPainted(false);
        dateButton.setContentAreaFilled(false);
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击文章标题时弹出编辑页面
                TodoItem tmp=new TodoItem(date, "新建任务");
                todoList.add(tmp);
                addTodoItem(tmp);

            }
        });
        contentPanel.add(dateButton);

        for(TodoItem tmp:todoList){
            addTodoItem(tmp);
        }

        contentPanel.revalidate();
        contentPanel.repaint();

        // 创建滚动面板，用于容纳内容面板
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        //System.out.println(scrollPane.getVerticalScrollBar().getPreferredSize());
        //System.out.println(scrollPane.getHorizontalScrollBar().getPreferredSize());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(3, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 3));


        // 将滚动面板添加到面板中
        add(scrollPane, BorderLayout.CENTER);
    }


    public void addTodoItem(TodoItem todoItem) {
        // 创建文章标题按钮
        JButton titleButton = new JButton(todoItem.getTitle());
        titleButton.setBorderPainted(false);
        titleButton.setFocusPainted(false);
        titleButton.setContentAreaFilled(false);
        titleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击文章标题时弹出编辑页面
                showArticleEditor(todoItem,titleButton);
            }
        });

        // 将文章标题按钮添加到内容面板
        contentPanel.add(titleButton);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showArticleEditor(TodoItem todoItem,JButton titleButton) {
        // 创建编辑对话框
        JDialog editorDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "编辑", true);
        editorDialog.setLayout(new BorderLayout());
        editorDialog.setPreferredSize(new Dimension(300, 300));

        // 创建编辑面板
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());

        // 创建标题输入框
        JTextField titleField = new JTextField(todoItem.getTitle());


        // 创建类型选择框
        JCheckBox checkBox = new JCheckBox("朋友可见");
        checkBox.setSelected(todoItem.getPub());
        add(checkBox);
        JPanel titleArea = new JPanel();

        titleArea.setLayout(new BoxLayout(titleArea,BoxLayout.Y_AXIS));
        titleArea.add(titleField);
        titleArea.add(checkBox);
        editorPanel.add(titleArea, BorderLayout.NORTH);

        // 创建内容文本区域
        JTextArea contentArea = new JTextArea(todoItem.getContent());
        editorPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);


        // 创建提交按钮
        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新文章标题和内容
                todoItem.setTitle(titleField.getText());
                todoItem.setContent(contentArea.getText());
                todoItem.setPub(checkBox.isSelected());

                // 更新文章标题按钮
                titleButton.setText(titleField.getText());

                OnedayInfo tmp=new OnedayInfo(my_uid,show_uid,date);
                tmp.setTodoList(todoList);
                Connection.writeObject(new Message(MessageType.CLIENT_UPDATE_ONEDAY,tmp));

                editorDialog.dispose();
            }
        });

        // 创建删除按钮
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.remove(titleButton);
                todoList.remove(todoItem);

                contentPanel.revalidate();
                contentPanel.repaint();

                OnedayInfo tmp=new OnedayInfo(my_uid,show_uid,date);
                tmp.setTodoList(todoList);
                Connection.writeObject(new Message(MessageType.CLIENT_UPDATE_ONEDAY,tmp));

                editorDialog.dispose();
            }
        });

        //按钮区域
        JPanel buttonArea= new JPanel(new GridLayout(1,2));
        buttonArea.add(submitButton);
        buttonArea.add(deleteButton);
        editorPanel.add(buttonArea, BorderLayout.SOUTH);

        // 将编辑面板添加到对话框
        editorDialog.add(editorPanel);

        // 设置对话框的大小和位置
        editorDialog.pack();
        editorDialog.setLocationRelativeTo(this);
        editorDialog.setVisible(true);
    }
}

