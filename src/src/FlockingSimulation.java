/*
 * Documentation:
 *  Looked up Color choices at https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
 *  Looked up how to initalize an ArrayList at https://www.geeksforgeeks.org/initialize-an-arraylist-in-java/
 *  Looked up how to access an ArrayList at https://howtodoinjava.com/java/collections/arraylist/arraylist-get-method-example/
 *  Looked up Spacebar space key input value at https://css-tricks.com/snippets/javascript/javascript-keycodes/
 *  Looked up how to set a delay https://stackoverflow.com/questions/23283041/how-to-make-java-delay-for-a-few-seconds/48403623
 *  C2C Cooley explained how to implement getAlignmentVector(), getCohesionVector(), getSeparationVector()
 *      (particularly that in the instructions "this boid" meant the parameter and not the presently indexed boid)
 *  C2C Cooley claimed that Boid draw() looks better if fillOval() get size*2 and I and my code agree with that
 */

/*
 * Known Issues:
 *      Boundary attempt mostly fails
 * That's the only issue I've found
 */

package src;

import java.awt.*;

/**
 * Top level class for the Flocking Simulation with the activate() method to run the simulation
 * @author C22Steven.Ford
 */
public class FlockingSimulation {
    /* Fields */
    private final java.awt.Color BACKGROUND_COLOR = Color.WHITE;
    private boolean pause;
    private final int SCREEN_HEIGHT = 500;
    private final int SCREEN_WIDTH = 500;

    /* Constructors */

    /**
     * Zero argument constructor for the FlockingSimulation
     */
    FlockingSimulation() {
        this.pause = false;
    }

    /* Action Functions */

    /**
     * Sets up the DrawingPanel, creates the flocks, and runs the animation to include pause/resume via spacebar, disruptions (via a left mouse click), and exits with a right mouse click
     */
    void activate() {
        //Set up DrawingPanel
        DrawingPanel window = new DrawingPanel(SCREEN_WIDTH, SCREEN_HEIGHT); //automatically calls DrawingPanel.run()
        window.setBackground(BACKGROUND_COLOR); //set background color
        Boid.setDrawingPanel(window, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Create flocks
        Flock blueFlock = new Flock("Blue", 10, Color.BLUE);
        Flock redFlock = new Flock("Red", 10, Color.RED);

        int spaceVal = ' '; //get space bar key value
        //Run the animation to include pause/resume via space bar
        while (!window.isMouseButtonDown(DrawingPanel.RIGHT_BUTTON)) { //run until right click
            //Update the screen
            try {
                Thread.sleep(100); //update every 0.1 seconds
            }
            catch (Exception e) {
                throw new IllegalArgumentException();
            }
            window.setBackground(BACKGROUND_COLOR);
            blueFlock.draw();
            redFlock.draw();
            window.copyGraphicsToScreen();
            //Move each flock
            blueFlock.move();
            redFlock.move();
            if (pause = window.keyHasBeenHit(spaceVal)) { //true if space bar hit
                while (pause) {
                    //be able to exit while paused
                    if (window.isMouseButtonDown(DrawingPanel.RIGHT_BUTTON)) {
                        endGame(window);
                    }
                    window.waitForKey();
                    if (window.getKeyHitCode() == spaceVal) { //key input was space key
                        pause = false; //exit loop
                    }
                }
            }
            if (window.isMouseButtonDown(DrawingPanel.LEFT_BUTTON)) {
                //Handle disruptions
                blueFlock.evade(window.getMouseClickX(DrawingPanel.LEFT_BUTTON), window.getMouseClickY(DrawingPanel.LEFT_BUTTON));
                redFlock.evade(window.getMouseClickX(DrawingPanel.LEFT_BUTTON), window.getMouseClickY(DrawingPanel.LEFT_BUTTON));
            }
        }
        endGame(window);
    }


    /**
     * Ends Game
     * @param window Current DrawingPanel object
     */
    void endGame(DrawingPanel window) {
        //Clean up nicely on right click
        window.closeWindow();
        System.exit(0);
    }
}
