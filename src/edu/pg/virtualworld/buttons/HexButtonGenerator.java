package edu.pg.virtualworld.buttons;

import javax.swing.*;

public class HexButtonGenerator implements ButtonGenerator {
    @Override
    public JButton create() {
        return new HexagonalButton();
    }
}
