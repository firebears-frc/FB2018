package org.firebears.recording;

/**
 * Represents something connected the robot that can be recorded, and
 * potentially set later.
 */
public interface Recordable {

    /**
     * @return a unique label. Must not be null.
     */
    String getLabel();

    /**
     * @return the current value to be recorded.
     */
    double get();

    /**
     * Determine if this is a playable object. Non-settable objects may be recorded
     * for information purposes only. For instance, you may choose to record gyro or
     * accelerometer readings, even though you can't play those back explicitly.
     * 
     * @return whether values can be played back on the object.
     */
    default boolean isSettable() {
        return true;
    }

    /**
     * Play a value back to the object. If the object is non-settable, nothing bad
     * should happen.
     * 
     * @param value
     *            new value to be set.
     */
    void set(double value);

}
