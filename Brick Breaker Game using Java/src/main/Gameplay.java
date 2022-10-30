package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Gameplay extends JPanel implements ActionListener, KeyListener {

    private boolean play = false;
    private int score = 0;

    private Timer timer, t;
    private int delay = 8;

    private int playerX = 250;
    private int velx = 60;

    private int ballposX = 120;
    private int ballposY = 600;
    private float ballXdir = -6;
    private float ballYdir = -12;

    Random parameter = new Random();
    private int randrow = parameter.nextInt(20);
    private int randcol = parameter.nextInt(20);

    private int totalBricks = randrow * randcol;

    private MapGenerator map;

    public Gameplay() {

        map = new MapGenerator(randrow, randcol);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //background
        ImageIcon img = new ImageIcon("D:\\aman\\clg practical\\java\\Main\\src\\main\\background.jpg");
        Image newimg = img.getImage().getScaledInstance(800, 1000, Image.SCALE_DEFAULT);
        img = new ImageIcon(newimg);
        img.paintIcon(this, g, 1, 1);

        //map generate
        map.draw1((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 1000);
        g.fillRect(0, 0, 1000, 3);
        g.fillRect(780, 0, 3, 1000);

        //SCORE
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("SCORE: " + score, 590, 30);

        //create paddle
        g.setColor(Color.red);
        g.fillRect(playerX, 920, 90, 8);

        //create ball
        ImageIcon img1 = new ImageIcon("D:\\aman\\clg practical\\java\\Main\\src\\main\\ball3.png");
        Image newimg1 = img1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        img1 = new ImageIcon(newimg1);
        img1.paintIcon(this, g, ballposX, ballposY);

        //won and loose text
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("You Won!,  Scores: " + score, 250, 500);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter To restart ", 250, 540);

        }

        if (ballposY > 930) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Game Over,  Scores: " + score, 250, 500);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter To restart ", 250, 540);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {

        if (play) {

            //intersection of the ball with paddle
            if (new Rectangle(ballposX, ballposY, 30, 30).intersects(new Rectangle(playerX, 910, 100, 8))) {
                ballYdir = -ballYdir;
            }
            // intersection with brick

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 70;
                        int brickY = i * map.brickHeight + 40;
                        int brickwidth = map.brickWidth;
                        int brickheight = map.brickHeight;
                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        //if ball  rectangle intersects with brick rectangle make bricks array 0 and decrement the bricks and increment the scores
                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 1;

                            //after intersect intersecting with bricks if it intersects with x wall it sends back to opposite side
                            if (ballposX + 19 <= brickrect.x || ballposX + 10 >= brickrect.x) {
                                ballXdir = -ballXdir;
                            } else {
                                //after intersect intersecting with bricks if it intersects with y wall it sends back to opposite side
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            //moving ball and changing its position
            ballposX += ballXdir;
            ballposY += ballYdir;

            //if ball in x direction is less then send back it to its oppostite side for (left side) 
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }

            //if ball in y direction is less then send back it to its oppostite side for (top side) 
            if (ballposY < 0) {
                ballYdir = -ballYdir;

            }

            //if ball in x direction is greater then send back it to its oppostite side for (right side) 
            if (ballposX > 740) {
                ballXdir = -ballXdir;
                // System.out.println("greater ballXdir"+ballposX);
            }

            //restrict paddle shuld not go less then 0
            if (playerX < 0) {
                playerX = 0;
                velx = 0;
            }
            //restrict paddle should not go greater then 600
            if (playerX > 700) {
                playerX = 700;
                velx = 0;
            }
            playerX += velx;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        // System.out.println("pressed");
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 600;
                ballXdir = -6;
                ballYdir = -12;
                playerX = 250;
                score = 0;
                Random parameter = new Random();
                randrow = parameter.nextInt(20);
                randcol = parameter.nextInt(20);
                map = new MapGenerator(randcol, randcol);
                totalBricks = randcol * randcol;
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        velx = 15;
    }

    public void moveLeft() {
        play = true;
        velx = -15;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        velx = 0;
    }

}
