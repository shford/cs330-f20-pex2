/**
 * <h1>Vector Calculator</h1>
 * Vector330Class - provides a 2D vector with associated operations and support * <p>
 * <b>Vector330Class - provides a 2D vector with associated operations and support.</b>
 *
 * @author  C2C Hampton Ford
 * @version 1.0
 */

 /*
 * Documentation:
 * Made JUnit5 test using instructions from https://www.jetbrains.com/help/idea/2020.2/create-tests.html?utm_source=product&utm_medium=link&utm_campaign=IU&utm_content=2020.2
 * Refreshed on exception handling at https://www.w3schools.com/java/java_try_catch.asp
 * atan2() documentation at https://www.tutorialspoint.com/java/number_atan2.htm
 * Looked up .contains after Intellij said indexOf() was bad https://www.w3schools.com/java/ref_string_contains.asp
 * Looked up java indexOf() to test if char is in string https://stackoverflow.com/questions/506105/how-can-i-check-if-a-single-character-appears-in-a-string
 * Java String Length https://www.tutorialspoint.com/java/java_string_length.htm
 * Looked up Java string formatting https://dzone.com/articles/java-string-format-examples
 * Looked up what a dot product is https://www.onlinemathlearning.com/image-files/dot-product.png
 * Java absolute value https://www.google.com/search?client=firefox-b-1-d&q=java+math+absolute+value
 * Java split string https://stackoverflow.com/questions/10631715/how-to-split-a-comma-separated-string
 * More java atan() docs https://stackoverflow.com/questions/41085949/java-how-to-calculate-degree-from-tan
 * Java arctangent https://www.tutorialspoint.com/java/number_atan.htm
 * Java printf double https://stackoverflow.com/questions/3853185/double-formatting-question-for-printf-in-java
 * How to use java PI https://stackoverflow.com/questions/12594058/how-to-use-math-pi-in-java/12594064
 * Looked up what scalar multiplication was https://www.khanacademy.org/math/linear-algebra/vectors-and-spaces/vectors/v/multiplying-vector-by-scalar
 * Built regex expression with https://www.freeformatter.com/java-regex-tester.html#ad-output
 * IDE Configuration https://www.jetbrains.com/help/go/creating-and-editing-run-debug-configurations.html
 * Fixed IDE issue https://intellij-support.jetbrains.com/hc/en-us/community/posts/206815235--Cannot-resolve-symbol-for-all-the-Java-classes
 * Resolved Intellij reinstall project issues https://intellij-support.jetbrains.com/hc/en-us/community/posts/207036035-Package-name-Foo-does-not-correspond-to-the-file-path-
 */
package src;

/**
 * Vector333Class - provides a 2D vector with associated operations and support
 * @author C22Steven.Ford
 */
public class Vector330Class {
    /*Fields*/
    private double x;
    private double y;
    private static final double EPS = 1E-9; //epsilon value

    /*Constructors*/
    /**
     * Vector333Class - zero argument constructor
     */
    public Vector330Class() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Vector330Class - constructor initializing both x and y components with ints
     * @param x - new value for x component of the vector to be typecast to double
     * @param y - new value for y component of the vector to be typecast to double
     */
    public Vector330Class(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vector330Class - constructor initializing both x and y components with longs
     * @param x - new long value for x component of the vector to be typecast to double
     * @param y - new long value for y component of the vector to be typecast to double
     */
    public Vector330Class(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vector330Class - constructor initializing both x and y components with doubles
     * @param x - new value for x component of the vector
     * @param y - new value for y component of the vector
     */
    public Vector330Class(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*Operator Methods*/
    /**
     * direction() - computes and returns the direction (orientation) of the vector in radians
     * @return angle Direction of the vector in radians
     */
    public double direction() {
        return Math.atan2(this.x, this.y); //returns radians by default
    }

    /**
     * equals() - checks for vector equality if this and the other (v) vector have components within epsilon of each other
     * @param v - other vector passed in
     * @return boolean true if this and other vectors are close enough, else false
     */
    public boolean equals(Vector330Class v) {
        //get differences
        double xDiff = Math.abs(this.x - v.x);
        double yDiff = Math.abs(this.y - v.y);
        if (xDiff <= EPS && yDiff <= EPS) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * add() - does vector addition of this vector with the one passed in
     * @param v - other vector passed in
     * @return vector sum of this and the other vector
     */
    public Vector330Class add(Vector330Class v) {
        //add vectors
        Vector330Class summedVector = new Vector330Class();
        summedVector.x = this.x + v.x;
        summedVector.y = this.y + v.y;
        return summedVector;
    }

    /**
     * subtract() - subtracts passed in vector from this vector
     * @param v - other vector passed in
     * @return vector difference of this vector minus the other vector passed in
     */
    public Vector330Class subtract(Vector330Class v) {
        //subtract vectors
        Vector330Class subtractedVector = new Vector330Class();
        subtractedVector.x = this.x - v.x;
        subtractedVector.y = this.y - v.y;
        return subtractedVector;
    }

    /**
     * dotProduct() - computes the dot product of this vector and the other vector
     * @param v - other vector to compute the dot product with
     * @return the scalar (double) dot product of this vector and the other vector
     */
    public double dotProduct(Vector330Class v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * scale() - does a scalar-vector multiplication of this vector with double value passed in
     * @param s Scalar multiple
     * @return scaledVector New scaled vector object from Vector333Class
     */
    public Vector330Class scale(double s) {
        //scale vector
        Vector330Class scaledVector = new Vector330Class();
        scaledVector.x = this.x * s;
        scaledVector.y = this.y * s;
        return scaledVector;
    }

    /**
     * magnitude() - computes the magnitude (2-norm or length) of this vector
     * @return magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y); //use distance formula to get magnitude
    }

    /**
     * normalize() - creates a normalized (of length one) vector in same direction as this vector
     * @return the normalized vector or the zero vector is original vector is close to zero in magnitude
     */
    public Vector330Class normalize() {
        Vector330Class normalizedVector = new Vector330Class(1, 1);
        if (this.x == 0 && this.y == 0) {
            return normalizedVector; //avoids divide by 0 rules
        }
        //divide by distances to normalize
        normalizedVector.x = this.x / Math.sqrt(this.x * this.x + this.y * this.y);
        normalizedVector.y = this.y / Math.sqrt(this.x * this.x + this.y * this.y);
        return normalizedVector;
    }

    /**
     * toString() - overrides the default toString() method producing an angle-bracket version of this vector
     * @return string representation of the vector in the form " 3.0, 4.0 "
     */
     @Override
    public String toString() {
        return String.format("< %f, %f >", this.x, this.y);
    }

    /**
     * parseVector() - inputs a Scanner object from which it reads and parses a string representing the vector with and expected form of " 3.0, 4.0 " - note spaces are needed after the opening vector sign, after the comma, and before the closing vector sign.
     * @param s - Scanner object from which to read the String representation of the vector
     * @return a new Vector330Class object based upon the provided input
     * @throws Exception - object with the message set to a description of the parsing error encountered
     */
    public static Vector330Class parseVector(java.util.Scanner s) throws Exception {
        try {
            //create the return vector
            Vector330Class newVector = new Vector330Class();
            //split string vector
            String rawVector = s.nextLine(); //looks like "< x, y >"
            String[] fields = rawVector.split(" "); //something like ["<", "x,", "y", ">"] so indices 1 and 2 are always x and y
            //remove comma from x val
            String xVal = fields[1];
            String[] xValFields = xVal.split(",");
            //convert values
            newVector.x = Double.parseDouble(xValFields[0]); //.parseDouble already handles errors
            newVector.y = Double.parseDouble(fields[2]);
            return newVector;
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    /*Getter Methods*/
    /**
     * getX() - returns x component of vector as a double
     * @return value of x component as a double
     */
    public double getX() {
        return this.x;
    }

    /**
     * getY() - return the y component of vector as a double
     * @return value of y component as a double
     */
    public double getY() {
        return this.y;
    }

    /**
     * getXint() - return the x component of vector as an int
     * @return value of x component as an int
     */
    public int getXint() {
        return (int)this.x;
    }

    /**
     * getYint() - returns the y component of vector as an int
     * @return value of y component as an int
     */
    public int getYint() {
        return (int)this.y;
    }

    /**
     * getXlong() - returns the x component of vector as a long
     * @return value of x component as a long
     */
    public long getXlong() {
        return (long)this.x;
    }

    /**
     * getYlong() - returns the y component of vector as a long
     * @return value of y component as a long
     */
    public long getYlong() {
        return (long)this.y;
    }

    /**
     * setX( double ) - sets the x component of vector using an input of type double
     * @param x - new value for x component as a double
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * setY( double ) - sets the y component of vector using an input of type double
     * @param y - new value of y component as a double
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * setX( int ) - sets the x component of vector using an input of type int (to be typecast to double)
     * @param x - new value of x component as an int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setY( int ) - sets the y component of vector using an input of type int (to be typecast to double)
     * @param y - new value of y component as an int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * setX( long )sets the x component of vector using an input of type long (to be typecast to double)
     * @param x - new value of x component as a long
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * setY( long ) - sets the y component of vector using an input of type long (to be typecast to double)
     * @param y - new value of y component as a long
     */
    public void setY(long y) {
        this.y = y;
    }
}
