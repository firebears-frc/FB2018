package org.firebears;

import org.firebears.subsystems.Lights;
import org.firebears.util.RobotReport;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static final boolean DEBUG = true;

    public static CANTalon chassisLeftMaster;
    public static CANTalon chassisLeftSlave;
    public static SpeedControllerGroup chassisLeftMotors;
    public static CANTalon chassisRightMaster;
    public static CANTalon chassisRightSlave;
    public static SpeedControllerGroup chassisRightMotors;
    public static DifferentialDrive chassisRobotDrive;
    
    public static PowerDistributionPanel pdp;

    public static Compressor compressor;
    
    public static CANTalon leftIntake;
    public static CANTalon rightIntake;

    public static DoubleSolenoid leftOpenClose;
    public static DoubleSolenoid rightOpenClose;
    public static DoubleSolenoid leftUpDown;
    public static DoubleSolenoid rightUpDown;
    
    public static DoubleSolenoid leftLaunch;
    public static DoubleSolenoid rightLaunch;
    
    public static CANTalon leftLaunchSpinner;
    public static CANTalon rightLaunchSpinner;

    public static AnalogInput pressureSensor;

    public static final int CAN_LEFT_MASTER = 2;
    public static final int CAN_LEFT_SLAVE = 3;
    public static final int CAN_RIGHT_MASTER = 4;
    public static final int CAN_RIGHT_SLAVE = 5;
    public static final int CAN_LEFT_CUBE_SPINNER = 11;
    public static final int CAN_RIGHT_CUBE_SPINNER = 12;
    public static final int CAN_LEFT_GRABBER_MOTOR = 13;
    public static final int CAN_RIGHT_GRABBER_MOTOR = 14;
    public static final int CAN_CLIMBER_MOTOR = 15;
    public static final int CAN_SPARE_MOTOR = 16;

    // Variables for closed loop driving
    public static final int PID_IDX = 0;
    public static final int TIMEOUT_MS = 10;
    static double m_P = 3.0;
    static double m_I = 0;
    static double m_D = 0;
    static double m_ff = 1.0; //1.46;
    static int m_izone = 256;
    static double m_rampRate = 0.2;
    static int m_profile = 0;
    public static int m_CountPerRev = 700;// ****Magnetic
    public static boolean CLOSED_LOOP_DRIVING = true;

    // For autoSelecion
    public static String side;
    public static String priority;
    public static boolean shouldCross;

    // Different Sensors
    public static AHRS navXBoard;

    public static AnalogInput rangeFinder;
    public static AnalogInput rangeFinder2;

    public static Encoder encoderLeft;
    public static Encoder encoderRight;

    public static DigitalInput tapeSensor;

    public static void init(RobotReport report) {

	// Set up motors for driving
	chassisLeftMaster = new CANTalon(CAN_LEFT_MASTER);
	chassisLeftMaster.setName("Chassis", "FrontLeft");
	chassisLeftMaster.setNeutralMode(NeutralMode.Brake);
	report.addCAN(CAN_LEFT_MASTER, "Left Master", chassisLeftMaster);

	chassisLeftSlave = new CANTalon(CAN_LEFT_SLAVE);
	chassisLeftSlave.setName("Chassis", "BackLeft");
	chassisLeftSlave.setNeutralMode(NeutralMode.Brake);
	report.addCAN(CAN_LEFT_SLAVE, "Left Slave", chassisLeftSlave);

	chassisLeftMotors = new SpeedControllerGroup(chassisLeftMaster, chassisLeftSlave);
	// LiveWindow.addActuator("Chassis", "LeftMotors", chassisLeftMotors);

	chassisRightMaster = new CANTalon(CAN_RIGHT_MASTER);
	chassisRightMaster.setName("Chassis", "FrontRight");
	chassisRightMaster.setNeutralMode(NeutralMode.Brake);
	report.addCAN(CAN_RIGHT_MASTER, "Right Master", chassisRightMaster);

	chassisRightSlave = new CANTalon(CAN_RIGHT_SLAVE);
	chassisRightSlave.setName("Chassis", "BackRight");
	chassisRightSlave.setNeutralMode(NeutralMode.Brake);
	report.addCAN(CAN_RIGHT_SLAVE, "Right Slave", chassisRightSlave);

	chassisRightMotors = new SpeedControllerGroup(chassisRightMaster, chassisRightSlave);

	chassisLeftSlave.follow(chassisLeftMaster);
	chassisRightSlave.follow(chassisRightMaster);
	chassisRobotDrive = new DifferentialDrive(chassisLeftMaster, chassisRightMaster);

	chassisRobotDrive.setSafetyEnabled(true);
	chassisRobotDrive.setExpiration(0.1);
	chassisRobotDrive.setMaxOutput(1.0);

	// Set up parameters for closed loop driving
	// if (CLOSED_LOOP_DRIVING) {
	chassisLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
	chassisLeftMaster.configEncoderCodesPerRev(m_CountPerRev);
	chassisLeftMaster.setSensorPhase(true);
	setPID(chassisLeftMaster, m_P, m_I, m_D, m_ff, m_izone, m_rampRate, m_profile);

	chassisRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
	chassisRightMaster.configEncoderCodesPerRev(m_CountPerRev);
	chassisRightMaster.setSensorPhase(true);
	setPID(chassisRightMaster, m_P, m_I, m_D, m_ff, m_izone, m_rampRate, m_profile);
	
	// }

	// DigitalInput encoderLeftInputA = new DigitalInput(2);
	// DigitalInput encoderLeftInputB = new DigitalInput(3);
	// encoderLeft = new Encoder(encoderLeftInputA, encoderLeftInputB, false,
	// EncodingType.k4X);
//	encoderLeft = new Encoder(2, 3, false, EncodingType.k4X);
//	encoderLeft.setDistancePerPulse(0.05639);
//	encoderLeft.setPIDSourceType(PIDSourceType.kRate);
	//
	// DigitalInput encoderRightInputA = new DigitalInput(4);
	// DigitalInput encoderRightInputB = new DigitalInput(5);
	// encoderRight = new Encoder(encoderRightInputA, encoderRightInputB, true,
	// EncodingType.k4X);
//	encoderRight = new Encoder(4, 5, true, EncodingType.k4X);
//	encoderRight.setDistancePerPulse(0.05639);
//	encoderRight.setPIDSourceType(PIDSourceType.kRate);

	leftIntake = new CANTalon(CAN_LEFT_GRABBER_MOTOR);
	leftIntake.setName("Grabber", "leftIntake");
	report.addCAN(CAN_LEFT_GRABBER_MOTOR, "leftIntake", leftIntake);
	
	rightIntake = new CANTalon(CAN_RIGHT_GRABBER_MOTOR);
	rightIntake.setName("Grabber", "rightIntake");
	report.addCAN(CAN_RIGHT_GRABBER_MOTOR, "rightIntake", rightIntake);
	
	leftLaunchSpinner = new CANTalon(CAN_LEFT_GRABBER_MOTOR);
	leftLaunchSpinner.setName("shooter", "leftSpinner");
	
	rightLaunchSpinner = new CANTalon(CAN_RIGHT_GRABBER_MOTOR);
	rightLaunchSpinner.setName("shooter", "rightSpinner");
	
	leftLaunch = new DoubleSolenoid(0,1,0);
    leftLaunch.setName("shooter", "leftPneumatics");
    
	rightLaunch = new DoubleSolenoid(0,3,2);
    rightLaunch.setName("shooter", "rightPneumatics");
	
	leftOpenClose = new DoubleSolenoid(0, 5, 4);
	leftOpenClose.setName("Grabber", "leftOpenClose");
	report.addPcm(0, 5, 4, "leftOpenClose", leftOpenClose);
	
	rightOpenClose = new DoubleSolenoid(0, 7, 6);
	rightOpenClose.setName("Grabber", "leftOpenClose");
	report.addPcm(0, 7, 6, "rightOpenClose", rightOpenClose);
	
	leftUpDown = new DoubleSolenoid(1, 1, 0);
	leftUpDown.setName("Grabber", "leftUpDown");
	report.addPcm(1, 1, 0, "leftUpDown", leftUpDown);
	
	rightUpDown = new DoubleSolenoid(1, 3, 2);
	rightUpDown.setName("Grabber", "rightUpDown");
	report.addPcm(1, 3, 2, "rightUpDown", rightUpDown);
	
	compressor = new Compressor();
	compressor.setClosedLoopControl(true);
	
	rangeFinder = new AnalogInput(0);
	rangeFinder.setName("Chassis", "Rangefinder");
	report.addAnalogInput(0, "Range Finder", rangeFinder);

	rangeFinder2 = new AnalogInput(1);
	rangeFinder2.setName("Chassis", "Rangefinder2");
	report.addAnalogInput(1, "Range Finder 2", rangeFinder2);

	pressureSensor = new AnalogInput(2);
	pressureSensor.setName("Chassis", "PressureSensor");
	report.addAnalogInput(2, "Pressure Sensor", pressureSensor);

	// Put Ultrasonic Switches here

	tapeSensor = new DigitalInput(0);
	report.addDigitalIO(2, "Tape Finder", tapeSensor);

	// Put Sensor for when cube is loaded here

	// Put Sensor for when cube is in the grabber here

	try {
	    // navXBoard = new AHRS(SPI.Port.kMXP);
	    navXBoard = new AHRS(Port.kUSB);
	} catch (RuntimeException ex) {
	    DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	}

	pdp = new PowerDistributionPanel();
	pdp.clearStickyFaults();


	// testSolenoid = new DoubleSolenoid(1, 2);

	report.addOtherConfig(Lights.I2C_ADDRESS, "Trinket I2C Address");
    }

    /**
     * @return the angle wrapped into the range -180 to 180...
     */
    public static double boundAngle(double angle) {
	while (angle > 180) {
	    angle -= 360;
	}
	while (angle < -180) {
	    angle += 360;
	}
	return angle;
    }

    /**
     * @return current NavXBoard angle wrapped to the range -180 to 180.
     */
    public static double getNavXAngle() {
	return boundAngle(navXBoard.getAngle());
    }

    private static void setPID(TalonSRX talonSRX, double pidP, double pidI, double pidD, double pidF, int pidIZone,
	    double pidRampRate, int slotIdx) {
	talonSRX.configClosedloopRamp(pidRampRate, TIMEOUT_MS);
	talonSRX.configNominalOutputForward(0.0, TIMEOUT_MS);
	talonSRX.configNominalOutputReverse(0.0, TIMEOUT_MS);
	talonSRX.configPeakOutputForward(1.0, TIMEOUT_MS);
	talonSRX.configPeakOutputReverse(-1.0, TIMEOUT_MS);
	talonSRX.config_kP(slotIdx, pidP, TIMEOUT_MS);
	talonSRX.config_kI(slotIdx, pidI, TIMEOUT_MS);
	talonSRX.config_kD(slotIdx, pidD, TIMEOUT_MS);
	talonSRX.config_kF(slotIdx, pidF, TIMEOUT_MS);
	talonSRX.config_IntegralZone(slotIdx, pidIZone, TIMEOUT_MS);
	talonSRX.selectProfileSlot(slotIdx, PID_IDX);
    }

    public static class CANTalon extends WPI_TalonSRX {

	public int encoderMultiplier = 0;

	public CANTalon(int deviceNumber) {
	    super(deviceNumber);
	}

	@Override
	public void set(double speed) {
	    // if (encoderMultiplier != 0) {
	    if (CLOSED_LOOP_DRIVING) {
		set(ControlMode.Velocity, speed * encoderMultiplier);
	    } else {
		set(ControlMode.PercentOutput, speed);
	    }
	}

	@Override
	public String toString() {
	    return "CANTalon(" + getDeviceID() + ")";
	}

	public void configEncoderCodesPerRev(int ticks) {
	    this.encoderMultiplier = ticks;
	}
    }
}
