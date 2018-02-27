//package org.firebears.commands;
//
//import org.firebears.Robot;
//import org.firebears.RobotMap;
//
//import edu.wpi.first.wpilibj.command.CommandGroup;
//import edu.wpi.first.wpilibj.command.PIDCommand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
///**
// * Rotate the robot to the previously saved gyro direction.
// * @see GetRotationCommand
// */
//public class AdjustRotationCommand extends PIDCommand {
//
//    double offset = 0.;
//    public static final double MAX_SPEED = 0.5;
//
//    public AdjustRotationCommand() {
//	super(.050, 0.00005, 0.00); // PID
//	requires(Robot.chassis);
//	getPIDController().setToleranceBuffer(6);
//    }
//
//    public AdjustRotationCommand(double degree_offset) {
//	super(.050, 0.00001, 0.00); // PID
//	requires(Robot.chassis);
//	offset = degree_offset;
//	getPIDController().setToleranceBuffer(6);
//    }
//
//    @Override
//    protected double returnPIDInput() {
//	return RobotMap.navXBoard.getAngle();
//    }
//
//    @Override
//    protected void usePIDOutput(double output) {
//	if (output > MAX_SPEED) {
//	    output = MAX_SPEED;
//	} else if (output < -1 * MAX_SPEED) {
//	    output = -1 * MAX_SPEED;
//	}
//	Robot.chassis.drive(0, output, true);
//    }
//
//    private double bound(double which) {
//	if (which >= 360.) {
//	    return which - 360;
//	} else if (which < 0) {
//	    return which + 360;
//	} else {
//	    return which;
//	}
//    }
//
//    @Override
//    protected void initialize() {
//	getPIDController().setInputRange(0, 359);
//	getPIDController().setContinuous(true);
//	getPIDController().setAbsoluteTolerance(2);
//	getPIDController().enable();
//	setSetpoint(bound(RobotMap.rotation + offset));
//	setTimeout(3);
//	if (RobotMap.DEBUG)
//	    System.out.println("\t # " + this);
//    }
//
//    @Override
//    protected void execute() {
//
//    }
//
//    @Override
//    protected boolean isFinished() {
//	if (isTimedOut()) {
//	    return true;
//	}
//	return Math.abs(RobotMap.navXBoard.getAngle() - RobotMap.rotation) < 4;
//    }
//
//    @Override
//    protected void end() {
//	Robot.chassis.drive(0, 0, true);
//    }
//
//    @Override
//    protected void interrupted() {
//	Robot.chassis.drive(0, 0, true);
//
//    }
//
//    @Override
//    public String toString() {
//	return "AdjustRotationCommand()";
//    }
//}
