package org.firebears.commands;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to stop stop the recording called from the smartDashboard.
 */
public class StopRecordingCommand extends Command {

    
    public StopRecordingCommand() {
    }
   
    protected void initialize() {
    	StartRecordingCommand.recording = false;
    }
   
    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }
  
    protected void end() {
    }

    
    protected void interrupted() {
    }
}
