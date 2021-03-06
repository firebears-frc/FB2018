package org.firebears.commands.driver;

import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.SpinGrabberWheelsCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;
import org.firebears.commands.grabber.WaitforArmUpCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Move grabber up and other things
 */
public class GrabberUpCommand extends CommandGroup {

    public GrabberUpCommand() {
        addSequential(new OpenGrabberCommand(false));
//    	addSequential(new WaitForCubeAquisitionCommand());
        addSequential(new WaitCommand(.5));
    	addSequential(new RaiseGrabberCommand(true));
//    	addSequential(new WaitCommand(.5));
    	addSequential(new WaitforArmUpCommand());
    	addSequential(new SpinGrabberWheelsCommand(false));
    }
}
