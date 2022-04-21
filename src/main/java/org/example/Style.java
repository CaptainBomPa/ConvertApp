package org.example;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Style {

    public static void initButtonStyle(JButton button)
    {
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(Color.decode(Menu.BG_COLOR));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setFont(new Font("", Font.BOLD, 25));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.decode(Menu.BG_COLOR_PRESSED));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(Color.decode(Menu.BG_COLOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode(Menu.BG_COLOR_HOVER));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode(Menu.BG_COLOR));
            }
        });
    }

    public static void initJTextAreaStyle(JTextPane jTextPane)
    {
        jTextPane.setBackground(Color.decode(Menu.BG_COLOR));
        jTextPane.setForeground(Color.WHITE);

        StyledDocument style = jTextPane.getStyledDocument();
        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        style.setParagraphAttributes(0, style.getLength(), align, false);

        jTextPane.setFont(new Font("", Font.BOLD, 20));
        jTextPane.setMargin(new Insets(33,5,33,5));
    }

    public static void initLabelStyle(JLabel label)
    {
        label.setOpaque(true);
        label.setBackground(Color.decode(Menu.BG_COLOR));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("", Font.BOLD, 25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
    }
}
