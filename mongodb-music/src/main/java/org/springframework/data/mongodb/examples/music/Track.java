package org.springframework.data.mongodb.examples.music;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * @author Oliver Gierke
 */
public class Track {

    private int number;
    private Stars rating;
    private String name;


    /**
     * Creates a new {@link Track} with the given number and name.
     * 
     * @param number
     * @param name
     */
    public Track(int number, String name) {

        Assert.isTrue(number > -1, "Track number must be positive or 0!");
        Assert.isTrue(StringUtils.hasText(name), "Track name must be given!");

        this.number = number;
        this.name = name;
    }


    public String getName() {

        return name;
    }


    public int getNumber() {

        return number;
    }


    public Stars getRating() {

        return rating;
    }


    public void setRating(Stars rating) {

        this.rating = rating;
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

        Track that = (Track) obj;

        boolean numberEqual = this.number == that.number;
        boolean nameEqual = this.name.equals(that.name);

        return numberEqual && nameEqual;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;
        result += 31 * number;
        result += 31 * name.hashCode();
        return result;
    }
}
