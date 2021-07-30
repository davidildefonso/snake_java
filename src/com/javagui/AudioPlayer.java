package com.javagui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    Clip clip;


    AudioPlayer(String path){
        File file = new File(path);
        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            file
                    );
            clip = AudioSystem.getClip();
            clip.open(audioStream);

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

    public  void play(){
        if(clip == null) return;
        stop();
       // clip.setFramePosition(0);
        clip.start();
    }

    public void stop(){
        if(clip.isRunning()) clip.stop();
    }

    public void close(){
        stop();
        clip.close();
    }
}
