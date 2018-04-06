package org.firebears.commands.auto;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.GetAcceleration;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.VisionGetCubeCommandGroup;
import org.firebears.commands.auto.movement.RightSideRightScaleCommand;
import org.firebears.commands.auto.movement.RightSideRightSwitchCommand;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.ReverseGrabberWheelsCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestCommandGroup extends CommandGroup {

	public TestCommandGroup() {

//		addSequential(new RightSideRightScaleCommand());
//		addSequential(new SpinShooterWheelsCommand(0));
//		addSequential(new RelativeAngleCommand(80));
//		addSequential(new VisionGetCubeCommandGroup());
//		addSequential(new RelativeAngleCommand(180));
//		addSequential(new SpinShooterWheelsCommand(.4));
//		addSequential(new WaitCommand(.5));
//		addSequential(new DriveToDistanceStraightCommand(36, -.5));
//		
//		// Remove if not necessary on competition bot
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new WaitCommand(.25));
//		addSequential(new OpenGrabberCommand(false));
//		addSequential(new WaitCommand(.25));
//		
//		addSequential(new FireCubeCommand());
		addSequential(new RelativeAngleCommand(-110));
		addSequential(new VisionGetCubeCommandGroup());
		addSequential(new DriveToDistanceStraightCommand(24, -.5));
		addSequential(new SpinShooterWheelsCommand(.6));
		addSequential(new WaitCommand(.25));
		addSequential(new RelativeAngleCommand(60));
		addSequential(new WaitCommand(.5));
		
		// Remove if not necessary on competition bot
		addSequential(new OpenGrabberCommand(true));
		addSequential(new WaitCommand(.25));
		addSequential(new OpenGrabberCommand(false));
		addSequential(new WaitCommand(.25));
		
		addSequential(new FireCubeCommand());
		
//		addSequential(new RightSideRightSwitchCommand());
//		addSequential(new DriveToDistanceStraightCommand(24, -.5));
//		addSequential(new WaitCommand(.5));
//		addSequential(new RelativeAngleCommand(90));
//		addSequential(new WaitCommand(.5));
//		addSequential(new DriveToDistanceStraightCommand(36, .5));
//		addSequential(new WaitCommand(.5));
//		addSequential(new RelativeAngleCommand(-90));
//		addSequential(new WaitCommand(.5));
//		addSequential(new DriveToDistanceStraightCommand(36, .5));
//		addSequential(new WaitCommand(.5));
//		addSequential(new RelativeAngleCommand(-90));
//		addSequential(new WaitCommand(.5));
//		addSequential(new VisionGetCubeCommandGroup());
//		addSequential(new RelativeAngleCommand(180));
//		addSequential(new SpinShooterWheelsCommand(.4));
//		addSequential(new WaitCommand(.5));
//		addSequential(new DriveToDistanceStraightCommand(36, -.5));
//		
//		// Remove if not necessary on competition bot
//		addSequential(new OpenGrabberCommand(true));
//		addSequential(new WaitCommand(.25));
//		addSequential(new OpenGrabberCommand(false));
//		addSequential(new WaitCommand(.25));
//		
//		addSequential(new FireCubeCommand());
	}
}
