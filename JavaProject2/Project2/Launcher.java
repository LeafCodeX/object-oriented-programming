package Project2;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                Program program = new Program("Project 2 - Virtual world design in JAVA");
                program.display();
            }
        });
    }
}