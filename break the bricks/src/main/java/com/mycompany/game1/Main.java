package com.mycompany.game1;

import javax.swing.JFrame;

// this class for starting the game
public class Main {

    public static void main(String[] args) {
        //app creation
        JFrame obj = new JFrame (); // this statement for calling Jframe and make the window for the game
        Gameplay gameplay = new Gameplay(); // making object for the gameplay class
        obj.setBounds(10, 10, 700, 600);//window boundaries
        obj.setTitle("Break The Bricks");
        obj.setResizable(false); // this statement does not allow the user to change the size of the window
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this statement to exit the game
        obj.add(gameplay); // to start the game




    }

}
