/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;

import javax.lang.model.util.ElementScanner6;

import org.firebears.Robot;
import org.firebears.RobotMap;
import org.firebears.subsystems.Chassis;

public class LineFollowerCommand extends Command {

  DigitalInput rightSensor = RobotMap.rightSensor;// 2
  DigitalInput centerSensor = RobotMap.tapeSensor;// 3
  DigitalInput leftSensor = RobotMap.cubeSwitch;// 4

  private boolean Rsen;
  private boolean Csen;
  private boolean Lsen;
  private boolean seenTape;

  private double driveSpeed = 0.2;
  private double rotationSpeed = 0.275
  ;
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

    timeout = System.currentTimeMillis() + 4000;

    System.out.print("initialize");
    seenTape = false;
    // drive slowly
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.print("execute");
    Rsen = rightSensor.get();
    System.out.print(Rsen);
    Csen = centerSensor.get();
    System.out.print(Csen);
    Lsen = leftSensor.get();
    System.out.print(Lsen);

    if (Lsen || Csen || Rsen) {
      seenTape = true;

    }

    if (Lsen && !Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
    } else if (!Lsen && Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, 0.0, false);
    } else if (!Lsen && !Csen && Rsen) {
      Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
    } else if (Lsen && Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, -rotationSpeed, false);
    } else if (!Lsen && Csen && Rsen) {
      Robot.chassis.drive(-driveSpeed, rotationSpeed, false);
    } else if (!Lsen && !Csen && !Rsen) {
      Robot.chassis.drive(-driveSpeed, 0.0, false);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() >= timeout) {
      return true;
    }
    if (Lsen == false && Rsen == false && Csen == false && seenTape) {
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
