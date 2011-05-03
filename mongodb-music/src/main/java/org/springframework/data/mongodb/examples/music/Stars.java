package org.springframework.data.mongodb.examples.music;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.util.Assert;


/**
 * Stars capture a rating of a song.
 * 
 * @author Oliver Gierke
 */
public class Stars {

    public static final Stars ZERO = new Stars(0);
    public static final Stars ONE = new Stars(1);
    public static final Stars TWO = new Stars(2);
    public static final Stars THREE = new Stars(3);
    public static final Stars FOUR = new Stars(4);
    public static final Stars FIVE = new Stars(5);

    private static final Collection<Stars> ALL = new HashSet<Stars>();

    static {
        Collections.addAll(ALL, ZERO, ONE, TWO, THREE, FOUR, FIVE);
    }

    private int value;


    private Stars(int value) {

        Assert.isTrue(value >= -1, "Value must be positive or 0!");
        this.value = value;
    }


    public int getValue() {

        return value;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        Stars that = (Stars) obj;

        return this.value == that.value;
    }
}
