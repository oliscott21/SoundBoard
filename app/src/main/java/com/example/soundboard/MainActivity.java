package com.example.soundboard;

/*
    @author: Oliver Lester
    @description: This java class holds all the necessary components for the SoundBoard app to work.
        It holds the logic for the app to have a button that cycles between red, yellow, and
        green. Also having a second meaning to the button, that being a delay associated with the
        button, a 0, 1.5, and 3 second delay. It has the logic to play four distinct sounds when
        their associated buttons are pushed. The playing happens after the delay's state when
        clicking the button. These plays are done on threads which allows for no locking when using
        delays. Meaning, a sound with a 3 second delay won't stop a sound with a 0 second delay
        from playing. It will stay play on pushing the button, and if pushed before the three
        seconds, it will play before the other sound.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int toggle_code;

    /**
     * This function takes in a bundle and creates the UI.
     * This function takes in one Bundle object as a parameter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle_code = 0;
    }

    /**
     * This method is used by the TOGGLE DELAY button in the activity. It uses the int toggle_code
     *      to determine the color of the button; 0 = red, 1 = yellow, 2 = green. It then changes
     *      the value of the button to the next color needed; red to yellow, yellow to green, and
     *      green back to red. It also changes the toggle_code to the value of the color changed to.
     * This method takes in a single View object as a parameter.
     */
    public void toggle(View view) {
        Drawable buttonDrawable = view.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        if (toggle_code == 0) {
            DrawableCompat.setTint(buttonDrawable, getColor(R.color.toggle_yellow));
            toggle_code = 1;
        } else if (toggle_code == 1) {
            DrawableCompat.setTint(buttonDrawable, getColor(R.color.toggle_green));
            toggle_code = 2;
        } else {
            DrawableCompat.setTint(buttonDrawable, getColor(R.color.toggle_red));
            toggle_code = 0;
        }
        view.setBackground(buttonDrawable);
    }

    /**
     * This inner class is responsible for implementing the threading of the app. It will play the
     *      MediaPlayer given after the given delay. It will then release the MediaPlayer after it
     *      finishes playing.
     */
    public static class PlayRunnable implements Runnable {

        MediaPlayer mp;
        long sleep;

        /**
         * This is the constructor of the PlayRunnable class. It takes in two parameters and sets
         *      the mp and sleep variables to or based of these parameters.
         * This constructor takes in a MediaPlayer object which is the one this thread will play.
         *      It also takes in a int which will be used to determine how long the thread sleeps.
         */
        public PlayRunnable(MediaPlayer mp, int delay) {
            this.mp = mp;
            if (delay == 0){
                sleep = 0;
            } else if (delay == 1){
                sleep = 1500;
            } else {
                sleep = 3000;
            }
        }

        /**
         * This method is responsible for the run portion of the thread. It first sleeps based off
         *      the delay associated with the thread, this can be 0, 1.5, or three seconds. It then
         *      starts the MediaPlayer. Finally releasing the MediaPlayer once done playing.
         * This method takes in no parameters.
         */
        @Override
        public void run() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mp.start();
            mp.setOnCompletionListener(MediaPlayer::release);
        }
    }

    /**
     * This method is used by one of the buttons in the activity to play the sound associated with
     *      the hello.wav file. It does so by creating a thread of the class PlayRunnable which
     *      implements Runnable. The thread starts here.
     * This method takes in a single View object as a parameter.
     */
    public void playHello(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.hello);
        PlayRunnable task = new PlayRunnable(mp, toggle_code);
        Thread t = new Thread(task);
        t.start();
    }

    /**
     * This method is used by one of the buttons in the activity to play the sound associated with
     *      the thank_you.wav file. It does so by creating a thread of the class PlayRunnable which
     *      implements Runnable. The thread starts here.
     * This method takes in a single View object as a parameter.
     */
    public void playThankYou(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.thank_you);
        PlayRunnable task = new PlayRunnable(mp, toggle_code);
        Thread t = new Thread(task);
        t.start();
    }

    /**
     * This method is used by one of the buttons in the activity to play the sound associated with
     *      the more_effort.wav file. It does so by creating a thread of the class PlayRunnable
     *      which implements Runnable. The thread starts here.
     * This method takes in a single View object as a parameter.
     */
    public void playExert(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.more_effort);
        PlayRunnable task = new PlayRunnable(mp, toggle_code);
        Thread t = new Thread(task);
        t.start();
    }

    /**
     * This method is used by one of the buttons in the activity to play the sound associated with
     *      the food.wav file. It does so by creating a thread of the class PlayRunnable which
     *      implements Runnable. The thread starts here.
     * This method takes in a single View object as a parameter.
     */
    public void playFood(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.food);
        PlayRunnable task = new PlayRunnable(mp, toggle_code);
        Thread t = new Thread(task);
        t.start();
    }
}