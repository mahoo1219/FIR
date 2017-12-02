package org.mahoo;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Chess {
    public static final int DIAMETER = ChessBoard.SPAN - 2;
    private int col;
    private int row;
    private Color color;
    ChessBoard chessBoard;

    public Chess(ChessBoard chessBoard, int col, int row, Color color) {
        this.chessBoard = chessBoard;
        this.col = col;
        this.row = row;
        this.color = color;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public Color getColor() {
        return color;
    }

    public void draw(Graphics graphics) {
        int xPos = col * chessBoard.SPAN + chessBoard.MARGIN;
        int yPos = row * chessBoard.SPAN + chessBoard.MARGIN;
        Graphics2D graphics2D = (Graphics2D) graphics;
        RadialGradientPaint paint = null;
        int x = xPos + DIAMETER / 4;
        int y = yPos - DIAMETER / 4;
        float[] f = {0f, 1f};
        Color[] colors = {Color.WHITE, Color.BLACK};

        // 圆形辐射渐变模式填充
        if (color == Color.BLACK) {
            paint = new RadialGradientPaint(x, y, DIAMETER, f, colors);
        } else if (color == Color.WHITE) {
            paint = new RadialGradientPaint(x, y, DIAMETER * 4, f, colors);
        }

        graphics2D.setPaint(paint);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        Ellipse2D e = new Ellipse2D.Float(xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER);
        graphics2D.fill(e);
    }
}
