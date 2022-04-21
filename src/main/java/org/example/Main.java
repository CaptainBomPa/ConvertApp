package org.example;

import javax.swing.*;

public class Main {
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 400;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        new Menu(frame);
    }
}
