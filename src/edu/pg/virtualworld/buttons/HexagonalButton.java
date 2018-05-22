package edu.pg.virtualworld.buttons;

import javax.swing.*;
import java.awt.*;

class HexagonalButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    public static final int LENGTH = 95;
    public static final int WIDTH = 105;


    public HexagonalButton() {
        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(WIDTH, LENGTH));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for (int i = 0; i < SIDES; i++) {
            hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                    (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
        }
        g.drawPolygon(hex);
    }
}
