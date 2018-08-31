package org.firebears.recording;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Creates and manipulates {@link Recording} objects.
 */
public class RecordingFactory {

    private List<Recordable> recordableList = new ArrayList<>();

    /**
     * @param recordable a {@code Recordable}.
     */
    public void add(Recordable recordable) {
        recordableList.add(recordable);
    }

    /**
     * @param recordables list of  {@code Recordable} objects.
     */
    public void addAll(List<Recordable> recordables) {
        for (Recordable recordable : recordables) {
            recordableList.add(recordable);
        }
    }

    /**
     * @param speedController a {@code SpeedController}.
     * @param label           a unique label for the {@code SpeedController}.
     */
    public void add(SpeedController speedController, String label) {
        recordableList.add(new SpeedControllerRecordable(speedController, label));
    }

    /**
     * @param joystick a {@code Joystick}.
     * @param axisType one axis on the {@code Joystick}.
     * @param label    a unique label for the {@code SpeedController}.
     */
    public void add(Joystick joystick, Joystick.AxisType axisType, String label) {
        recordableList.add(new JoystickRecordable(joystick, axisType, label));
    }

    /**
     * @return a new {@code Recording}.
     */
    public Recording newRecording() {
        Recordable[] recordables = recordableList.toArray(new Recordable[recordableList.size()]);
        return new Recording(recordables);
    }

    /**
     * @param in the {@code Reader} on which to load a CSV file.
     * @return a new {@code Recording}.
     */
    public Recording load(Reader in) {
        Recording recording = newRecording();
        try (BufferedReader reader = new BufferedReader(in)) {
            long currentTime = 0;
            double[] datapoint = new double[recordableList.size()];
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\"")) {
                    continue;
                }
                long deltaTime = readDataLine(line, datapoint);
                currentTime += deltaTime;
                recording.add(new RecordingLine(currentTime, datapoint));
            }
        } catch (IOException e) {
            e.printStackTrace();
            recording = null;
        }
        return recording;
    }

    /**
     * @param line      one CSV line.
     * @param datapoint an array of numbers. Must already be of the correct length.
     *                  As a side-effect, data will be written into this array.
     * @return the value of the first column on the CSV line. Will be the number of
     *         milliseconds for which these values should be set back into a
     *         {@code Recordable}.
     */
    private long readDataLine(String line, double[] datapoint) {
        String[] s = line.split(",");
        long deltaTime = Long.parseLong(s[0]);
        for (int i = 0; i < datapoint.length; i++) {
            datapoint[i] = Double.parseDouble(s[i + 1]);
        }
        return deltaTime;
    }

    public void save(Recording recording, Writer out, String comments) {
        try (PrintWriter writer = new PrintWriter(out)) {
            if (comments != null && !comments.isEmpty()) {
                writer.printf("\"%s\"%n", comments);
            }
            writer.printf("%s%n", makeHeaderCsv());
            long previousTime = 0;
            for (RecordingLine recordingLine : recording) {
                writer.printf("%s%n", recordingLine.toCsv(previousTime));
                previousTime = recordingLine.getTime();
            }
        }
    }

    private String makeHeaderCsv() {
        StringBuffer sb = new StringBuffer();
        sb.append("\"timeDelta\"");
        for (Recordable recordable : recordableList) {
            sb.append(',').append('"').append(recordable.getLabel()).append('"');
        }
        return sb.toString();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * An object for recording the values on a {@link SpeedController}.
     */
    public static class SpeedControllerRecordable implements Recordable {

        private final SpeedController speedController;
        private final String label;

        /**
         * @param speedController a @code {@link SpeedController}.
         * @param label           a unique label.
         */
        public SpeedControllerRecordable(SpeedController speedController, String label) {
            this.speedController = speedController;
            this.label = label;
        }

        /**
         * @return the unique label for this {@code Recordable}.
         */
        @Override
        public String getLabel() {
            return label;
        }

        /**
         * @return the current value on this {@code SpeedController}.
         */
        @Override
        public double get() {
            return speedController.get();
        }

        /**
         * @param value the value to set into this {@code SpeedController}.
         */
        @Override
        public void set(double value) {
            this.speedController.set(value);
        }
    }

    /**
     * An object for recording the values from one axis on a {@link Joystick}. This
     * {@link Recordable} is not settable.
     */
    public static class JoystickRecordable implements Recordable {

        private final Joystick joystick;
        private final Joystick.AxisType axisType;
        private final String label;

        /**
         * @param joystick the {@code Joystick}.
         * @param axisType the axis to record.
         * @param label    a unique label.
         */
        public JoystickRecordable(Joystick joystick, Joystick.AxisType axisType, String label) {
            this.joystick = joystick;
            this.axisType = axisType;
            this.label = label;
        }

        /**
         * @return the unique label for this {@code Recordable}.
         */
        @Override
        public String getLabel() {
            return label;
        }

        /**
         * @return the value for this joystick's axis.
         */
        @Override
        public double get() {
            return joystick.getAxis(axisType);
        }

        /**
         * @return false, since this {@code Recordable} may not have values set into it.
         */
        @Override
        public boolean isSettable() {
            return false;
        }

        /**
         * @param value a value.
         */
        @Override
        public void set(double value) {
        }
    }

    /**
     * A {@link Recordable} that exists purely for testing purposes.
     */
    public static class FakeRecordable implements Recordable {
        private final String label;

        /**
         * @param label a unique label.
         */
        public FakeRecordable(String label) {
            this.label = label;
        }

        /**
         * @return the unique label for this {@code Recordable}.
         */
        @Override
        public String getLabel() {
            return label;
        }

        /**
         * @return a random value.
         */
        @Override
        public double get() {
            double value = Math.random() * 2.0 - 1.0;
            System.out.printf("get(%f)%n", value);
            return value;
        }

        /**
         * @param value a value.
         */
        @Override
        public void set(double value) {
            System.out.printf("set(%f)%n", value);
        }
    }
}
