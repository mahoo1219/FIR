package org.mahoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Five extends JFrame {
    private JToolBar toolBar;
    private JButton startButton, backButton, exitButton;
    JLabel message;
    private ChessBoard boardPanel;

    public Five() throws HeadlessException {
        super("单机版五子棋");
        toolBar = new JToolBar();
        startButton = new JButton("重新开始");
        backButton = new JButton("悔棋");
        exitButton = new JButton("退出");
        toolBar.add(startButton);
        toolBar.add(backButton);
        toolBar.add(exitButton);
        this.add(toolBar, BorderLayout.NORTH);
        message = new JLabel("请黑子下棋");
        this.add(message, BorderLayout.SOUTH);
        boardPanel = new ChessBoard(this);
        this.add(boardPanel, BorderLayout.CENTER);

        ActionMonitor monitor = new ActionMonitor();
        startButton.addActionListener(monitor);
        backButton.addActionListener(monitor);
        exitButton.addActionListener(monitor);

        //this.setBounds(200, 200, 300, 200);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);


    }

    public static void main(String[] args) {
        new Five();
    }

    class ActionMonitor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                boardPanel.restartGame();
            } else if (e.getSource() == backButton) {
                boardPanel.goBack();
            } else if (e.getSource() == exitButton) {
                System.exit(0);
            }
        }
    }

}
