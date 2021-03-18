package src;

//todo Add "java.lang.Exception - -" to headers

import java.util.ArrayList;

/**
 * The Flock class is an aggregation of Boid objects all with similar characteristics to include those that dictate its flocking behaviors of alignment, cohesion, and separation.
 * @author C22Steven.Ford
 */
public class Flock {
    /* Fields */
    private java.util.ArrayList<Boid> flock;
    private String name;
    private int radiusAlignment = 40;
    private int radiusCohesion = 20;
    private int radiusEvasion  = 10;
    private int radiusSeparation = 10;
    private double weightAlignment = 0.2;
    private double weightCohesion = 0.2;
    private double weightCurrentVelocity = 0.4;
    private double weightSeparation = 0.2;

    /* Constructors */
    /**
     * Zero-argument flock constructor creates a Flock object with the given name and the number of Boid objects controlled by the count argument.
     * @param name name to be associated with the new Flock object
     * @param count number of Boid objects to create in the flock
     * @param flockColor the color of the flock
     */
    Flock(String name, int count, java.awt.Color flockColor) {
        this.name = name;
        flock = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) { //initialize flock w/ boids
            Boid obj = new Boid();
            obj.setColor(flockColor);
            obj.setSize(10); //provide a default other than 0
            obj.setSpeed(20); //provide a default other than 0
            flock.add(obj);
        }
    }

    /**
     * Flock full-argument constructor setting flock name, count, color, size and speed.
     * @param name name of the new flock
     * @param count number of Boids in the new flock
     * @param color color of each Boid in the flock
     * @param size size of each Boid in the new flock
     * @param speed speed of each Boid in the new flock
     */
    Flock(java.lang.String name, int count, java.awt.Color color, int size, double speed) {
        this.name = name;
        flock = new ArrayList<>();
        for (int i = 0; i < count; ++i) { //initialize flock w/ boids
            Boid obj = new Boid();
            obj.setColor(color);
            obj.setSize(size);
            obj.setSpeed(speed);
            flock.add(obj);
        }
    }

    /**
     * Creates a new flock with the sepcified parameters. Key difference here is
     * that Boids are to be represented with the 'image' BufferedImage object instead of a color.
     * @param name String to name the flock
     * @param count number of boids in the flock
     * @param image image to render for each boid in the flock
     * @param size how large each boid is when rendered
     * @param speed  how fast these boids move
     */
    Flock(java.lang.String name, int count, java.awt.image.BufferedImage image, int size, double speed) {
        this.name = name;
        flock = new ArrayList<>();
        for (int i = 0; i < count; ++i) { //initialize flock w/ boids
            Boid obj = new Boid();
            obj.setImage(image);
            obj.setSize(size);
            obj.setSpeed(speed);
            flock.add(obj);
        }
    }

    /* Action Methods */
    /**
     * Calculates the Eucleadean distance between the locations of Boids a and b.
     * @param a first boid
     * @param b second boid
     * @return the distance between the locations of Boids a and b
     */
    double distance(Boid a, Boid b) {
        double xDiff = a.getLocation().getX()-b.getLocation().getX();
        double yDiff = a.getLocation().getY()-b.getLocation().getY();
        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    /**
     * Draws each of the Boids in the flock
     */
    void draw() {
        for (Boid boid : flock) {
            boid.draw();
        }
    }

    /**
     * Invokes the evade behavior moving each Boid in the flock that is close
     * enough (radiusEvasion) to the disruption point to move directly away from that point
     * @param x x coordinate of the disruption point
     * @param y y coordinate of the disruption point
     */
    void evade(int x, int y) {
        for (Boid boid : flock) {
            boid.evade(x, y, this.radiusEvasion);
        }
    }

    /**
     * Moves the flock first by computing a new velocity for each boid in the flock. Once all the boids have their new velocity, update the velocity and apply it to move each boid.
     */
    void move() {
        Vector330Class tmpAlignment;
        Vector330Class tmpCohesion;
        Vector330Class tmpSeparation;
        double scaledSpeed;

        Vector330Class newVelocity;
        for (Boid boid : flock) {
            //Create the new velocity vector by multiplying each of the following by their
            //respective weights, adding the resulting vectors together, and then scaling the
            //sum vector by the flock’s speed.
            tmpAlignment = getAlignmentVector(boid).scale(weightAlignment);
            tmpCohesion = getCohesionVector(boid).scale(weightCohesion);
            tmpSeparation = getSeparationVector(boid).scale(weightSeparation);
            scaledSpeed = (boid.getSpeed()) * weightCurrentVelocity;
            newVelocity = tmpAlignment.add(tmpCohesion).add(tmpSeparation).scale(scaledSpeed);
            boid.setNewVelocity(newVelocity);
        }
        //After all boids in the flock have their new velocities, make their new velocities into their
        //current velocities, move them, and draw them.
        for (Boid boid : flock) {
            //Set current velocity to new velocity
            boid.setVelocity(boid.getNewVelocity());
            //Move them
            boid.move();
            //Draw them
            draw();
        }
    }

    /**
     * Calculates the alignment unit vector for Boid b to align itself
     * with near neighbors (within radiusAlignment) of itself
     * @param b current boid
     * @return a normalized vector in the average direction of Boid b's neighbors
     */
    Vector330Class getAlignmentVector(Boid b) {
        Vector330Class sumAlignmentVectors = new Vector330Class();
        //Get average position of boids w/in radius of current boid b
        for (Boid boid : flock) {
            //Check that boid is w/in current boid b's radius
            if (distance(boid, b) < radiusCohesion) {
                //Add the other boid’s velocity vector to a sum of velocities vector.
                sumAlignmentVectors = sumAlignmentVectors.add(boid.getLocation());
            }
        }
        //Compute the alignment vector as the unit vector of the sum of velocity vectors.
        return sumAlignmentVectors.normalize();
    }

    /**
     * Calculates the average location of the near-neighbors (those within radiusCohesion) and then creates a unit cohesion vector to that point
     * @param b the current Boid object being considered
     * @return the unit cohesion vector
     */
    private Vector330Class getCohesionVector(Boid b) {
        int numBoidsInCohesionRadius = 0;
        Vector330Class sumCohesionVectors = new Vector330Class();
        Vector330Class avgSumCohesionVectors;
        //Get average position of boids w/in radius of current boid b
        for (Boid boid : flock) {
            //Check that boid is w/in current boid b's radius
            if (distance(boid, b) < radiusCohesion) {
                sumCohesionVectors = sumCohesionVectors.add(boid.getLocation());
                ++numBoidsInCohesionRadius;
            }
        }
        avgSumCohesionVectors = sumCohesionVectors.scale((double)1/numBoidsInCohesionRadius); //divide to get average

        //Compute cohesion vector
        return (avgSumCohesionVectors.subtract(b.getLocation())).normalize();
    }

    /**
     * Calculates the separation unit vector so the current Boid does not encrotch upon its nearest neighbors (those within radiusSeparation)
     * @param b current boid
     * @return a normalized vector pointing away from the neighbors
     */
    private Vector330Class getSeparationVector(Boid b) {
        Vector330Class sumSeparationVectors = new Vector330Class();
        Vector330Class avgSumSeparationVectors;
        int numBoidsInSeparationRadius = 0;
        //Get average position of boids w/in radius of current boid b
        for (Boid boid : flock) {
            //Check that boid is w/in current boid b's radius
            if (distance(boid, b) < radiusCohesion) {
                //Compute offset vector
                Vector330Class offsetV = b.getLocation().subtract(boid.getLocation());
                //Compute the unit vector of the offset vector and scale it by the separation
                //radius minus the distance between the current boid’s position and the
                //other boid’s position
                Vector330Class scaledUnitOffsetV = (offsetV.normalize()).scale(radiusSeparation - distance(b, boid));

                //Add the vector from the previous calculation to a running sum vector.
                sumSeparationVectors = sumSeparationVectors.add(scaledUnitOffsetV);
                ++numBoidsInSeparationRadius;
            }
        }
        avgSumSeparationVectors = sumSeparationVectors.scale((double)1/numBoidsInSeparationRadius);
        //Compute the separation vector as the unit vector of the average separation vector.
        return avgSumSeparationVectors.normalize();
    }

    /* Setter methods */
    void setRadiusAlignment(int radiusAlignment) {
        this.radiusAlignment = radiusAlignment;
    }

    void setRadiusCohesion(int radiusCohesion) {
        this.radiusCohesion = radiusCohesion;
    }

    void setRadiusEvasion(int radiusEvasion) {
        this.radiusEvasion = radiusEvasion;
    }

    void setRadiusSeparation(int radiusSeparation) {
        this.radiusSeparation = radiusSeparation;
    }

    void setWeightAlignment(double weightAlignment) {
        this.weightAlignment = weightAlignment;
    }

    void setWeightCohesion(double weightCohesion) {
        this.weightCohesion = weightCohesion;
    }

    void setWeightSeparation(double weightSeparation) {
        this.weightSeparation = weightSeparation;
    }
}
