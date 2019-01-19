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
<<<<<<< HEAD
  DigitalInput rightSensor = RobotMap.rightSensor;
  DigitalInput centerSensor = RobotMap.tapeSensor;
  DigitalInput leftSensor = RobotMap.cubeSwitch;
=======
  DigitalInput rightSensor = RobotMap.cubeSwitch;
  DigitalInput middleSensor = RobotMap.middleSensor;
  DigitalInput leftSensor = RobotMap.tapeSensor;
>>>>>>> c3aece08c3e8f0d77e950b19b4484b7e4104d3f5
  long timeout;
  int state;
  int state2;

  public LineFollowerCommand() {
    requires(Robot.chassis);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.chassis.drive(0.2, 0, true);
<<<<<<< HEAD
    timeout = System.currentTimeMillis() + 10000;
=======
    timeout = System.currentTimeMillis() + 2000;
>>>>>>> c3aece08c3e8f0d77e950b19b4484b7e4104d3f5
    System.out.println("initialize");
    state = 0;
    state2 = 0;
    // drive slowley
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
<<<<<<< HEAD
    if (leftSensor.get() == true) {
      Robot.chassis.drive(-0.5, 0.5, true);
    }else
    if (centerSensor.get() == true) {
      Robot.chassis.drive(-0.5, 0, true);
    }else
    if (rightSensor.get() == true) {
      Robot.chassis.drive(-0.5, -0.5, true);
    }else
    if (leftSensor.get() && rightSensor.get() && centerSensor.get() == false) {
      Robot.chassis.drive(-0.5, 0, true);
=======
    System.out.println(state);
    if (state == 0) {
      if (leftSensor.get() == false && middleSensor.get() == false && rightSensor.get() == false) {
        Robot.chassis.drive(-0.5, 0, true);
      } else {
        state = 2;
      }
    } else if (state == 1) {
      timeout = System.currentTimeMillis() + 5000;
      state = 2;
    } else if (state == 2) {
      if (leftSensor.get() == true && middleSensor.get() == true && rightSensor.get() == true) {
        Robot.chassis.drive(-0.5, 0, true);
      } else if (leftSensor.get() == true && middleSensor.get() == true) {
        Robot.chassis.drive(-0.5, -0.5, true);
      } else if (rightSensor.get() == true && middleSensor.get() == true) {
        Robot.chassis.drive(-0.5, 0.5, true);
      } else if (leftSensor.get() == true) {
        Robot.chassis.drive(-0.5, -1, true);
      } else if (rightSensor.get() == true) {
        Robot.chassis.drive(-0.5, 1, true);
      } else if (leftSensor.get() == false && rightSensor.get() == false && middleSensor.get() == true) {
        Robot.chassis.drive(-0.5, 0, true);
      }
    }
    if (state2 == 1) {
      if ((leftSensor.get() || rightSensor.get()) || middleSensor.get()) {
        state2 = 0;
        state = 1;
      }
>>>>>>> c3aece08c3e8f0d77e950b19b4484b7e4104d3f5
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() >= timeout) {
      return true;
    }
<<<<<<< HEAD
    if (leftSensor.get() == false && rightSensor.get() == false && centerSensor.get() == false){
      return true;
=======
    if (state == 2) {
      if (leftSensor.get() == false && rightSensor.get() == false && middleSensor.get() == false) {
          timeout = System.currentTimeMillis() + 3000;
        state2 = 1;
      }
>>>>>>> c3aece08c3e8f0d77e950b19b4484b7e4104d3f5
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
