package com.charlie.rainbowdinosaur;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    GameEngine dinoRainbows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize the GameEngine object
        // Pass it the screen size (height & width)
        dinoRainbows = new GameEngine(this, size.x, size.y);

        // Make GameEngine the view of the Activity
        setContentView(dinoRainbows);
    }

    // Android Lifecycle functions
    // ----------------------------

    // This function gets run when user switches from the game to some other app on the phone
    @Override
    protected void onPause() {
        super.onPause();

        // Pause the game
        dinoRainbows.pauseGame();
    }

    // This function gets run when user comes back to the game
    @Override
    protected void onResume() {
        super.onResume();

        // Start the game
        dinoRainbows.startGame();
    }
}
