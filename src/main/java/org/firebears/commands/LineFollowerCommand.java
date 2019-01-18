/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;
import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.subsystems.Chassis;


public class LineFollowerCommand extends Command {
  DigitalInput rightSensor = RobotMap.cubeSwitch;
  DigitalInput leftSensor = RobotMap.tapeSensor;
  long timeout;

  public LineFollowerCommand() {
    requires(Robot.chassis);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.chassis.drive(0.2, 0, true);
    timeout = System.currentTimeMillis() + 5000;
    System.out.println("initialize");
    // drive slowley
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (leftSensor.get() == false) {
      Robot.chassis.drive(-0.5, 0.5, true);
    }else
    if (rightSensor.get() == false) {
      Robot.chassis.drive(-0.5, -0.5, true);
    }else
    if (leftSensor.get() && rightSensor.get()) {
      Robot.chassis.drive(-0.5, 0, true);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() >= timeout){
      return true;
    }
    if (leftSensor.get() == false && rightSensor.get() == false){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0, 0, true);
    System.out.println("end");
  }


}
