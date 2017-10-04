/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomdots;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

/**
 *
 * @author anike
 */
public class MovingObj extends JPanel implements ActionListener {

    Timer t = new Timer(15, this);

    Particle p;

    public MovingObj() {
    }

    public MovingObj(Particle part) {
        this.p = part;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D target = (Graphics2D) g;
        Ellipse2D targetcircle = new Ellipse2D.Double(RandomDots.TARGETX, RandomDots.TARGETY, 30, 30);
        target.fill(targetcircle);
        
        for (int i = 0; i < p.NUM_BOIDS; i++) {
            Graphics2D g0 = (Graphics2D) g;
            Ellipse2D circle0 = new Ellipse2D.Double(p.p.get(i).x, p.p.get(i).y, 10, 10);
            g0.fill(circle0);
        }    
        t.start();
    }
    

    public void actionPerformed(ActionEvent e) {

        updateSwarm();
        repaint();
    }

    void updateSwarm() {
        // Calculate fitness for swarm
        for (int i = 0; i < 40; i++) {
            p.p.get(i).calcFitness(RandomDots.TARGETX, RandomDots.TARGETY);
        }
        // Store gBest
        for (int i = 0; i < 40; i++) {

            if (p.p.get(i).pBest < p.gBest) {
                p.gBest = p.p.get(i).pBest;
                p.gBestIndex = i;
            }
        }
        // Update velocity
        for (int i = 0; i < 40; i++) {
            if (i != p.gBestIndex) {
                p.p.get(i).update(p.p.get(p.gBestIndex));
            }
        }
        
     
    }
}
