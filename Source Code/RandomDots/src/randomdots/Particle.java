/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomdots;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author anike
 */
public class Particle {

    final int MAX_VELOCITY = 250; // To the particles from flying to fast
    final int NUM_BOIDS = 40; // Size of the swarm

    static int TARGETX = 0; // Will se the initial target pos to the middle 
    static int TARGETY = 0; // of the screen in the setup func

    int gBestIndex; // Index of global best
    float gBest;  // Global best
    float x, y;                   // current x,y pos
    float pBestX, pBestY;        // best x and y pos 
    float velocityX, velocityY;  // current velocity change to x and y
    float pBest;                 // pBest fitness value
    float fitness;               // current fitness value
    int tx, ty;                  // target x and y pos
    int MAX_FITNESS = 10000;
    ArrayList<Particle> p;
    static boolean flag = true;
    int counter;
    public Particle() {
        p = new ArrayList<>();
        counter = 0;
    }

    public Particle(int mx, int my) {
        pBest = MAX_FITNESS;
        fitness = MAX_FITNESS;
        velocityX = 1;
        velocityY = 1;
        x = (float) (mx + velocityX);
        y = (float) (my + velocityY);
    }

    public void calcFitness(int tx, int ty) {

        // Standard distance equation between two points
        // tx and ty are coordinates of target 
        float xcomp = (x - tx) * (x - tx);
        float ycomp = (y - ty) * (y - ty);
        counter++;
        if((ycomp<=0.1)){
            if (flag == true) {
                flag = false;
                String count = Integer.toString(counter);
                JOptionPane.showMessageDialog(null, "Converged with iteration count =" + count, "InfoBox: " + "Target Found", JOptionPane.INFORMATION_MESSAGE);

            }
        }
        fitness = (float) Math.sqrt(xcomp + ycomp);

        // Check fitness value against pBest value
        if (this.tx == tx && this.ty == ty) {
            // Target is same as before
            if (fitness < pBest) {
                pBest = fitness;
                pBestX = x;
                pBestY = y;
            }
        } else {
            // Target has changed
            this.tx = tx;
            this.ty = ty;
            pBest = fitness;
            flag = true;
            counter =0;
        }
    }

    public void update(Particle gBest) {

        // Constants 
        int c1 = 2;
        int c2 = 2;

        // Implement Eberhart and Kennedy standard velocity change equation
        // Calculate new velocity change for X
        velocityX = (float) (velocityX + (c1 * Math.random() * (pBestX - x))
                + (c2 * Math.random() * (gBest.x - pBestX)));

        // Calculate new velocity change for y
        velocityY = (float) (velocityY + (c1 * Math.random() * (pBestY - y))
                + (c2 * Math.random() * (gBest.y - pBestY)));

        // Rather then checking the bounds
        // we can implement a MAX velocity
        // particles will go off screen but 
        // will be brought back to the target 
        // by the PSO algorithm
        if (velocityX >= MAX_VELOCITY) {
            velocityX = 1;
        }
        if (velocityY >= MAX_VELOCITY) {
            velocityY = 1;
        }

        // Change x and y pos based on velocity
        //0.02 is used to lower down the velocity of particles
        x = x + (velocityX * 0.02f);
        y = y + (velocityY * 0.02f);
        
    }

    public void createParticle() {

        Random rand = new Random();
        gBest = MAX_FITNESS;

        for (int i = 0; i < NUM_BOIDS; i++) {
            Particle part = new Particle((int) (rand.nextInt(1400) * (1 * 0.25)), (int) (rand.nextInt(200) * (1 * 0.25)));
            //Particle part = new Particle((int) (Math.random() * (1 * 0.25)), (int) (Math.random() * (1 * 0.25)));            
            p.add(i, part);
        }
    }
}
