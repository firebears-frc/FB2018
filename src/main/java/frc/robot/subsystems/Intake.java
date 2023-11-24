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

public class Intake extends SubsystemBase {
    private static final class Constants {
        private static final int LEFT_CAN_ID = 13;
        private static final int RIGHT_CAN_ID = 14;

        private static final int LEFT_RAISE_FORWARD_CHANNEL = 1;
        private static final int LEFT_RAISE_REVERSE_CHANNEL = 0;
        private static final int RIGHT_RAISE_FORWARD_CHANNEL = 3;
        private static final int RIGHT_RAISE_REVERSE_CHANNEL = 2;
        private static final int LEFT_CLOSE_FORWARD_CHANNEL = 5;
        private static final int LEFT_CLOSE_REVERSE_CHANNEL = 4;
        private static final int RIGHT_CLOSE_FORWARD_CHANNEL = 7;
        private static final int RIGHT_CLOSE_REVERSE_CHANNEL = 6;

        public static final double INTAKE_SPEED = 0.5;
        public static final double HOLD_SPEED = 0.05;

        public static final int PEAK_CURRENT_LIMIT = 20;
        public static final int PEAK_CURRENT_DURATION = 1000;
        public static final int CONTINUOUS_CURRENT_LIMIT = 10;
    }

    private enum IntakeState {
        INTAKE,
        EJECT,
        HOLD,
        STOP
    }

    private final WPI_TalonSRX leftMotor, rightMotor;
    private final MotorControllerGroup motors;
    private final DoubleSolenoid leftRaise, rightRaise, leftClose, rightClose;
    private final DoubleSolenoidGroup raise, close;

    @AutoLogOutput(key = "Intake/State")
    private IntakeState state;
    @AutoLogOutput(key = "Intake/Speed")
    private double speed;

    public Intake(PneumaticsControlModule pcm_0, PneumaticsControlModule pcm_1) {
        leftMotor = new WPI_TalonSRX(Constants.LEFT_CAN_ID);
        leftMotor.configFactoryDefault();
        leftMotor.setInverted(true);
        leftMotor.setNeutralMode(NeutralMode.Coast);
        leftMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        leftMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        leftMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);

        rightMotor = new WPI_TalonSRX(Constants.RIGHT_CAN_ID);
        rightMotor.configFactoryDefault();
        rightMotor.setInverted(false);
        rightMotor.setNeutralMode(NeutralMode.Coast);
        rightMotor.configPeakCurrentLimit(Constants.PEAK_CURRENT_LIMIT);
        rightMotor.configPeakCurrentDuration(Constants.PEAK_CURRENT_DURATION);
        rightMotor.configContinuousCurrentLimit(Constants.CONTINUOUS_CURRENT_LIMIT);

        motors = new MotorControllerGroup(leftMotor, rightMotor);

        leftRaise = pcm_1.makeDoubleSolenoid(Constants.LEFT_RAISE_FORWARD_CHANNEL,
                Constants.LEFT_RAISE_REVERSE_CHANNEL);
        rightRaise = pcm_1.makeDoubleSolenoid(Constants.RIGHT_RAISE_FORWARD_CHANNEL,
                Constants.RIGHT_RAISE_REVERSE_CHANNEL);
        raise = new DoubleSolenoidGroup(leftRaise, rightRaise);

        leftClose = pcm_0.makeDoubleSolenoid(Constants.LEFT_CLOSE_FORWARD_CHANNEL,
                Constants.LEFT_CLOSE_REVERSE_CHANNEL);
        rightClose = pcm_0.makeDoubleSolenoid(Constants.RIGHT_CLOSE_FORWARD_CHANNEL,
                Constants.RIGHT_CLOSE_REVERSE_CHANNEL);
        close = new DoubleSolenoidGroup(leftClose, rightClose);

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

        state = IntakeState.STOP;
        speed = 0.0;
    }

    public Command up() {
        return runOnce(() -> raise.set(DoubleSolenoid.Value.kForward));
    }

    public Command down() {
        return runOnce(() -> raise.set(DoubleSolenoid.Value.kReverse));
    }

    public Command open() {
        return runOnce(() -> close.set(DoubleSolenoid.Value.kForward));
    }

    public Command close() {
        return runOnce(() -> close.set(DoubleSolenoid.Value.kReverse));
    }

    public Command intake() {
        return runOnce(() -> state = IntakeState.INTAKE);
    }

    public Command hold() {
        return runOnce(() -> state = IntakeState.HOLD);
    }

    public Command eject() {
        return runOnce(() -> state = IntakeState.EJECT);
    }

    public Command stop() {
        return runOnce(() -> state = IntakeState.STOP);
    }

    @Override
    public void periodic() {
        // Figure out what speed we should be running
        speed = switch (state) {
            case INTAKE -> Constants.INTAKE_SPEED;
            case EJECT -> -1.0 * Constants.INTAKE_SPEED;
            case HOLD -> Constants.HOLD_SPEED;
            case STOP -> 0.0;
        };

        motors.set(speed);
    }
}
