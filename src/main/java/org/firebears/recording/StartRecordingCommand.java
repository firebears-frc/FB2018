package org.firebears.recording;

import edu.wpi.first.wpilibj.command.Command;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create a recording as a CSV file on the RoboRIO.  New files will be named based on the current
 * date and time.  If a USB drive is plugged into the robot, all recordings will be saved to that drive.
 */
public class StartRecordingCommand extends Command {
    static boolean isRecording = false;

    private final RecordingFactory factory;
    private Recording recording;
    private long startTime;
    protected static File recordingFile;

    public StartRecordingCommand(RecordingFactory factory) {
        this.factory = factory;
    }

    protected void initialize() {
        isRecording = true;
        recording = factory.newRecording();
        startTime = System.currentTimeMillis();
        recordingFile = findTemporaryFile();
    }

    protected void execute() {
        long currentTime = System.currentTimeMillis() - startTime;
        recording.addLine(currentTime);
    }

    protected boolean isFinished() {
        return !isRecording;
    }

    protected void end() {
        String comments = "Temporary recording as of " + (new Date());
        try (PrintWriter out = new PrintWriter(recordingFile)) {
            factory.save(recording, out, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recording = null;
    }

    public static File findTemporaryFile() {
        File dir = new File("/U/");
        if (!dir.exists()) {
            dir = new File(System.getProperty("java.io.tmpdir"));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm"); 
        String fileName = String.format("recording-%s.csv", dateFormat.format(new Date()));
        File file = new File(dir, fileName);
        return file;
    }
}
