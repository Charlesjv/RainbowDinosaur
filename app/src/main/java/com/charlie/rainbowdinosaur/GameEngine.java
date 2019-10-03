package com.charlie.rainbowdinosaur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    int LINE_POSITION = 70;




    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    Player player;
    Item item;

    Player enemy;

    String fingerAction = "";


    int mouseXPosition;
    int mouseYPosition;


    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // ----------------------------
    // ## GAME STATS
    // ----------------------------


    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        this.player = new Player(this.getContext(),screenWidth/2+300, 100);

        this.item = new Item(this.getContext(),80,LINE_POSITION - 50);



    }




    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    public void updatePositions() {



//        this.item.setxPosition(this.player.getxPosition()- 10);
//        this.item.updateHitbox();

//            this.item.getImage().setxPosition(this.item.getxPosition() +10);
//            this.item.updateHitbox();

            this.item.xPosition = this.item.xPosition + 15;



//            if(fingerAction == "mouseup"){
//
//                this.player.setyPosition(this.player.getyPosition() - 70);
//                this.player.updateHitbox();
//            }else if(fingerAction == "mousedown"){
//
//                this.player.setyPosition(this.player.getyPosition() + 70);
//                this.player.updateHitbox();
//
//            }



    }



//    public void mouseDistance( int mousex1, int mousey1, int playerx2, int playery2){
//
//        double a = (mousex1 - playerx2);
//        double b = (mousey1 - playery2);
//        double distance = Math.sqrt((a * a) + (b * b));
//
//        // 2. calculate the "rate" to move
//        double xn = (a / distance);
//        double yn = (b / distance);
//
//        // 3. move the bullet
//        playerx2 = playerx2 + (int) (xn * 15);
//        playery2 = playery2 + (int) (yn * 15);
//
////        Log.d(TAG, "Position of bullet " + i + ": (" + bull.x + "," + bull.y + ")");
////
//
//    }



    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);



            //Draw player on the screen

            this.canvas.drawBitmap(this.player.getImage(),screenWidth/2 + 300 ,100,paintbrush);


            //Draw lines on the screen
            paintbrush.setColor(Color.BLACK);

            for(int i = 1; i <= 4; i++) {
                this.canvas.drawLine(0, LINE_POSITION * i, this.screenWidth - 170, LINE_POSITION * i, paintbrush);

            }


            //DRAWING ENEMIES ON THE SCREEN

            this.canvas.drawBitmap(this.item.getImage(),80,LINE_POSITION - 50 ,paintbrush);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);




            // Players Hitbox
            this.canvas.drawRect(this.player.getHitbox().left,this.player.getHitbox().top, this.player.getHitbox().right,this.player.getHitbox().bottom,paintbrush);

            //Enemy Hitbox

            this.canvas.drawRect(this.item.getHitbox().left,this.item.getHitbox().top, this.item.getHitbox().right,this.item.getHitbox().bottom,paintbrush);

            //Score Label
            paintbrush.setTextSize(50);
            this.canvas.drawText("Score: 0 ",this.screenWidth/2 + 40, 30,paintbrush);


            // Lives Label

            paintbrush.setTextSize(50);
            this.canvas.drawText("Lives = 3 ",this.screenWidth/2 + 40, 300,paintbrush);
            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------




    int middle = 148;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            mouseXPosition = (int) event.getX();
            mouseYPosition = (int) event.getY();

            Log.d(TAG,"Mouse x "+"Mouse Y "+mouseXPosition+","+mouseYPosition);

            if(mouseYPosition > middle){




               this.player.setyPosition(this.player.getyPosition() + 10);
                this.player.updateHitbox();



            }else if(mouseYPosition < middle){


                this.player.setyPosition(this.player.getyPosition() - 10);
                this.player.updateHitbox();
            }


        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}
