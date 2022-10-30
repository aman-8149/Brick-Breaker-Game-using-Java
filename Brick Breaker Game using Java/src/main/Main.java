
package main;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
       JFrame obj=new JFrame();
       Gameplay gamePlay=new Gameplay();
       obj.add(gamePlay);
       obj.setBounds(10,10,800,1000);
       obj.setTitle("Brick Breaker Game");
       obj.setLocation(500,0);
       obj.setResizable(false);
       obj.setVisible(true);
       
       obj.setDefaultCloseOperation(obj.EXIT_ON_CLOSE);
    }
    
}
