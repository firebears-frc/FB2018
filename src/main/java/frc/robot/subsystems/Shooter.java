package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private static final class Constants {
        private static final int LEFT_CAN_ID = 11;
        private static final int RIGHT_CAN_ID = 12;

        private static final int LEFT_FORWARD_CHANNEL = 1;
        private static final int LEFT_REVERSE_CHANNEL = 0;
        private static final int RIGHT_FORWARD_CHANNEL = 3;
        private static final int RIGHT_REVERSE_CHANNEL = 2;

        public static final double SHOOT_SPEED = 1.0;

        public static final int PEAK_CURRENT_LIMIT = 60;
        public static final int PEAK_CURRENT_DURATION = 1000;
        public static final int CONTINUOUS_CURRENT_LIMIT = 40;
    }

    private final WPI_TalonSRX leftMotor = new WPI_TalonSRX(Constants.LEFT_CAN_ID);
    private final WPI_TalonSRX rightMotor = new WPI_TalonSRX(Constants.RIGHT_CAN_ID);
    private final DoubleSolenoid leftSolenoid;
    private final DoubleSolenoid rightSolenoid;

    public Shooter(PneumaticsControlModule pcm) {
        leftSolenoid = pcm.makeDoubleSolenoid(Constants.LEFT_FORWARD_CHANNEL, Constants.LEFT_REVERSE_CHANNEL);
        rightSolenoid = pcm.makeDoubleSolenoid(Constants.RIGHT_FORWARD_CHANNEL, Constants.RIGHT_REVERSE_CHANNEL);

        leftMotor.configFactoryDefault();
        leftMotor.setInverted(false);
        leftMotor.setNeutralMode(NeutralMode.Coast);
        leftMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        leftMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        leftMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);
        
        rightMotor.configFactoryDefault();
        rightMotor.setInverted(true);
        rightMotor.setNeutralMode(NeutralMode.Coast);
        rightMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        rightMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        rightMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);
    }

    public Command punch() {
        return runOnce(() -> {
            leftSolenoid.set(DoubleSolenoid.Value.kReverse);
            rightSolenoid.set(DoubleSolenoid.Value.kReverse);
        });
    }

    public Command retract() {
        return runOnce(() -> {
            leftSolenoid.set(DoubleSolenoid.Value.kForward);
            rightSolenoid.set(DoubleSolenoid.Value.kForward);
        });
    }

    public Command spin() {
        return runOnce(() -> {
            leftMotor.set(Constants.SHOOT_SPEED);
            rightMotor.set(Constants.SHOOT_SPEED);
        });
    }

    public Command stop() {
        return runOnce(() -> {
            leftMotor.set(0.0);
            rightMotor.set(0.0);
        });
    }
}
