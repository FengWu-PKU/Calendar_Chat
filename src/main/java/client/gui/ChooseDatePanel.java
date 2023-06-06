package client.gui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ChooseDatePanel extends JPanel {
    private JDateChooser dateChooser;
    private JLabel help;

    private TodoPanel todoPanel;

    public void setTodoPanel(TodoPanel todoPanel) {
        this.todoPanel = todoPanel;
    }

    public ChooseDatePanel() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(700, 50));


        // 创建日期选择器
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200,27));

        help=new JLabel("点击日期可以新建事项，点击事项可以编辑");

        // 创建提交按钮
        JButton submitButton = new JButton("显示");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = dateChooser.getDate();
                todoPanel.update_date(selectedDate);
            }
        });

        // 创建面板用于容纳日期选择器和标题输入框
        add(new JLabel("选择日期:"));
        add(dateChooser);
        add(submitButton);
        add(help);
    }
}
