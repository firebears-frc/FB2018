// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

//
//test
package org.firebears;

import org.firebears.commands.ActivateCompressor;
import org.firebears.commands.BackIntoWallCommand;
import org.firebears.commands.CelebrateCommand;
import org.firebears.commands.DriveToDistanceStraightCommand;
import org.firebears.commands.I2CCommand;
import org.firebears.commands.NonPIDRotateCommand;
//import org.firebears.commands.NonPIDAngle;
import org.firebears.commands.PlayMirroredRecording;
import org.firebears.commands.PlayRecordingCommand;
import org.firebears.commands.ResetNavX;
import org.firebears.commands.RotateToAngleCommandFast;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.StartRecordingCommand;
import org.firebears.commands.StopRecordingCommand;
import org.firebears.commands.TestMotors;
import org.firebears.commands.TurnToAngleDeceleration;
import org.firebears.commands.VisionForwardCommand;
import org.firebears.commands.VisionGetCubeCommandGroup;
import org.firebears.commands.VisionRotateCommandFast;
import org.firebears.commands.auto.ChangePriority;
import org.firebears.commands.auto.ChangeShouldCross;
import org.firebears.commands.auto.ChangeSide;
import org.firebears.commands.auto.DriveToDistanceCommand;
import org.firebears.commands.auto.DriveToTapeCommand;
import org.firebears.commands.auto.TestCommandGroup;
import org.firebears.commands.auto.TestCrossFieldCommandGroup;
import org.firebears.commands.driver.GrabberDownCommand;
import org.firebears.commands.driver.GrabberUpCommand;
import org.firebears.commands.driver.DanceCommand;
import org.firebears.commands.driver.DriverCloseCommand;
import org.firebears.commands.driver.FireCubeCommand;
import org.firebears.commands.grabber.OpenGrabberCommand;
import org.firebears.commands.grabber.RaiseGrabberCommand;
import org.firebears.commands.grabber.ReverseGrabberWheelsCommand;
import org.firebears.commands.grabber.SpinGrabberWheelsCommand;
import org.firebears.commands.shooter.ExtendShooterCommand;
import org.firebears.commands.shooter.SpinShooterWheelsCommand;
import org.firebears.commands.shooter.TestShooterSpeedCommand;
import org.firebears.commands.testing.TestRobotCommand;
import org.firebears.util.RobotReport;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick joystick1;
    public Joystick joystick2;
    public JoystickButton testPID;
    public JoystickButton Driveinches;
    public JoystickButton extendShooter;
    public JoystickButton highShot;
    public JoystickButton medShot;
    public JoystickButton lowShot;
    public JoystickButton switchShot;
    public JoystickButton armDown;
    public JoystickButton armUp;
    public JoystickButton armClose;
    public JoystickButton armOpen;
    public JoystickButton armOpenClose;
    public JoystickButton celebrateButton;
    public JoystickButton celebrateButton2;
    public JoystickButton spinGrabberWheels;
    public JoystickButton reverseGrabberWheels;
    public JoystickButton stopGrabberWheels;
    public JoystickButton dance;
    public JoystickButton testMotors;
    public JoystickButton testMotors2;

    String testRecording = "recordings/MiddleSideRightSwitch.csv";

    public OI(RobotReport report) {
	// Intialize the joysticks
	joystick1 = new Joystick(0);
	report.addJoystick(0, "Joystick 1", joystick1);

	joystick2 = new Joystick(1);

	// Driveinches = new JoystickButton(joystick1,1);
	// Driveinches.whileHeld(new DriveToDistanceCommand(18.8));

	report.addJoystick(1, "Joystick 2", joystick2);

	// Joystick Buttons
	
	testMotors = new JoystickButton(joystick1, 7);
	TestMotors testMotorsStraightForward = new TestMotors(true, true);
	testMotors.whileHeld(testMotorsStraightForward);
	report.addJoystickButton(0, 7, "Test Motors Forward", testMotorsStraightForward);
	
	testMotors = new JoystickButton(joystick1, 9);
	TestMotors testMotorsStraightBackwards = new TestMotors(true, false);
	testMotors.whileHeld(testMotorsStraightBackwards);
	report.addJoystickButton(0, 9, "Test Motors Backward", testMotorsStraightBackwards);
	
	extendShooter = new JoystickButton(joystick2, 1);
	Command fireCubeCommand = new FireCubeCommand();
	extendShooter.whenPressed(fireCubeCommand);
	report.addJoystickButton(1, 1, "Shoot", fireCubeCommand);
	
//	highShot = new JoystickButton(joystick2, 6);
//	highShot.whenPressed(new SpinShooterWheelsCommand(.9));
//	
//	medShot = new JoystickButton(joystick2, 5);
//	medShot.whenPressed(new SpinShooterWheelsCommand(.6));
//	
//	lowShot = new JoystickButton(joystick2, 4);
//	lowShot.whenPressed(new SpinShooterWheelsCommand(.4));
//	
//	switchShot = new JoystickButton(joystick2, 3);
//	switchShot.whenPressed(new SpinShooterWheelsCommand(.25));
	
	armDown = new JoystickButton(joystick2, 7);
	Command grabberDownCommand = new GrabberDownCommand();
	armDown.whenPressed(grabberDownCommand);
	report.addJoystickButton(1, 7, "Arm Down", grabberDownCommand);
	
	armUp = new JoystickButton(joystick2, 9);
	Command grabberUpCommand = new GrabberUpCommand();
	armUp.whenPressed(grabberUpCommand);
	report.addJoystickButton(1, 9, "Arm Up", grabberUpCommand);
	
//	armClose = new JoystickButton(joystick2, 8);
	Command armCloseCommand = new DriverCloseCommand();
//	armClose.whenPressed(armCloseCommand);
//	report.addJoystickButton(1, 8, "Arm Close", armCloseCommand);
	
//	armOpen = new JoystickButton(joystick2, 10);
	Command armOpenCommand = new  OpenGrabberCommand(true);
//	armOpen.whenPressed(armOpenCommand);
//	report.addJoystickButton(1, 10, "Arm Open", armOpenCommand);
	
	armOpenClose = new JoystickButton(joystick2, 14);
	Command armClosedCommand = new OpenGrabberCommand(false);
	armOpenClose.whenPressed(armOpenCommand);
	armOpenClose.whenReleased(armClosedCommand);
	report.addJoystickButton(1, 14, "Toggle arms open/closed", armClosedCommand);
	
	spinGrabberWheels = new JoystickButton(joystick2, 11);
	Command spinnerWheelsStartCommand = new SpinGrabberWheelsCommand(true);
	spinGrabberWheels.whenPressed(spinnerWheelsStartCommand);
	Command spinnerWheelsStopCommand = new SpinGrabberWheelsCommand(false);
	spinGrabberWheels.whenReleased(spinnerWheelsStopCommand);
	report.addJoystickButton(1, 11, "Spinner wheels", spinnerWheelsStartCommand);
	
	reverseGrabberWheels = new JoystickButton(joystick2, 12);
	Command grabberWheelsStartCommand = new ReverseGrabberWheelsCommand(true);
	reverseGrabberWheels.whenPressed(grabberWheelsStartCommand);
	Command grabberWheelsStopCommand = new ReverseGrabberWheelsCommand(false);
	reverseGrabberWheels.whenReleased(grabberWheelsStopCommand);
	report.addJoystickButton(1, 12, "Grabber wheels", spinnerWheelsStartCommand);


	celebrateButton = new JoystickButton(joystick2, 13);
	Command celebrateCommand = new CelebrateCommand();
	celebrateButton.whenPressed(celebrateCommand);
	report.addJoystickButton(1, 13, "Celebrate", celebrateCommand);
	
//	dance = new JoystickButton(joystick1, 1);
//	dance.whileHeld(new DanceCommand());
	// Switch between Open and Closed Loop Driving
	// testPID = new JoystickButton(joystick1, 1);
	// Command switchDriving = new SwitchDrivingType();
	// testPID.whenPressed(new SwitchDrivingType());
	// report.addJoystickButton(0, 1, "Swtich Driving Type", switchDriving);
//	celebrateButton2 =  new JoystickButton(joystick1, 2);
//	celebrateButton2.whileHeld(celebrateCommand);
	
	report.addJoystickButton(1, 2, "Right-side", "Autonomous");
	report.addJoystickButton(1, 3, "Left-side", "Autonomous");
	report.addJoystickButton(1, 8, "Split", "Autonomous");
	report.addJoystickButton(1, 15, "Switch/Scale", "Autonomous");
	report.addJoystickButton(1, 16, "Cross/Dont-cross", "Autonomous");
	
	// SmartDashboard Buttons
	// Recording Commands
	if (RobotMap.DEBUG) {
	    SmartDashboard.putData("Start Recording", new StartRecordingCommand());
	    SmartDashboard.putData("Stop Recording", new StopRecordingCommand());
	    SmartDashboard.putData("Play Recording", new PlayRecordingCommand());
	    SmartDashboard.putData("Play Recording Mirrored", new PlayMirroredRecording());
	    SmartDashboard.putData("Test Recording", new PlayRecordingCommand(testRecording));
	    SmartDashboard.putData("Mirror Test Recording", new PlayMirroredRecording(testRecording));
	    
	    // Vision Commands
	    SmartDashboard.putData("Vision Turn", new VisionRotateCommandFast());
	    SmartDashboard.putData("Drive to Cube", new VisionForwardCommand(.5));
	    SmartDashboard.putData("Vision Get Cube", new VisionGetCubeCommandGroup());

	    // Other Commands
	    SmartDashboard.putData("DriveToTapeCommand", new DriveToTapeCommand(.4));
	    SmartDashboard.putData("RotateToAngle", new RelativeAngleCommand(90));
	    SmartDashboard.putData("Rotate to Specific Angle", new RotateToAngleCommandFast(90));
	    SmartDashboard.putNumber("Target Angle", 90);
	    SmartDashboard.putNumber("Max Speed", .7);
	    SmartDashboard.putNumber("Decel Degree", 80);
	    SmartDashboard.putNumber("Min Speed", .1);
	    SmartDashboard.putNumber("Target Speed", .5);
	    SmartDashboard.putData("Drive into Wall", new BackIntoWallCommand(12));
	    SmartDashboard.putData("DriveStraight", new DriveToDistanceStraightCommand(200, .5));
	    SmartDashboard.putData("Test Command Group", new TestCommandGroup());
	    // SmartDashboard.putData("Nullzone Command",new DriveIntoNullZoneCommand());
		SmartDashboard.putData("Decel Turn to Angle", new TurnToAngleDeceleration(90, .5, 80));
	    
	   SmartDashboard.putData("Extend Shooter", new ExtendShooterCommand(true));
	   SmartDashboard.putData("Retract Shooter", new ExtendShooterCommand(false));
	   SmartDashboard.putData("Spin Shooter Wheels Fast", new SpinShooterWheelsCommand(Robot.shooter.HIGH_SPEED));
	   SmartDashboard.putData("Spin Shooter Wheels Slow", new SpinShooterWheelsCommand(Robot.shooter.LOW_SPEED));
	   SmartDashboard.putData("Stop Shooter Wheels", new SpinShooterWheelsCommand(0));
	   SmartDashboard.putData("Test Shooter Speed", new TestShooterSpeedCommand());
	   SmartDashboard.putNumber("Set Shooter Test Speed", 0);
	   
	   SmartDashboard.putData("Open Grabber", new OpenGrabberCommand(true));
	   SmartDashboard.putData("Close Grabber", new OpenGrabberCommand(false));
	   SmartDashboard.putData("Raise Grabber", new RaiseGrabberCommand(true));
	   SmartDashboard.putData("Lower Grabber", new RaiseGrabberCommand(false));
	   SmartDashboard.putData("Spin Grabber Wheels", spinnerWheelsStartCommand);
	   SmartDashboard.putData("Stop Grabber Wheels", spinnerWheelsStopCommand);
	   
	   SmartDashboard.putData("Turn on Compressor", new ActivateCompressor(true));
	   SmartDashboard.putData("Turn off Compressor", new ActivateCompressor(false));
	   SmartDashboard.putData("Reset NavX", new ResetNavX());
//	   SmartDashboard.putData("NOTPIDROTATECOMMAND", new NonPIDRotateCommand(-90));
	   
	   // Auto commands
	   SmartDashboard.putData("Left side", new ChangeSide("Left"));
	   SmartDashboard.putData("Middle", new ChangeSide("Middle"));
	   SmartDashboard.putData("Right side", new ChangeSide("Right"));
	   SmartDashboard.putData("Scale priority", new ChangePriority("Scale"));
	   SmartDashboard.putData("Switch priority", new ChangePriority("Switch"));
	   SmartDashboard.putData("Should cross", new ChangeShouldCross(true));
	   SmartDashboard.putData("Shouldn't cross", new ChangeShouldCross(false));
	   
	   SmartDashboard.putData("setAnimation", new I2CCommand(1, 3));
	   SmartDashboard.putData("TEST", new TestRobotCommand());
	   SmartDashboard.putData("Celebrate", new CelebrateCommand());

		}
    }

    public Joystick getJoystick1() {
	return joystick1;
    }

}
