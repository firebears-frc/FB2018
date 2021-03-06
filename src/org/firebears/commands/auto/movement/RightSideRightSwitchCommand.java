package org.firebears.commands.auto.movement;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.RelativeAngleCommandFast;
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
public class RightSideRightSwitchCommand extends CommandGroup {

    public RightSideRightSwitchCommand() {
//        addSequential(new PlayRecordingCommand("recordings/RightSideRightSwitch.csv"));
    	
    	addSequential(new DriveToDistanceStraightCommand(60, .4));
		addSequential(new DriveToDistanceStraightCommand(48, .8));
		addSequential(new WaitCommand(.5));
		addSequential(new RelativeAngleCommandFast(90), 2);
//		addSequential(new DriveToDistanceStraightCommand(24, -.5));
//		addSequential(new WaitCommand(.5));
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new DriveToDistanceStraightCommand(10, 0.5));
		addSequential(new DriveToDistanceStraightCommand(40, -.35),1.0);
		addSequential(new SpinShooterWheelsCommand(.1));
		addSequential(new WaitCommand(.25));
		addSequential(new FireCubeCommand());
//        addSequential(new ActivateCompressor(true));
		
		
//		addSequential(new WaitCommand(.5));
//		addSequential(new RaiseGrabberCommand(false));
//		addSequential(new WaitCommand(2.5));
//		addSequential(new ReverseGrabberWheelsCommand(true));
//		addSequential(new WaitCommand(3.0));
//        addSequential(new ActivateCompressor(true));
//		addSequential(new RaiseGrabberCommand(true));
//		addSequential(new ReverseGrabberWheelsCommand(false));
//		addSequential(new OpenGrabberCommand())

    }
}
