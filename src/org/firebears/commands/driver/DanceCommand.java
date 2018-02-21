package org.firebears.commands.driver;

import org.firebears.Robot;
import org.firebears.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Dance
 */
public class DanceCommand extends Command {
	int danceState;
	final int RIGHT_STATE = 0;
	final int LEFT_STATE = 1;
	final double STATE_DURATION = 2000;
	double lastTime;

	public DanceCommand() {
		requires(Robot.grabber);
	}
	protected void initialize() {
		lastTime = System.currentTimeMillis();
		danceState = RIGHT_STATE;
	}
	protected void execute() {
		if (System.currentTimeMillis() < lastTime + STATE_DURATION) {
			switch (danceState) {
			case RIGHT_STATE:
				danceState = LEFT_STATE;
				RobotMap.leftUpDown.set(Robot.grabber.SOL_REVERSE);
				RobotMap.rightUpDown.set(Robot.grabber.SOL_FORWARD);
				break;
			case LEFT_STATE:
				danceState = RIGHT_STATE;
				RobotMap.leftUpDown.set(Robot.grabber.SOL_FORWARD);
				RobotMap.rightUpDown.set(Robot.grabber.SOL_REVERSE);
				break;
			default:
				System.out.println("danceState is not valid: " + danceState);
				break;
			}
			lastTime = System.currentTimeMillis();
		}
	}
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
	}
	protected void interrupted() {
	}
}
