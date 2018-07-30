package org.firebears.recording;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to stop the recording begun by {@link StartRecordingCommand}.
 */
public class StopRecordingCommand extends InstantCommand {
    
    private final RecordingFactory factory;
    
    public StopRecordingCommand(RecordingFactory factory)  {
        this.factory = factory;
    }

    protected void initialize() {
        StartRecordingCommand.isRecording = false;
    }

}
