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
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	static double m_P = 0.25;
	static double m_I = 0.0;
	static double m_D = 0.05;
	static double m_ff = 1.75;//1.0; // 1.46;
	static int m_izone = 256;
	static double m_rampRate = 0.0;
	static int m_profile = 0;
	public static int m_CountPerRev = 650;// ****Magnetic
	public static boolean CLOSED_LOOP_DRIVING = true;

	// For autoSelecion
	public static String side;
	public static String priority;
	public static boolean shouldCross;

	// Different Sensors
	public static AHRS navXBoard;

	public static AnalogInput grabberRangeFinder;
	public static AnalogInput chassisRangeFinder;

	public static Encoder encoderLeft;
	public static Encoder encoderRight;

	public static DigitalInput tapeSensor;

	public static DigitalInput cubeSwitch;
	
	public static DigitalInput grabberUpPositionSensor;
	public static DigitalInput grabberDownPositionSensor;

	public static DigitalInput lidarSensor0;
	public static DigitalInput lidarSensor1;
	
	public static boolean TestDriveSimLeftMasterPositive = false;
	public static boolean TestDriveSimLeftMasterNegative = false;
	public static boolean TestDriveSimLeftSlavePositive = false;
	public static boolean TestDriveSimLeftSlaveNegative = false;
	
	public static boolean TestDriveSimRightMasterPositive = false;
	public static boolean TestDriveSimRightMasterNegative = false;
	public static boolean TestDriveSimRightSlavePositive = false;
	public static boolean TestDriveSimRightSlaveNegative = false;
	
	public static boolean TestDriveSim = false;

	
	public static boolean TestShooterLeftPositive = false;
	public static boolean TestShooterLeftNegative = false;
	public static boolean TestShooterRightPositive = false;
	public static boolean TestShooterRightNegative = false;
	
	public static boolean TestShooter = false;
	

	public static boolean TestSolenoidGrabberVertUp = false;
	public static boolean TestSolenoidGrabberVertDown = false;

	public static boolean TestSolenoid = false;


	public static boolean TestNavx = false;
	public static boolean TestUltrasonic = false;
	public static boolean TestVision = false;
	public static boolean TestVoltage = false;
	public static boolean TestPressure = false;
	
	public static boolean DisableShooter = false;

	
	public static void init(RobotReport report) {

		// Set up motors for driving
		chassisLeftMaster = new CANTalon(CAN_LEFT_MASTER);
		chassisLeftMaster.setName("Chassis", "FrontLeft");
		chassisLeftMaster.setNeutralMode(NeutralMode.Brake);
//		chassisLeftMaster.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisLeftMaster.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisLeftMaster.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisLeftMaster.enableCurrentLimit(true);
		report.addCAN(CAN_LEFT_MASTER, "Left Master - PDP:3", chassisLeftMaster);

		chassisLeftSlave = new CANTalon(CAN_LEFT_SLAVE);
		chassisLeftSlave.setName("Chassis", "BackLeft");
		chassisLeftSlave.setNeutralMode(NeutralMode.Brake);
//		chassisLeftSlave.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisLeftSlave.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisLeftSlave.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisLeftSlave.enableCurrentLimit(true);
		report.addCAN(CAN_LEFT_SLAVE, "Left Slave - PDP:2", chassisLeftSlave);

		chassisLeftMotors = new SpeedControllerGroup(chassisLeftMaster, chassisLeftSlave);
		// LiveWindow.addActuator("Chassis", "LeftMotors", chassisLeftMotors);

		chassisRightMaster = new CANTalon(CAN_RIGHT_MASTER);
		chassisRightMaster.setName("Chassis", "FrontRight");
		chassisRightMaster.setNeutralMode(NeutralMode.Brake);
//		chassisRightMaster.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisRightMaster.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisRightMaster.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisRightMaster.enableCurrentLimit(true);
		report.addCAN(CAN_RIGHT_MASTER, "Right Master - PDP:1", chassisRightMaster);

		chassisRightSlave = new CANTalon(CAN_RIGHT_SLAVE);
		chassisRightSlave.setName("Chassis", "BackRight");
		chassisRightSlave.setNeutralMode(NeutralMode.Brake);
//		chassisRightSlave.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisRightSlave.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisRightSlave.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisRightSlave.enableCurrentLimit(true);
		report.addCAN(CAN_RIGHT_SLAVE, "Right Slave - PDP:0", chassisRightSlave);

		chassisRightMotors = new SpeedControllerGroup(chassisRightMaster, chassisRightSlave);

		chassisLeftSlave.follow(chassisLeftMaster);
		chassisRightSlave.follow(chassisRightMaster);
		chassisRobotDrive = new DifferentialDrive(chassisLeftMaster, chassisRightMaster);

		chassisRobotDrive.setSafetyEnabled(true);
		chassisRobotDrive.setExpiration(0.1);
		chassisRobotDrive.setMaxOutput(1.0);

		// Set up parameters for closed loop driving
		if (CLOSED_LOOP_DRIVING) {
			chassisLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
			chassisLeftMaster.configEncoderCodesPerRev(m_CountPerRev);
			chassisLeftMaster.setSensorPhase(true);
//			chassisLeftMaster.
			setPID(chassisLeftMaster, m_P, m_I, m_D, m_ff, m_izone, m_rampRate, m_profile);
			
			chassisRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, TIMEOUT_MS);
			chassisRightMaster.configEncoderCodesPerRev(m_CountPerRev);
			chassisRightMaster.setSensorPhase(true);
			setPID(chassisRightMaster, m_P, m_I, m_D, m_ff, m_izone, m_rampRate, m_profile);
		}

		leftIntake = new CANTalon(CAN_LEFT_GRABBER_MOTOR);
		leftIntake.setName("Grabber", "leftIntake");
		leftIntake.setNeutralMode(NeutralMode.Brake);
		report.addCAN(CAN_LEFT_GRABBER_MOTOR, "leftIntake", leftIntake);

		rightIntake = new CANTalon(CAN_RIGHT_GRABBER_MOTOR);
		rightIntake.setName("Grabber", "rightIntake");
		rightIntake.setNeutralMode(NeutralMode.Brake);
		report.addCAN(CAN_RIGHT_GRABBER_MOTOR, "rightIntake", rightIntake);

		leftLaunchSpinner = new CANTalon(CAN_LEFT_CUBE_SPINNER);
		leftLaunchSpinner.setSensorPhase(true);
		leftLaunchSpinner.setNeutralMode(NeutralMode.Coast);
		leftLaunchSpinner.setName("shooter", "leftSpinner");
		report.addCAN(CAN_LEFT_CUBE_SPINNER, "leftSpinner", leftLaunchSpinner);

		rightLaunchSpinner = new CANTalon(CAN_RIGHT_CUBE_SPINNER);
		rightLaunchSpinner.setSensorPhase(true);
		rightLaunchSpinner.setNeutralMode(NeutralMode.Coast);
		rightLaunchSpinner.setName("shooter", "rightSpinner");
		report.addCAN(CAN_RIGHT_CUBE_SPINNER, "rightSpinner", rightLaunchSpinner);

		leftLaunch = new DoubleSolenoid(0, 1, 0);
		leftLaunch.setName("shooter", "leftPneumatics");
		report.addPcm(0, 1, 0, "leftPneumatics", leftLaunch);

		rightLaunch = new DoubleSolenoid(0, 3, 2);
		rightLaunch.setName("shooter", "rightPneumatics");
		report.addPcm(0, 3, 2, "rightPneumatics", rightLaunch);

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

		grabberRangeFinder = new AnalogInput(0);
		grabberRangeFinder.setName("Grabber", "Rangefinder");
		report.addAnalogInput(0, "Range Finder", grabberRangeFinder);

		chassisRangeFinder = new AnalogInput(1);
		chassisRangeFinder.setName("Chassis", "Rangefinder2");
		report.addAnalogInput(1, "Range Finder 2", chassisRangeFinder);

		pressureSensor = new AnalogInput(2);
		pressureSensor.setName("Chassis", "PressureSensor");
		report.addAnalogInput(2, "Pressure Sensor", pressureSensor);

		// Put Ultrasonic Switches here

		lidarSensor0 = new DigitalInput(0);
		report.addDigitalIO(0, "Grabber Lidar 0", lidarSensor0);
		
		lidarSensor1 = new DigitalInput(1);
		report.addDigitalIO(1, "Grabber Lidar 1", lidarSensor1);
		
		tapeSensor = new DigitalInput(2);
		report.addDigitalIO(2, "Tape Finder", tapeSensor);

		// Put Sensor for when cube is loaded here

		// Put Sensor for when cube is in the grabber here
		cubeSwitch = new DigitalInput(4);
		report.addDigitalIO(4, "Cube Detector", cubeSwitch);
		
		grabberDownPositionSensor = new DigitalInput(6);
		report.addDigitalIO(0, "Grabber Down Position", grabberDownPositionSensor);

		grabberUpPositionSensor = new DigitalInput(5);
		report.addDigitalIO(1, "Grabber Up Position", grabberUpPositionSensor);

		try {
			// navXBoard = new AHRS(SPI.Port.kMXP);
			navXBoard = new AHRS(Port.kUSB);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

//		pdp = new PowerDistributionPanel();
//		pdp.clearStickyFaults();

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
			if (encoderMultiplier != 0) {
//			if (CLOSED_LOOP_DRIVING) {
				set(ControlMode.Velocity, speed * encoderMultiplier);
				SmartDashboard.putNumber(this + " Target", speed * encoderMultiplier);
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
	
	public static class EncoderPIDSource implements PIDSource {

		PIDSourceType pidType;
		WPI_TalonSRX sourceTalon;
		
		public EncoderPIDSource(WPI_TalonSRX talon) {
			sourceTalon = talon;
		}
		
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			pidType = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kRate;
		}

		@Override
		public double pidGet() {
			return sourceTalon.getSelectedSensorVelocity(PID_IDX);
		}
		
	}
}
