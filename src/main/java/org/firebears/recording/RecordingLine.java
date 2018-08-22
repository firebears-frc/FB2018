package org.firebears.recording;

/**
 * Represents one line in a {@link Recording}.
 */
public class RecordingLine implements Comparable<RecordingLine> {

    final private long time;
    final private double[] data;

    /**
     * Create one new {@code RecordingLine} based on the current values of all the
     * {@code Recordables}.
     * 
     * @param currentTime
     *            Current time in milliseconds.
     * @param recordables
     *            An array of recordable objects.
     */
    RecordingLine(long currentTime, Recordable... recordables) {
        this.time = currentTime;
        data = new double[recordables.length];
        for (int i = 0; i < recordables.length; i++) {
            data[i] = recordables[i].get();
        }
    }

    /**
     * Create one new {@code RecordingLine} based on a sequence of numbers.
     * 
     * @param currentTime
     *            Current time in milliseconds.
     * @param datapoint
     *            an array of numbers.
     */
    RecordingLine(long currentTime, double... datapoint) {
        this.time = currentTime;
        data = new double[datapoint.length];
        for (int i = 0; i < datapoint.length; i++) {
            data[i] = datapoint[i];
        }
    }

    /**
     * Determine if a line is greater-than, equal-to, or less-than the other line.
     * Supports the {@link Comparable} interface.
     * 
     * @param other another line
     * @return an integer greater-than, equal-to, or less-than zero.
     */
    @Override
    public int compareTo(RecordingLine other) {
        return Long.compare(this.time, other.time);
    }

    /**
     * @param previousTime
     *            time in milliseconds.
     * @return a comma-separated-value line.
     */
    String toCsv(long previousTime) {
        StringBuffer sb = new StringBuffer();
        sb.append(time - previousTime);
        for (int i = 0; i < data.length; i++) {
            sb.append(',').append(data[i]);
        }
        return sb.toString();
    }

    /**
     * @return time in milliseconds, relative to the beginning of this
     *         {@link Recording}.
     */
    long getTime() {
        return time;
    }
    
    /**
     * Set all the data values into the settable recordables.
     * This will cause all motors to execute at this lines values.
     */
    void executeLine(Recordable... recordables)  {
        for (int i = 0; i < data.length; i++) {
            if (recordables[i].isSettable()) {
                recordables[i].set(data[i]);
            }
        }
    }
}
