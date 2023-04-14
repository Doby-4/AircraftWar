package edu.hitsz.SwingUI;

import edu.hitsz.ranking.Score;
import edu.hitsz.ranking.ScoreDAO;
import edu.hitsz.ranking.Scores2SwingTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class RankingBoard {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel topLable;
    private JScrollPane tableScrollPanel;
    private JTable scoreTable;
    private JButton Deletebutton;
    private boolean haveDeleted = false;
    //    public List<int> deletedScores = new ArrayList<>();
    private DefaultTableModel model;

    private List<Score> scores;

    public RankingBoard(ScoreDAO scoreDAO) {
        scores = scoreDAO.getAllScores();
        Scores2SwingTable S2T = new Scores2SwingTable(scores);
        S2T.convert();
        model = S2T.getTableModel();
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
        Deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(Deletebutton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    scoreDAO.deleteScore(row);
                    model.removeRow(row);
                    scoreDAO.sortScore();
                    scores = scoreDAO.getAllScores();
                    Scores2SwingTable S2T_del = new Scores2SwingTable(scores);
                    S2T_del.convert();
                    model = S2T_del.getTableModel();
                    scoreTable.setModel(model);
                    tableScrollPanel.setViewportView(scoreTable);
                    scoreDAO.saveScore();
                }
            }
        });
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
