// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Chassis extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonSRX leftTalon1;
private WPI_TalonSRX leftTalon2;
private MotorControllerGroup leftMotors;
private WPI_TalonSRX rightTalon1;
private WPI_TalonSRX rightTalon2;
private MotorControllerGroup rightMotors;
private DifferentialDrive differentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    /**
    *
    */
    public Chassis() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
leftTalon1 = new WPI_TalonSRX(2);
 
 

leftTalon2 = new WPI_TalonSRX(3);
 
 

leftMotors = new MotorControllerGroup(leftTalon1, leftTalon2  );
 addChild("LeftMotors",leftMotors);
 

rightTalon1 = new WPI_TalonSRX(4);
 
 

rightTalon2 = new WPI_TalonSRX(5);
 
 

rightMotors = new MotorControllerGroup(rightTalon1, rightTalon2  );
 addChild("RightMotors",rightMotors);
 

differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
 addChild("Differential Drive",differentialDrive);
 differentialDrive.setSafetyEnabled(true);
differentialDrive.setExpiration(0.1);
differentialDrive.setMaxOutput(1.0);



    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void arcadeDrive(double speed, double rotaiton) {

        differentialDrive.arcadeDrive(rotaiton, speed * -1);
    }

}

