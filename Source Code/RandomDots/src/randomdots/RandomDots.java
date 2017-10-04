/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomdots;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 *
 * @author anike
 */
public class RandomDots {


    static int TARGETX = 0; // Will se the initial target pos to the middle 
    static int TARGETY = 0; // of the screen in the setup func
    static int x_Cor = 0;
    static int y_Cor = 0;
    static int MAX_FITNESS = 10000;
    static boolean flag = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Particle p = new Particle();
        p.createParticle();
        MovingObj m = new MovingObj(p);
        JFrame f = new JFrame();
        
        f.add(m);
        f.setVisible(true);
        f.setSize(1600, 1000);
        TARGETX = (f.getWidth()) - 200;
        TARGETY = f.getHeight() - 200;
        f.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               
                TARGETX = e.getX();
                TARGETY = e.getY();
                p.gBest = MAX_FITNESS;
               // System.out.println(e.getPoint());
            }
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Moving Swarm Robots Simulation");
    }
}
