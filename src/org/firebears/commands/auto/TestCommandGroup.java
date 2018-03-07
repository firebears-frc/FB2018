package org.firebears.commands.auto;

import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.GetAcceleration;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.WaitForCubeAquisitionCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TestCommandGroup extends CommandGroup {

	public TestCommandGroup() {
		// addParallel(new PlayRecordingCommand("recordings/LeftSideLeftScale.csv"));
		// addSequential(new WaitCommand(5.0));
		// addSequential(new SpinShooterWheelsCommand(.5));
		// addSequential(new WaitCommand(2.0));
		// addSequential(new FireCubeCommand());

		
//		addSequential(new DriveToDistanceStraightCommand(24, -.5));
		
		
		addSequential(new ResetNavX());
		addSequential(new WaitCommand(.25));
    	addSequential(new DriveToDistanceStraightCommand(190, 0.70));
    	addSequential(new WaitCommand(0.5));
    	addSequential(new RelativeAngleCommand(90));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(215, 1.0));
    	
    	
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(0));
    	addSequential(new WaitCommand(.5));
    	addSequential(new DriveToDistanceStraightCommand(54, 0.7));
    	addSequential(new WaitCommand(.5));
    	addSequential(new RelativeAngleCommand(90));
    	

//		addSequential(new ResetNavX());
//		addSequential(new WaitCommand(.25));
//		addSequential(new RelativeAngleCommand(-90));
//		addSequential(new WaitCommand(1));
//		addSequential(new RelativeAngleCommand(180));

	}
}
