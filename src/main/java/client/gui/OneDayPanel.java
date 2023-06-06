package client.gui;


import common.TodoItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OneDayPanel extends JPanel {
    private ArrayList<TodoItem> TodoList;
    private JPanel contentPanel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("M.dd");

    public OneDayPanel(Date date) {
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(100,150));

        // 创建Todolist
        TodoList = new ArrayList<>();

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
                addTodoItem(new TodoItem(date, "新建任务"));
            }
        });
        contentPanel.add(dateButton);
        contentPanel.revalidate();
        contentPanel.repaint();

        // 创建滚动面板，用于容纳内容面板
        JScrollPane scrollPane = new JScrollPane(contentPanel);

        // 将滚动面板添加到面板中
        add(scrollPane, BorderLayout.CENTER);
    }


    public void addTodoItem(TodoItem todoItem) {
        // 创建文章对象
        TodoList.add(todoItem);

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
        editorPanel.add(titleField, BorderLayout.NORTH);

        // 创建内容文本区域
        JTextArea contentArea = new JTextArea(todoItem.getContent());
        editorPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        // 创建提交按钮
        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新文章标题和内容
                String newTitle = titleField.getText();
                String newContent = contentArea.getText();
                todoItem.setTitle(newTitle);
                todoItem.setContent(newContent);

                // 更新文章标题按钮
                titleButton.setText(newTitle);

                editorDialog.dispose();
            }
        });
        editorPanel.add(submitButton, BorderLayout.SOUTH);

        // 将编辑面板添加到对话框
        editorDialog.add(editorPanel);

        // 设置对话框的大小和位置
        editorDialog.pack();
        editorDialog.setLocationRelativeTo(this);
        editorDialog.setVisible(true);
    }
}

