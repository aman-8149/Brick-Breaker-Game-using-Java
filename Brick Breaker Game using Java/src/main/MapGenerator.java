package main;

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 650 / col;
        brickHeight = 400 / row;
    }
    Random rm = new Random();

    public void draw1(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    Color c = new Color(rm.nextInt(256), rm.nextInt(256), rm.nextInt(256));
                    g.setColor(c);
                    g.fillRect(j * brickWidth + 70, i * brickHeight + 40, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 70, i * brickHeight + 40, brickWidth, brickHeight);
                }
            }
        }

    }


    public void setBricksValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
