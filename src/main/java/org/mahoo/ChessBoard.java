package org.mahoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ChessBoard extends JPanel {
    public static final int MARGIN = 15;
    public static final int SPAN = 20;
    public static final int ROWS = 18;
    public static final int COLS = 18;

    Image image;
    Chess[] chessList; // 记录已经下在棋盘上的棋子的数组
    int chessCount;  //当前棋盘上棋子的个数
    boolean isBlack = true; //下一步轮到哪一方下棋，默认是黑棋先
    boolean isGaming = true;

    private Five five;

    private boolean hasChess(int col, int row) {
        for (int i = 0; i < chessCount; i++) {
            Chess chess = chessList[i];
            if (chess != null && chess.getCol() == col && chess.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public ChessBoard(Five five) {
        this.five = five;
        image = Toolkit.getDefaultToolkit().getImage("/img/back.png");
        chessList = new Chess[400];
        this.addMouseListener(new MouseMointor());
        this.addMouseMotionListener(new MouseMotionMointor());
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
        for (int i = 0; i <= ROWS; i++) {
            graphics.drawLine(MARGIN, MARGIN + i * SPAN, MARGIN + COLS * SPAN, MARGIN + i * SPAN);
        }
        for (int i = 0; i <= COLS; i++) {
            graphics.drawLine(MARGIN + i * SPAN, MARGIN, MARGIN + i * SPAN, MARGIN + ROWS * SPAN);
        }

        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);

        for (int i = 0; i < chessCount; i++) {
            chessList[i].draw(graphics);
            if (i == chessCount - 1) {
                int xPos = chessList[i].getCol() * SPAN + MARGIN;
                int yPos = chessList[i].getRow() * SPAN + MARGIN;
                graphics.setColor(Color.RED);
                graphics.drawRect(xPos - Chess.DIAMETER / 2, yPos - Chess.DIAMETER / 2, Chess.DIAMETER, Chess.DIAMETER);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(MARGIN * 2 + SPAN * COLS, MARGIN * 2 + SPAN * ROWS);
    }

    public boolean isWin(int col, int row) {
        int continueCount = 1;
        Color c = isBlack ? Color.BLACK : Color.WHITE;
        // 横向向左寻找
        for (int x = col - 1; x >= 0; x--) {
            if (hasChess(x, row, c)) {
                continueCount++;
            } else break;
        }
        // 横向向右寻找
        for (int x = col + 1; x <= COLS; x++) {
            if (hasChess(x, row, c)) {
                continueCount++;
            } else break;
        }
        if (continueCount >= 5)
            return true;
        else
            continueCount = 1;
        // 纵向向上寻找
        for (int y = row - 1; y >= 0; y--) {
            if (hasChess(col, y, c)) {
                continueCount++;
            } else break;
        }
        // 纵向向下寻找
        for (int y = row + 1; y <= ROWS; y++) {
            if (hasChess(col, y, c)) {
                continueCount++;
            } else break;
        }
        if (continueCount >= 5)
            return true;
        else
            continueCount = 1;
        // 左上到右下
        // 向左上寻找
        for (int x = col - 1, y = row - 1; x >= 0 && y >= 0; x--, y--) {
            if (hasChess(x, y, c)) {
                continueCount++;
            } else break;
        }
        // 向右下寻找
        for (int x = col + 1, y = row + 1; x <= COLS && y <= ROWS; x++, y++) {
            if (hasChess(x, y, c)) {
                continueCount++;
            } else break;
        }
        if (continueCount >= 5)
            return true;
        else
            continueCount = 1;
        // 右上到左下
        // 向右上寻找
        for (int x = col + 1, y = row - 1; x <= COLS && y >= 0; x++, y--) {
            if (hasChess(x, y, c)) {
                continueCount++;
            } else break;
        }
        // 向左下寻找
        for (int x = col - 1, y = row + 1; x >= 0 && y <= ROWS; x--, y++) {
            if (hasChess(x, y, c)) {
                continueCount++;
            } else break;
        }
        if (continueCount >= 5)
            return true;
        else
            return false;
    }

    private boolean hasChess(int col, int row, Color color) {
        for (int i = 0; i < chessCount; i++) {
            Chess chess = chessList[i];
            if (chess != null && chess.getCol() == col && chess.getRow() == row && chess.getColor() == color)
                return true;
        }
        return false;
    }

    class MouseMointor extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isGaming) return;
            int col = (e.getX() - MARGIN + SPAN / 2) / SPAN;
            int row = (e.getY() - MARGIN + SPAN / 2) / SPAN;

            if (col < 0 || col > COLS || row < 0 || row > ROWS) return;
            if (hasChess(col, row)) return;
            Chess chess = new Chess(ChessBoard.this, col, row, isBlack ? Color.BLACK : Color.WHITE);
            chessList[chessCount++] = chess;
            repaint();
            if (isWin(col, row)) {
                String colorName = isBlack ? "黑棋" : "白棋";
                String msg = String.format("恭喜， %s赢了", colorName);
                five.message.setText(msg);
                JOptionPane.showMessageDialog(ChessBoard.this, msg);
                isGaming = false;
                return;
            }
            isBlack = !isBlack;
            if (isBlack) {
                five.message.setText("请黑子下棋！");
            } else
                five.message.setText("请白子下棋！");
        }
    }

    public void restartGame() {
        for (int i = 0; i < chessList.length; i++) {
            chessList[i] = null;
        }
        isBlack = true;
        isGaming = true;
        chessCount = 0;
        repaint();
        if (isBlack) {
            five.message.setText("请黑子下棋！");
        } else
            five.message.setText("请白子下棋！");
    }

    public void goBack() {
        if (chessCount == 0) {
            return;
        }
        chessList[chessCount - 1] = null;
        chessCount--;
        isBlack = !isBlack;
        repaint();
        if (isBlack) {
            five.message.setText("请黑子下棋！");
        } else
            five.message.setText("请白子下棋！");
    }

    class MouseMotionMointor extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            int col = (e.getX() - MARGIN + SPAN / 2) / SPAN;
            int row = (e.getY() - MARGIN + SPAN / 2) / SPAN;

            if (col < 0 || col > COLS || row < 0 || row > ROWS || !isGaming || hasChess(col, row))
                ChessBoard.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            else ChessBoard.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

}
