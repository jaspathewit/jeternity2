/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

/**
 * Encapsulates a side of a tile each side has a Background and
 * Foreground Colour and Pattern.
 */
public class Side {
    public static final Side EDGE = new Side(Colour.EDGE, Colour.EDGE, Pattern.EDGE);

    private final Colour background;
    private final Colour foreground;
    private final Pattern pattern;

    /**
     * Constructor
     *
     * @param background
     * @param foreground
     * @param pattern
     */
    public Side(Colour background, Colour foreground, Pattern pattern) {
        this.background = background;
        this.foreground = foreground;
        this.pattern = pattern;
    }

    public String getEdgeKey() {
        return background.code() + foreground.code() + pattern.code();
    }

    /**
     * @return the background
     */
    public Colour getBackground() {
        return background;
    }

    /**
     * @return the foreground
     */
    public Colour getForeground() {
        return foreground;
    }

    /**
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String result = background + "-" + foreground + "-" + pattern;
        return result;
    }

}
