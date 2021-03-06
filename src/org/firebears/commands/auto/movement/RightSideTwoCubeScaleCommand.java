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
public class RightSideTwoCubeScaleCommand extends CommandGroup {

    public RightSideTwoCubeScaleCommand() {
    	addSequential(new RightSideRightScaleCommand());
		addSequential(new SpinShooterWheelsCommand(0));
		addSequential(new RotateToAngleCommandFast(200), 2);
		addSequential(new VisionGetCubeCommandGroup());
		addSequential(new DriveToDistanceStraightCommand(24, -.5));
		addSequential(new SpinShooterWheelsCommand(.7));
		addSequential(new RotateToAngleCommandFast(120), 2);
//		addSequential(new DriveToDistanceStraightCommand(6, .25), 1);
//		addSequential(new WaitCommand(.5));
		
		// Remove if not necessary on competition bot
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new WaitCommand(.25));
//		addSequential(new OpenGrabberCommand(false));
//		addSequential(new WaitCommand(.25));
		
		addSequential(new FireCubeCommand());
    }
}
