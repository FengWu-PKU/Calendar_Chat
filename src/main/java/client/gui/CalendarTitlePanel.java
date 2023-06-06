package client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CalendarTitlePanel extends JPanel {
    private JLabel[] title;

    public CalendarTitlePanel(){
        setLayout(new GridLayout(1, 7));
        //setMaximumSize(new Dimension(this.getMaximumSize().width, 60));
        setPreferredSize(new Dimension(100*7, 50));
        //setMinimumSize(new Dimension(this.getMinimumSize().width, 60));
        //setBorder(new EmptyBorder(5, 10, 5, 10));

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startdate = calendar.getTime();

        title=new JLabel[7];

        for( int col =0; col<7;col++){
            title[col] = new JLabel("", SwingConstants.CENTER);
            title[col].setFont(new Font("微软雅黑", Font.BOLD, 14));

            calendar.setTime(startdate);
            calendar.add(Calendar.DAY_OF_WEEK,col);
            String dayOfWeek = new SimpleDateFormat("E").format(calendar.getTime());
            title[col].setText(dayOfWeek);

            title[col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(title[col]);

        }
    }
}
