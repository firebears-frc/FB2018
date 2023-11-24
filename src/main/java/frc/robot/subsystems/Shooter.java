package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.DoubleSolenoidGroup;

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
        leftMotor.configFactoryDefault();
        leftMotor.setInverted(false);
        leftMotor.setNeutralMode(NeutralMode.Coast);
        leftMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        leftMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        leftMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);

        rightMotor = new WPI_TalonSRX(Constants.RIGHT_CAN_ID);
        rightMotor.configFactoryDefault();
        rightMotor.setInverted(true);
        rightMotor.setNeutralMode(NeutralMode.Coast);
        rightMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        rightMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        rightMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);

        motors = new MotorControllerGroup(leftMotor, rightMotor);

        // https://v5.docs.ctr-electronics.com/en/stable/ch18_CommonAPI.html#setting-status-frame-periods
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 20);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 20);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 1000);
        leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 20);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 20);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 1000);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_21_FeedbackIntegrated, 1000);

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
