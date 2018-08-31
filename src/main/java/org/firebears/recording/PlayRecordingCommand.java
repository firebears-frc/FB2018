package org.firebears.recording;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Play a previously recorded command.
 */
public class PlayRecordingCommand extends Command {

    private final RecordingFactory factory;
    private Recording recording;
    private long startTime;
    private Iterator<RecordingLine> iter;
    RecordingLine currentLine;
    private boolean useLatestRecording;

    /**
     * Play the most recently recorded {@link Recording} created by any
     * {@link StartRecordingCommand}.
     * 
     * @param factory the current factory for creating {@link Recording} objects.
     */
    public PlayRecordingCommand(RecordingFactory factory) {
        this.factory = factory;
        useLatestRecording = true;
    }

    /**
     * Play a recording saved to a specific file.
     * 
     * @param factory       the current factory for creating {@link Recording}
     *                      objects.
     * @param recordingFile Recording file in CSV format.
     */
    public PlayRecordingCommand(RecordingFactory factory, File recordingFile) {
        this.factory = factory;
        this.recording = loadRecording(recordingFile);
    }

    /**
     * Play a recording saved to a named file. Can play files saved into the
     * {@code FRCUserProgram.jar} as system resources.
     * 
     * @param factory  the current factory for creating {@link Recording} objects.
     * @param fileName File name or resource name for CSV data.
     */
    public PlayRecordingCommand(RecordingFactory factory, String fileName) {
        this.factory = factory;
        if (fileName.startsWith("/tmp/") || fileName.startsWith("/home/lvuser/") || fileName.startsWith("/U/")) {
            File recordingFile = new File(fileName);
            this.recording = loadRecording(recordingFile);
        } else {
            InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
            this.recording = loadRecording(stream);
        }
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
        if (useLatestRecording) {
            recording = loadRecording(StartRecordingCommand.recordingFile);
        }
        if (recording != null) {
            iter = recording.iterator();
            currentLine = iter.hasNext() ? iter.next() : null;
        }
    }

    protected void execute() {
        long currentTime = System.currentTimeMillis() - startTime;
        while (currentLine != null && currentLine.getTime() < currentTime) {
            recording.executeLine(currentLine);
            currentLine = iter.hasNext() ? iter.next() : null;
        }
    }

    @Override
    protected boolean isFinished() {
        return currentLine == null || !iter.hasNext();
    }

    protected void end() {
        iter = null;
        currentLine = null;
        if (useLatestRecording) {
            recording = null;
        }
    }

    private Recording loadRecording(File file) {
        Recording recording = null;
        if (file == null) {
            System.out.println(this.getClass().getSimpleName() + ": loadRecording : null file");
        } else {
            try (Reader reader = new FileReader(file)) {
                recording = factory.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
                recording = null;
            }
        }
        return recording;
    }

    private Recording loadRecording(InputStream stream) {
        Recording recording = null;
        if (stream == null) {
            System.out.println(this.getClass().getSimpleName() + ": loadRecording : null stream");
        } else {
            try (Reader reader = new InputStreamReader(stream)) {
                recording = this.factory.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
                recording = null;
            }
        }
        return recording;
    }
}
