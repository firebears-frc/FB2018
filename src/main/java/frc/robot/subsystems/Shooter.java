package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.DoubleSolenoidGroup;
import frc.robot.util.talonsrx.CurrentLimitConfiguration;
import frc.robot.util.talonsrx.StatusFrameConfiguration;
import frc.robot.util.talonsrx.TalonSRXConfiguration;

public class Shooter extends SubsystemBase {
    private static final class Constants {
        private static final int LEFT_CAN_ID = 11;
        private static final int RIGHT_CAN_ID = 12;

        private static final int LEFT_FORWARD_CHANNEL = 1;
        private static final int LEFT_REVERSE_CHANNEL = 0;
        private static final int RIGHT_FORWARD_CHANNEL = 3;
        private static final int RIGHT_REVERSE_CHANNEL = 2;

        public static final double SHOOT_SPEED = 1.0;

        public static final TalonSRXConfiguration LEFT_CONFIG = new TalonSRXConfiguration(
                false,
                NeutralMode.Coast,
                CurrentLimitConfiguration.complex(40, 60, 1000),
                StatusFrameConfiguration.normal());
        public static final TalonSRXConfiguration RIGHT_CONFIG = new TalonSRXConfiguration(
                true,
                NeutralMode.Coast,
                CurrentLimitConfiguration.complex(40, 60, 1000),
                StatusFrameConfiguration.normal());
    }

    private final WPI_TalonSRX leftMotor, rightMotor;
    private final MotorControllerGroup motors;
    private final DoubleSolenoid leftSolenoid, rightSolenoid;
    private final DoubleSolenoidGroup solenoids;

    @AutoLogOutput(key = "Shooter/Speed")
    private double speed;

    public Shooter(PneumaticsControlModule pcm) {
        leftSolenoid = pcm.makeDoubleSolenoid(Constants.LEFT_FORWARD_CHANNEL, Constants.LEFT_REVERSE_CHANNEL);
        rightSolenoid = pcm.makeDoubleSolenoid(Constants.RIGHT_FORWARD_CHANNEL, Constants.RIGHT_REVERSE_CHANNEL);
        solenoids = new DoubleSolenoidGroup(leftSolenoid, rightSolenoid);

        leftMotor = new WPI_TalonSRX(Constants.LEFT_CAN_ID);
        rightMotor = new WPI_TalonSRX(Constants.RIGHT_CAN_ID);

        Constants.LEFT_CONFIG.apply(leftMotor);
        Constants.RIGHT_CONFIG.apply(rightMotor);

        motors = new MotorControllerGroup(leftMotor, rightMotor);

        speed = 0.0;
    }

    public Command punch() {
        return runOnce(() -> solenoids.set(DoubleSolenoid.Value.kForward));
    }

    public Command retract() {
        return runOnce(() -> solenoids.set(DoubleSolenoid.Value.kReverse));
    }

    public Command spin() {
        return runOnce(() -> speed = Constants.SHOOT_SPEED);
    }

    public Command stop() {
        return runOnce(() -> speed = 0.0);
    }

    @Override
    public void periodic() {
        motors.set(speed);
    }
}
