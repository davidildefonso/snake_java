package com.javagui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel
implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS =
            (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int [GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    String musicPath = "D:\\david\\JavaExamples\\580898__bloodpixelhero__in-game.wav";
    AudioPlayer bgMusic;
    AudioPlayer biteSound;
    AudioPlayer gameOverSound;
    String biteSoundPath = "D:\\david\\JavaExamples\\471620__puerta118m__bite.wav";
    String gameOverPath = "D:\\david\\JavaExamples\\7764__ls__strings.wav";
    HashMap<String, AudioPlayer> soundEffects;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(
                new Dimension(
                        SCREEN_WIDTH,
                        SCREEN_HEIGHT
                )
        );
        this.setBackground(
                new Color(13,150,22)
        );
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

        soundEffects = new HashMap<
                String, AudioPlayer>();
        soundEffects.put("bite",
                new AudioPlayer(biteSoundPath));
        soundEffects.put("gameOver",
                new AudioPlayer(gameOverPath));
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        //playSound(musicPath);
        bgMusic = new AudioPlayer(musicPath);
        bgMusic.play();
    }

    public void newApple(){
        appleX = random.nextInt((int)(
                SCREEN_WIDTH/UNIT_SIZE
                )) * UNIT_SIZE;
        appleY = random.nextInt((int)(
                SCREEN_HEIGHT/UNIT_SIZE
        )) * UNIT_SIZE;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
           /* for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE ; i++) {
                g.drawLine(i * UNIT_SIZE,
                        0,
                        i * UNIT_SIZE,
                        SCREEN_HEIGHT);
                g.drawLine(0,
                        i * UNIT_SIZE,
                        SCREEN_WIDTH,
                        i * UNIT_SIZE);
            }*/
            g.setColor(Color.RED);
            g.fillOval(appleX
                    , appleY,
                    UNIT_SIZE,
                    UNIT_SIZE);

            for (int i = 0; i < bodyParts ; i++) {
                if(i == 0){
                    g.setColor(Color.YELLOW);

                }else{
                    g.setColor(Color.ORANGE);

                }
                g.fillRect(
                        x[i],
                        y[i],
                        UNIT_SIZE,
                        UNIT_SIZE
                );
            }

        }else{
            gameOver(g);
        }

        drawStringInPlace("Score : "
                        + applesEaten,
                g,
                25,
                "right",
                "top");
    }

    public void move(){
        for (int i = bodyParts; i >0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction){
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void playSound(String path){
        File file = new File(path);
        System.out.println(path);
        System.out.println(file.exists());
        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            file
                    );
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

        }catch (
                UnsupportedAudioFileException e){
            System.out.println(e);
        }catch (
                IOException e){
            System.out.println(e);
        }catch(
                LineUnavailableException e){
            System.out.println(e);
        }

    }

    public void playBiteSound(){

        File file = new File(
                "D:\\david\\JavaExamples\\471620__puerta118m__bite.wav");
        System.out.println(file.exists());
        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            file
                    );
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

        }catch (
                UnsupportedAudioFileException e){
            System.out.println(e);
        }catch (
                IOException e){
            System.out.println(e);
        }catch(
                LineUnavailableException e){
            System.out.println(e);
        }


    }

    public void checkApple(){
        if(x[0] == appleX &&
        y[0] == appleY){
           // playBiteSound();
            bgMusic.stop();
            biteSound = new AudioPlayer(biteSoundPath);
            biteSound.play();
            bgMusic.play();
            bodyParts++;
            applesEaten++;
            newApple();


        }
    }

    public void checkCollisions(){
        for (int i = bodyParts; i > 0 ; i--) {
            if(x[0] == x[i]
            && y[0] == y[i]){
                running = false;
            }
        }

        if(x[0] < 0 || x[0] > SCREEN_WIDTH
        || y[0] < 0 || y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running) timer.stop();
    }

    public void drawStringInPlace(
            String str,
            Graphics g,
            int fontSize,
            String positionX,
            String positionY){

        g.setColor(Color.BLUE);
        g.setFont(new Font(
                "Sans",
                Font.BOLD,
                fontSize
        ));
        FontMetrics metrics = getFontMetrics(
                g.getFont()
        );

        int xPos = positionX == "middle"
            ?(SCREEN_WIDTH -
                metrics
                        .stringWidth(
                                str
                        ))/2
            : SCREEN_WIDTH *2 /3;
        int yPos = positionY == "middle"
                ? SCREEN_HEIGHT/2
                : g.getFont().getSize();

        g.drawString(str
                , xPos, yPos
                );
    }

    public void gameOver(Graphics g){
        drawStringInPlace("GaMe OvEr",
                g,
                75,
                "middle",
                "middle");

        bgMusic.close();
        gameOverSound = new AudioPlayer(gameOverPath);
        gameOverSound.play();


    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}
