package org.firebears.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutomaticAutoCommand extends Command {

	final String LEFT = "Left";
	final String RIGHT = "Right";
	final String MIDDLE = "Middle";
	final String SWITCH = "Switch";
	final String SCALE = "Scale"; 
	public AutomaticAutoCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);


		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
		String side = LEFT;
		String priority = SCALE;
		Boolean shouldCross = false;
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		// to-do = replace println's with auto commands
				switch (side) {

				// Robot on left side
				case LEFT:
					switch (priority) {
					
					// Scale as priority
					case SCALE:
						switch (gameData.charAt(1)) {
						
						// Scale on left
						case 'L':
							// Runs when robot on left, scale on left
							System.out.println("Select auto: left side, left scale");
							break;
							
						// Scale on right
						case 'R':
							if (shouldCross) {
								// Runs when robot on left, scale on right and allowed to cross
								System.out.println("Select auto: left side, right scale");
							} else {
								if (gameData.charAt(0) == 'L') {
									// Runs when robot on left, scale on right and cannot cross
									System.out.println("Select auto: left side, left switch");
								} else {
									// Runs when robot on left, switch & scale on right and cannot cross
									System.out.println("Select auto: cross auto line");
								}
							}
							break;
						}
						break;

					// Switch as priority
					case SWITCH:
						switch (gameData.charAt(0)) {
						
						// Switch on left
						case 'L':
							// Runs when robot on left, switch on left
							System.out.println("Select auto: left side, left switch");
							break;
							
						// Switch on right
						case 'R':
							if (shouldCross) {
								// Runs when robot on left, switch on right and allowed to cross
								System.out.println("Select auto: left side, right switch");
							} else {
								// Runs when robot on left, switch on right and cannot cross
								System.out.println("Select auto: cross auto line");
							}
							break;
						}
						break;
					}
					break;

				// Robot is middle
				case MIDDLE:
					switch (gameData.charAt(0)) {
					
					// Switch on left
					case 'L':
						// Runs when robot is middle, switch on left
						System.out.println("Select auto: middle, left switch");
						break;
						
					// Switch on right
					case 'R':
						// Runs when robot is middle, switch on right
						System.out.println("Select auto: middle, right switch");
						break;
					}
					break;
					
				// Robot on right side
				case RIGHT:
					switch (priority) {
					
					// Scale as priority
					case SCALE:
						switch (gameData.charAt(1)) {
						
						// Scale on right
						case 'R':
							// Runs when robot on right, scale on right
							System.out.println("Select auto: right side, right scale");
							break;
						
						// Scale on left
						case 'L':
							if (shouldCross) {
								// Runs when robot on right, scale on left and allowed to cross
								System.out.println("Select auto: right side, left scale");
							} else {
								if (gameData.charAt(0) == 'R') {
									// Runs when robot on right, scale on left and cannot cross
									System.out.println("Select auto: right side, right switch");
								} else {
									// Runs when robot on right, switch & scale on left and cannot cross
									System.out.println("Select auto: cross auto line");
								}
							}
							break;
						}
					}
					break;
					
					// Switch as priority
					case SWITCH:
						switch (gameData.charAt(0)) {
						
						// Switch on right
						case 'R': 
							// Runs when robot on right, switch on right
							System.out.println("Select auto: right side, right switch");
							break;
						
						// Switch on left
						case 'L':
							if (shouldCross) {
								// Runs when robot on right, switch on left and allowed to cross
								System.out.println("Select auto: right side, left switch");
							} else {
								// Runs when robot on right, switch on left and not allowed to cross
								System.out.println("Select auto: cross auto line");
							}
							break; 
						}
						
				}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
