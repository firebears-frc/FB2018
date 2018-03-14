package org.firebears.commands.driver;

import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.SpinGrabberWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * put the arm down and other thigns
 */
public class GrabberDownCommand extends CommandGroup {

    public GrabberDownCommand() {
    	addSequential(new SpinGrabberWheelsCommand(true));
    	addSequential(new RaiseGrabberCommand(false));
    	addSequential(new WaitCommand(2));
    	addSequential(new OpenGrabberCommand(true));
    }
}
