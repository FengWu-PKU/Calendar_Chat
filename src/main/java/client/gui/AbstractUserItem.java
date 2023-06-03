package client.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public abstract class AbstractUserItem extends JPanel implements ActionListener {
  private JLabel usernameLabel = new JLabel();
  private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

  public AbstractUserItem() {
    setMaximumSize(new Dimension(this.getMaximumSize().width, 60));
    setPreferredSize(new Dimension(this.getPreferredSize().width, 60));
    setMinimumSize(new Dimension(this.getMinimumSize().width, 60));
    setBorder(new EmptyBorder(5, 10, 5, 10));
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    setLayout(layout);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(3, 3, 3, 3);

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 1;
    layout.setConstraints(usernameLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.weightx = 0;
    layout.setConstraints(buttonPanel, constraints);

    add(usernameLabel);
    add(buttonPanel);
  }

  public void setUsername(String username) {
    usernameLabel.setText(username);
  }

  public void addButton(JButton button) {
    buttonPanel.add(button);
    button.addActionListener(this);
  }
}
