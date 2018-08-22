package org.firebears.recording;

import java.util.*;

/**
 * A sequence of {@link RecordingLine} objects. Note that the first
 * {@link RecordingLine} in a {@link Recording} should have a time value of
 * zero.
 */
public class Recording extends LinkedList<RecordingLine> {

    final private Recordable[] recordables;

    /**
     * @param recordables
     *            an array of {@code Recordable} objects.
     */
    Recording(Recordable... recordables) {
        this.recordables = recordables;
    }

    /**
     * Add one {@link RecordingLine} to this {@link Recording} based on the current
     * values in all current {@code Recordables}.
     * 
     * @param currentTime
     *            time in milliseconds, relative to the start of the recording.
     * @return a new {@code RecordingLine}.
     */
    public RecordingLine addLine(long currentTime) {
        RecordingLine recordingLine = new RecordingLine(currentTime, recordables);
        add(recordingLine);
        return recordingLine;
    }

    /**
     * Set all the data values into the recordables. This will cause all motors to
     * execute at the recordingLine's values.
     * 
     * @param recordingLine
     *            a line that will be set into all {@code Recordables} for the given
     *            delta time.
     */
    public void executeLine(RecordingLine recordingLine) {
        recordingLine.executeLine(recordables);
    }
}
