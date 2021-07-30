package com.javagui;

import javax.swing.*;
import java.awt.*;

public class Snake extends  JFrame{
    Snake(){

        this.add(new GamePanel());

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.
                EXIT_ON_CLOSE);
       // this.setSize(500, 500 );
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon(
                "snake.png"
        );
        this.setIconImage(
                image.getImage()
        );

     /*   this
                .getContentPane()
                .setBackground(
                        new Color(13,150,22)
                );*/
    }
}
