package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    @AutoLogOutput
    private IntakeState state = IntakeState.STOP;

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
        leftClose = pcm_0.makeDoubleSolenoid(Constants.LEFT_CLOSE_FORWARD_CHANNEL,
                Constants.LEFT_CLOSE_REVERSE_CHANNEL);
        rightClose = pcm_0.makeDoubleSolenoid(Constants.RIGHT_CLOSE_FORWARD_CHANNEL,
                Constants.RIGHT_CLOSE_REVERSE_CHANNEL);
    }

    public Command up() {
        return runOnce(() -> {
            leftRaise.set(DoubleSolenoid.Value.kForward);
            rightRaise.set(DoubleSolenoid.Value.kForward);
        });
    }

    public Command down() {
        return runOnce(() -> {
            leftRaise.set(DoubleSolenoid.Value.kReverse);
            rightRaise.set(DoubleSolenoid.Value.kReverse);
        });
    }

    public Command open() {
        return runOnce(() -> {
            leftClose.set(DoubleSolenoid.Value.kForward);
            rightClose.set(DoubleSolenoid.Value.kForward);
        });
    }

    public Command close() {
        return runOnce(() -> {
            leftClose.set(DoubleSolenoid.Value.kReverse);
            rightClose.set(DoubleSolenoid.Value.kReverse);
        });
    }

    public Command intake() {
        return runOnce(() -> {
            state = IntakeState.INTAKE;
        });
    }

    public Command hold() {
        return runOnce(() -> {
            state = IntakeState.HOLD;
        });
    }

    public Command eject() {
        return runOnce(() -> {
            state = IntakeState.EJECT;
        });
    }

    public Command stop() {
        return runOnce(() -> {
            state = IntakeState.STOP;
        });
    }

    @Override
    public void periodic() {
        // Figure out what speed we should be running
        double speed = switch (state) {
            case INTAKE -> Constants.INTAKE_SPEED;
            case EJECT -> -1.0 * Constants.INTAKE_SPEED;
            case HOLD -> Constants.HOLD_SPEED;
            case STOP -> 0.0;
        };

        motors.set(speed);
    }
}
