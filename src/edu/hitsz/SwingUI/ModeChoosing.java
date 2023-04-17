package edu.hitsz.SwingUI;

import edu.hitsz.application.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class ModeChoosing {
    private JPanel mainPanel;
    private JButton levelBtn1;
    private JButton levelBtn3;
    private JButton levelBtn2;
    private JCheckBox SoundEffectCtl;

    public int level;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ModeChoosing() {
        levelBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level = 1;
                Game.soundEffectEnable = SoundEffectCtl.isSelected();
                Game game = new easyGame();
                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
                try {
                    ImageManager.BACKGROUND_IMAGE1 = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        levelBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level = 2;
                Game.soundEffectEnable = SoundEffectCtl.isSelected();
                Game game = new normalGame();
//                game.soundEffectEnable = SoundEffectCtl.isSelected();
                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
                try {
                    ImageManager.BACKGROUND_IMAGE1 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        levelBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level = 3;
                Game.soundEffectEnable = SoundEffectCtl.isSelected();
                Game game = new hardGame();
//                game.soundEffectEnable = SoundEffectCtl.isSelected();
                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
                try {
                    ImageManager.BACKGROUND_IMAGE1 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    private void newGame() {
        Game game = new easyGame();
        Main.cardPanel.add(new easyGame(), "game");
        Main.cardLayout.show(Main.cardPanel, "game");
        game.action();
    }
}
