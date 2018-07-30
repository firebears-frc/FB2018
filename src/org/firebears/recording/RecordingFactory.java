package org.firebears.recording;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    public void add(Recordable recordable) {
        recordableList.add(recordable);
    }

    public void add(SpeedController speedController, String label) {
        recordableList.add(new SpeedControllerRecordable(speedController, label));
    }

    public void add(Joystick joystick, Joystick.AxisType axisType, String label) {
        recordableList.add(new JoystickRecordable(joystick, axisType, label));
    }

    public Recording newRecording() {
        Recordable[] recordables = recordableList.toArray(new Recordable[recordableList.size()]);
        return new Recording(recordables);
    }

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

    private static class SpeedControllerRecordable implements Recordable {

        private final SpeedController speedController;
        private final String label;

        private SpeedControllerRecordable(SpeedController speedController, String label) {
            this.speedController = speedController;
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public double get() {
            return speedController.get();
        }

        @Override
        public void set(double value) {
            this.speedController.set(value);
        }
    }

    private static class JoystickRecordable implements Recordable {

        private final Joystick joystick;
        private final Joystick.AxisType axisType;
        private final String label;

        private JoystickRecordable(Joystick joystick, Joystick.AxisType axisType, String label) {
            this.joystick = joystick;
            this.axisType = axisType;
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public double get() {
            return joystick.getAxis(axisType);
        }

        @Override
        public boolean isSettable() {
            return false;
        }

        @Override
        public void set(double value) {
        }
    }

    private static class FakeRecordable implements Recordable {
        private final String label;

        private FakeRecordable(String label) {
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public double get() {
            double value = Math.random() * 2.0 - 1.0;
            System.out.printf("get(%f)%n", value);
            return value;
        }

        @Override
        public void set(double value) {
            System.out.printf("set(%f)%n", value);
        }
    }

    // public static void main(String[] arg) throws InterruptedException,
    // IOException {
    // RecordingFactory f = new RecordingFactory();
    // f.add(new FakeRecordable("left"));
    // f.add(new FakeRecordable("right"));
    //
    // Recording recording1 = f.newRecording();
    // long startTime = System.currentTimeMillis();
    // for (int i = 0; i < 8; i++) {
    // long currentTime = System.currentTimeMillis() - startTime;
    // recording1.addLine(currentTime);
    // Thread.sleep(20);
    // }
    //
    // java.io.File desktop = new java.io.File(System.getProperty("user.home"),
    // "Desktop");
    // java.io.File outFile1 = new java.io.File(desktop, "q1.csv");
    // Writer out = new FileWriter(outFile1);
    // f.save(recording1, out, "First test");
    // out.close();
    //
    // Reader in = new FileReader(outFile1);
    // Recording recording2 = f.load(in);
    // in.close();
    //
    // java.io.File outFile2 = new java.io.File(desktop, "q2.csv");
    // out = new FileWriter(outFile2);
    // f.save(recording2, out, "second test");
    // out.close();
    // }
}
