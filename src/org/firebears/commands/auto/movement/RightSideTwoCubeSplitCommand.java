package org.firebears.commands.auto.movement;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.RelativeAngleCommandFast;
import org.firebears.commands.RotateToAngleCommandFast;
import org.firebears.commands.VisionGetCubeCommandGroup;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RightSideTwoCubeSplitCommand extends CommandGroup {

    public RightSideTwoCubeSplitCommand() {
    	addSequential(new RightSideRightScaleCommand());
		addSequential(new SpinShooterWheelsCommand(0));
		addSequential(new RotateToAngleCommandFast(200), 2);
		addSequential(new VisionGetCubeCommandGroup());
		addSequential(new RelativeAngleCommandFast(180), 2);
		addSequential(new SpinShooterWheelsCommand(.4));
		addSequential(new WaitCommand(.5));
		addSequential(new DriveToDistanceStraightCommand(36, -.5));
		
		// Remove if not necessary on competition bot
//		addSequential(new WaitCommand(.5));
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new WaitCommand(.25));
//		addSequential(new OpenGrabberCommand(false));
//		addSequential(new WaitCommand(.25));
		
		addSequential(new FireCubeCommand());
    }
}
