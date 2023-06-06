package client.gui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class NewTodoItemPanel extends JPanel {
    private JDateChooser dateChooser;
    private JTextField titleField;
    private JTextArea contentArea;

    public NewTodoItemPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 300));


        // 创建日期选择器
        dateChooser = new JDateChooser();

        // 创建标题输入框
        titleField = new JTextField();

        // 创建内容文本区域
        contentArea = new JTextArea();

        // 创建提交按钮
        JButton submitButton = new JButton("提交新事项");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = dateChooser.getDate();
                String title = titleField.getText();
                String content = contentArea.getText();

                // 执行提交操作，例如将数据传递给其他类处理
                // ...

                // 打印提交的数据
                System.out.println("选择的日期: " + selectedDate);
                System.out.println("标题: " + title);
                System.out.println("内容: " + content);
            }
        });

        // 创建面板用于容纳日期选择器和标题输入框
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("选择日期:"));
        inputPanel.add(dateChooser);
        inputPanel.add(new JLabel("标题:"));
        inputPanel.add(titleField);

        // 将日期选择器、标题输入框、内容文本区域和提交按钮添加到面板中
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentArea), BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}