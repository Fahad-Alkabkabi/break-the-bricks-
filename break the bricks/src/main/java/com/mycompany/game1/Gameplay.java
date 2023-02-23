package com.mycompany.game1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // for moving the ball
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; // for detecting the arrow keys
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JPanel;

// this class for the main gameplay

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false; // for the Main screen
    private int score = 0; //starting score

    private int totalBricks = 21; //number of bricks

    private Timer timer;
    private int delay = 8; // player speed = 8

    private int playerX = 310; // starting position for the player
    Random r = new Random();// to get random position for the ball every time
    private int ballposX = r.nextInt(650-90)+90; // boundaries for the ball starting position
    private int ballposY = r.nextInt(370-290)+290; // boundaries for the ball starting position
    private int ballXdir = -2; // ball direction
    private int ballYdir = -3; // ball direction

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this); // setting the key
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer timer = new Timer(delay, this);// object for player speed
        timer.start();
    }
    // this method for designing the gameplay and graphics

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);

        //drawing map
        map.draw( (Graphics2D)g);

        // borders
        g.setColor(Color.green);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(680, 0, 3, 592);

        // scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        // the paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);

        // this method for wining
        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won: "+score, 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        // this method for losing
        if(ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }


@Override
// this method for the movement of the ball and how should it react with the puddle and the bricks
public void actionPerformed(ActionEvent e) {
    if(play) { // this statement for the reaction if the ball touches the puddle
        if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
            ballYdir = -ballYdir; // will go to the opposite
        }
        for(int i = 0; i<map.map.length; i++) { // loop to detect the action between the ball and the bricks
            for(int j = 0; j<map.map[0].length; j++) {
                if(map.map[i][j] > 0) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                    Rectangle brickRect = rect;

                    if(ballRect.intersects(brickRect)) {
                        map.setBrickValue(0, i, j);// if the ball touches the brick the value will be 0
                        totalBricks--;
                        score += 5;

                        if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                            ballXdir = -ballXdir;
                        } else {
                            ballYdir = -ballYdir;
                        }

                        break;

                    }
                }
            }
        }

        ballposX += ballXdir;
        ballposY += ballYdir;
        if(ballposX < 0) { // for the left border
            ballXdir = -ballXdir;
        }
        if(ballposY < 0) { // for the top border
            ballYdir = -ballYdir;
        }
        if(ballposX > 670) { // for the right border
            ballXdir = -ballXdir;
        }
    }

    repaint();
}

@Override
public void keyTyped(KeyEvent e) {}
@Override
public void keyReleased(KeyEvent e) {}
@Override

// this method detecting the arrow keys
public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // the right key
        if(playerX >=600) {
            playerX = 600;
        } else {
            moveRight();
        }
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT) { // the left key
        if(playerX < 10) {
            playerX = 10;
        } else {
            moveLeft();
        }
    }
    if(e.getKeyCode() == KeyEvent.VK_ENTER) { // enter to restart
        if(!play) {
            play = true;

            ballposX = r.nextInt(600-100)+100;
            ballposY = r.nextInt(370-290)+290;
            ballXdir = -1;
            ballYdir = -2;
            playerX = 310;
            score = 0;
            totalBricks = 21;
            map = new MapGenerator(3, 7);

            repaint();
        }
    }
}
//this method to move the puddle to  the right
public void moveRight() {
    play = true;
    playerX+=20;
}
//this method to move the puddle to  the left
public void moveLeft() {
    play = true;
    playerX-=20;
}


}
