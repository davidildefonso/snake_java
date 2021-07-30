package com.javagui;

import javax.swing.*;
import java.awt.*;

public class Myframe extends JFrame {
    Myframe(){
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.
                EXIT_ON_CLOSE);
        this.setSize(420, 420 );
        this.setVisible(true);

        ImageIcon image = new ImageIcon(
                "snake.png"
        );
        this.setIconImage(
                image.getImage()
        );
        this
                .getContentPane()
                .setBackground(
                        new Color(13,150,22)
                );
    }
}
