package org.firebears.commands.driver;

import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.SpinGrabberWheelsCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class DriverCloseCommand extends CommandGroup {

    public DriverCloseCommand() {
    	addSequential(new SpinGrabberWheelsCommand(true));
        addSequential(new OpenGrabberCommand(false));
        addSequential(new WaitForCubeAquisitionCommand());
    	addSequential(new WaitCommand(.25));
        addSequential(new SpinGrabberWheelsCommand(false));
    }
}
