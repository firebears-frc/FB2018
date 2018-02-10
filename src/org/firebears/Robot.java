// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.firebears;
import java.io.File;

import org.firebears.commands.*;
import org.firebears.commands.auto.DriveToDistanceCommand;
import org.firebears.subsystems.*;
import org.firebears.util.RobotReport;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	public static OI oi;
	public static Vision vision;
	public static AutoSelection autoSelection;
	public static Chassis chassis;
	public static Lights lights;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotReport report = new RobotReport("FB2018");
		RobotMap.init(report);
		chassis = new Chassis();
		autoSelection = new AutoSelection();
		vision = new Vision();
		lights = new Lights();

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI(report);

		// Add commands to Autonomous Sendable Chooser
		// chooser.addDefault("Autonomous Command", new AutonomousCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		report.write(new File(System.getProperty("user.home"), "robotReport.md"));
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to
	 * reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// System.out.println("joytickbutton1: " + oi.joystick1.getRawButton(1));
		// System.out.println("joytickYAxis: "+ oi.joystick1.getRawAxis(1));
		// System.out.println("joytickXAxis: "+ oi.joystick2.getX());
		double x = oi.joystick2.getX();
		double y = oi.joystick2.getY();
		double z = oi.joystick2.getZ();

		if (x >= .33) {
			RobotMap.side = "Right";
		} else if (x <= -.33) {
			RobotMap.side = "Left";
		} else {
			RobotMap.side = "Middle";
		}
		if (y >= 0) {
			RobotMap.priority = "Switch";
		} else if (y < 0) {
			RobotMap.priority = "Scale";
		}
		if (z >= 0) {
			RobotMap.shouldCross = true;
		} else if (y < 0) {
			RobotMap.shouldCross = false;
		}
		
		SmartDashboard.putString("Side", RobotMap.side);
		SmartDashboard.putString("Priority", RobotMap.priority);
		SmartDashboard.putBoolean("Cross", RobotMap.shouldCross);
		
		Command selectedAuto = autoSelection.getAuto();
		selectedAuto.start();

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);
		
		

		// autonomousCommand = chooser.getSelected();
		// // schedule the autonomous command (example)
		// if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// Put Encoder values
		SmartDashboard.putNumber("Left Encoder", RobotMap.encoderLeft.get());
		SmartDashboard.putNumber("Back Left Encoder Distance",
				RobotMap.chassisLeftSlave.getSelectedSensorPosition(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Back Left Encoder Rate",
				RobotMap.chassisLeftSlave.getSelectedSensorVelocity(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Front Left Encoder Distance",
				RobotMap.chassisLeftMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Front Left Encoder Rate",
				RobotMap.chassisLeftMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));

		SmartDashboard.putNumber("Right Encoder", RobotMap.encoderRight.get());
		SmartDashboard.putNumber("Back Right Encoder Distance",
				RobotMap.chassisRightSlave.getSelectedSensorPosition(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Back Right Encoder Rate",
				RobotMap.chassisRightSlave.getSelectedSensorVelocity(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Front Right Encoder Distance",
				RobotMap.chassisRightMaster.getSelectedSensorPosition(RobotMap.PID_IDX));
		SmartDashboard.putNumber("Front Right Encoder Rate",
				RobotMap.chassisRightMaster.getSelectedSensorVelocity(RobotMap.PID_IDX));

		SmartDashboard.putNumber("DistanceInInches", Robot.chassis.getRangeFinderDistance());
		SmartDashboard.putNumber("NavX Angle", RobotMap.boundAngle(RobotMap.getNavXAngle()));
		
		SmartDashboard.putNumber("Amps", RobotController.getInputCurrent());

		SmartDashboard.putBoolean("TapeSensor", Robot.chassis.isTapeBright());
		// System.out.println("Tape Sensor: " + RobotMap.tape.get());

		SmartDashboard.putBoolean("Closed_LOOP", RobotMap.CLOSED_LOOP_DRIVING);
		SmartDashboard.putString("ControlMode", RobotMap.chassisLeftMaster.getControlMode().toString());

	}
}
