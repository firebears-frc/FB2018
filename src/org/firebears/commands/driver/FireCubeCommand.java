package org.firebears.commands.driver;

import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.shooter.ExtendShooterCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *	for shooting the cube
 */
public class FireCubeCommand extends CommandGroup {

    public FireCubeCommand() {
    	addSequential(new OpenGrabberCommand(true));
    	addSequential(new WaitCommand(.5));
        addSequential(new ExtendShooterCommand(true));
        addSequential(new WaitCommand(.75));
        addSequential(new ExtendShooterCommand(false));
//        addSequential(new SpinShooterWheelsCommand(0));
        addSequential(new OpenGrabberCommand(false));
    }
}
