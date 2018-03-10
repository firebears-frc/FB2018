package org.firebears.commands.auto.movement;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.ReverseGrabberWheelsCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class MiddleSideLeftSwitchCommand extends CommandGroup {

    public MiddleSideLeftSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/MiddleSideLeftSwitch.csv"));
    	addSequential(new DriveToDistanceStraightCommand(12, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(-60));
		addSequential(new WaitCommand(.7));
		addSequential(new DriveToDistanceStraightCommand(36, .5));
		addSequential(new WaitCommand(.7));
		addSequential(new RotateToAngleCommand(-120));
		addSequential(new WaitCommand(.5));
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new DriveToDistanceStraightCommand(45, 1.0));
		addSequential(new DriveToDistanceStraightCommand(60, -.5));
		addSequential(new SpinShooterWheelsCommand(.1));
		addSequential(new WaitCommand(.25));
		addSequential(new FireCubeCommand());
		
		
//		addSequential(new WaitCommand(.5));
//		addSequential(new RaiseGrabberCommand(false));
//		addSequential(new WaitCommand(2.5));
//		addSequential(new ReverseGrabberWheelsCommand(true));
//		addSequential(new WaitCommand(3.0));
//        addSequential(new ActivateCompressor(true));
//		addSequential(new RaiseGrabberCommand(true));
//		addSequential(new ReverseGrabberWheelsCommand(false));

    }
}
