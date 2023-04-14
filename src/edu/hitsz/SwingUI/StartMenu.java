package edu.hitsz.SwingUI;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel mainPanel;
    private JLabel title;
    private JButton startBtn;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public StartMenu() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardPanel.add(new ModeChoosing().getMainPanel(), "mode");
                Main.cardLayout.show(Main.cardPanel, "mode");
            }
        });
    }
}
