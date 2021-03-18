package src;

import java.util.Random;

/**
 * Boid class provides the single entity level for members of the flocks.
 * @author C22Steven.Ford
 */
public class Boid {
    /* Fields */
    private java.awt.Color color;
    private static java.awt.Graphics2D g;
    private java.awt.image.BufferedImage image;
    private Vector330Class location;
    private Vector330Class newVelocity = new Vector330Class();
    private static DrawingPanel panel;
    private static java.util.Random rand = new Random(System.currentTimeMillis());
    private static int screenHeight;
    private static int screenWidth;
    private int size;
    private double speed;
    private Vector330Class velocity;

    /* Constructors */
    /**
     * Constructor that creates the Boid with a random location and a random 360 deg velocity
     */
    Boid() {
        location = new Vector330Class(rand.nextInt(screenWidth), rand.nextInt(screenHeight));
        velocity = new Vector330Class(rand.nextInt(20), rand.nextInt(20));
    }

    /* Action Methods */
    /**
     * Draws the Boid at its current location on the DrawingPanel
     */
    void draw() {
        //Draw on graphics2D plane as circle w/ radius 1/2 size around x,y point
        Boid.g.setColor( this.color );
        Boid.g.fillOval( (this.getLocation().getXint() - this.size),
                (this.getLocation().getYint() - this.size), (2 * this.size), (2 * this.size) );
        //Draw Vector (line) indicating direction
        Vector330Class endV = this.location.add(getVelocity().normalize().scale(2*this.size));
        Boid.g.drawLine(this.getLocation().getXint(), this.getLocation().getYint(), endV.getXint(), endV.getYint());
    }

    /**
     * Move the Boid away from the (x,y) disruption position passed in so that
     * the Boid is the evadeRadius away from the disruption point.
     * @param mouseX x coordinate of the disruption point
     * @param mouseY y coordinate of the disruption point
     * @param evadeRadius distance used to detect if evasion is needed and to determine how far away to move
     */
    void evade(int mouseX, int mouseY, int evadeRadius) {
        int xDiff = (int)Math.abs(mouseX-location.getX());
        int yDiff = (int)Math.abs(mouseY-location.getY());
        if ( (xDiff <= evadeRadius) && (yDiff <= evadeRadius) ) { //if boid w/in click radius
            //Generate a perpendicular (direction) vector of bound/xDiff magnitude
            double xComponent = this.location.getX()-mouseX;
            double yComponent = this.location.getY()-mouseY;
            Vector330Class perpendicularVector = new Vector330Class(xComponent, yComponent);
            Vector330Class normPerpendicularVector = perpendicularVector.normalize(); //new velocity
            this.setVelocity(normPerpendicularVector);
            //Add new perpendicular normalized velocity to our current position
            move();
        }
    }

    /**
     * Moves the Boid per its current velocity vector with either bouncing or wrapping behavior on the edges
     */
    void move() {
        int lowerBound = 40;
        int upperBound = screenHeight-40;
        //Switch Y velocity at top and bottom boundaries
        if ((this.location.getYint() < lowerBound) || (this.location.getYint() > upperBound)) {
            velocity.setY(velocity.getYint()*-1);
        }
        int leftBound = 40;
        int rightBound = screenWidth-40;
        if ((this.location.getXint() < leftBound) || (this.location.getXint() > rightBound)) { //left boundary
            velocity.setX(velocity.getXint()*-1);
        }
        this.location = this.location.add(velocity); //Vector333Class.add() returns a new object
    }

    /**
     * Sets the current velocity to the new velocity vector; this allows the new velocity calculation to be based upon current velocities of the other Boids.
     */
    void updateVelocity() {
        velocity = newVelocity;
    }

    /*
    Setter/Getter Funcs
    */
    /**
     * Updates this Boid's velocity based upon the velocity vector passed in
     * @param v new velocity vector passed in
     */
    void setVelocity(Vector330Class v) {
        this.velocity = v;
    }

    /**
     * Change the Boid's speed
     * @param speed new speed
     */
    void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Change the Boid's size
     * @param size new size for the Boid
     */
    void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets the new velocity vector based upon the one provided (via alias)
     * @param nv new velocity vector passed in
     */
    void setNewVelocity(Vector330Class nv) {
        this.newVelocity = nv;
    }

    /**
     * Updates this Boid's location based upon the location vector passed in
     * @param l new location vector passed in
     */
    void setLocation(Vector330Class l) {
        this.location = l;
    }

    /**
     * Update the Boid's image for display
     * @param image new image for the Boid
     */
    void setImage(java.awt.image.BufferedImage image) {
        this.image = image;
    }

    /**
     * Sets an association of the Boid class to the DrawingPanel that it will reside in
     * @param panel the new DrawingPanel object
     * @param width width of the DrawingPanel
     * @param height height of the DrawingPanel
     */
    static void setDrawingPanel(DrawingPanel panel, int width, int height) {
        Boid.panel = panel;
        Boid.screenWidth = width;
        Boid.screenHeight = height;
        Boid.g = panel.getGraphics();
    }

    /**
     * Set the Boid's color
     * @param color new color
     */
    void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     *
     * @return location
     */
    Vector330Class getLocation() {
        return location;
    }

    /**
     *
     * @return speed
     */
    double getSpeed() {
        return speed;
    }

    /**
     *
     * @return velocity
     */
    Vector330Class getVelocity() {
        return velocity;
    }

    /**
     *
     * @return newVelocity
     */
    Vector330Class getNewVelocity() {
        return newVelocity;
    }

}
