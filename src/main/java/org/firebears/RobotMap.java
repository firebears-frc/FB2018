package org.firebears;

import org.firebears.recording.Recordable;
import org.firebears.recording.RecordingFactory;
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
import edu.wpi.first.wpilibj.Preferences;
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

	final static Preferences config = Preferences.getInstance();
	public static final boolean DEBUG = config.getBoolean("debug", true);

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

	// Variables for closed loop driving
	public static final int PID_IDX = 0;
	public static final int TIMEOUT_MS = 10;
	static double m_P = config.getDouble("chassis.pid.P", 0.25);
	static double m_I = config.getDouble("chassis.pid.I", 0.00);
	static double m_D = config.getDouble("chassis.pid.D", 0.05);
	static double m_ff = config.getDouble("chassis.pid.F", 1.75) ;//1.0; // 1.46;
	static int m_izone = config.getInt("chassis.pid.integralZone", 256);
	static double m_rampRate = config.getDouble("chassis.pid.rampRate", 0.00);
	static int m_profile = 0;
	public static int m_CountPerRev = 650;// ****Magnetic
    public static boolean CLOSED_LOOP_DRIVING = config.getBoolean("chassis.pid.closedLoop", true);

	// For autoSelecion
	public static String side;
	public static String priority;
	public static boolean shouldCross;
	public static boolean shouldSplit;

	// Different Sensors
	public static AHRS navXBoard;

	public static AnalogInput grabberRangeFinder;
	public static AnalogInput chassisRangeFinder;

	public static Encoder encoderLeft;
	public static Encoder encoderRight;

	public static DigitalInput tapeSensor;


	public static DigitalInput rightSensor;

	public static DigitalInput cubeSwitch;
	
	public static DigitalInput grabberUpPositionSensor;
	public static DigitalInput grabberDownPositionSensor;

	public static DigitalInput farLidarSensor;
	public static DigitalInput closeLidarSensor;
	
    public static RecordingFactory recordingFactory;
	
//	public static double degPerSec;
	
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
	
	public static boolean TestLightar = false;
	
	public static boolean DisableShooter = false;
	public static boolean DisableDrive = false;

	
	public static void init(RobotReport report) {

		// Set up motors for driving
		int chassisLeftMasterCanID = config.getInt("chassis.leftMaster.canID", 2);
		chassisLeftMaster = new CANTalon(chassisLeftMasterCanID);
		chassisLeftMaster.setName("Chassis", "FrontLeft");
		chassisLeftMaster.setNeutralMode(NeutralMode.Brake);
//		chassisLeftMaster.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisLeftMaster.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisLeftMaster.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisLeftMaster.enableCurrentLimit(true);
		report.addCAN(chassisLeftMasterCanID, "Left Master - PDP:3", chassisLeftMaster);

		int chassisLeftSlaveCanID = config.getInt("chassis.leftSlave.canID", 3);
		chassisLeftSlave = new CANTalon(chassisLeftSlaveCanID);
		chassisLeftSlave.setName("Chassis", "BackLeft");
		chassisLeftSlave.setNeutralMode(NeutralMode.Brake);
//		chassisLeftSlave.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisLeftSlave.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisLeftSlave.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisLeftSlave.enableCurrentLimit(true);
		report.addCAN(chassisLeftSlaveCanID, "Left Slave - PDP:2", chassisLeftSlave);

		chassisLeftMotors = new SpeedControllerGroup(chassisLeftMaster, chassisLeftSlave);
		// LiveWindow.addActuator("Chassis", "LeftMotors", chassisLeftMotors);

		int chassisRightMasterCanID = config.getInt("chassis.rightMaster.canID", 4);
		chassisRightMaster = new CANTalon(chassisRightMasterCanID);
		chassisRightMaster.setName("Chassis", "FrontRight");
		chassisRightMaster.setNeutralMode(NeutralMode.Brake);
//		chassisRightMaster.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisRightMaster.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisRightMaster.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisRightMaster.enableCurrentLimit(true);
		report.addCAN(chassisRightMasterCanID, "Right Master - PDP:1", chassisRightMaster);

		int chassisRightSlaveCanID = config.getInt("chassis.rightSlave.canID", 5);
		chassisRightSlave = new CANTalon(chassisRightSlaveCanID);
		chassisRightSlave.setName("Chassis", "BackRight");
		chassisRightSlave.setNeutralMode(NeutralMode.Brake);
//		chassisRightSlave.configPeakCurrentLimit(0, TIMEOUT_MS);
//		chassisRightSlave.configPeakCurrentDuration(0, TIMEOUT_MS);
//		chassisRightSlave.configContinuousCurrentLimit(20, TIMEOUT_MS);
//		chassisRightSlave.enableCurrentLimit(true);
		report.addCAN(chassisRightSlaveCanID, "Right Slave - PDP:0", chassisRightSlave);

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

		int grabberLeftMotorCanID = config.getInt("grabber.leftMotor.canID", 13);
		leftIntake = new CANTalon(grabberLeftMotorCanID);
		leftIntake.setName("Grabber", "leftIntake");
		leftIntake.setNeutralMode(NeutralMode.Brake);
		report.addCAN(grabberLeftMotorCanID, "leftIntake", leftIntake);

		int grabberRightMotorCanID = config.getInt("grabber.rightMotor.canID", 14);
		rightIntake = new CANTalon(grabberRightMotorCanID);
		rightIntake.setName("Grabber", "rightIntake");
		rightIntake.setNeutralMode(NeutralMode.Brake);
		report.addCAN(grabberRightMotorCanID, "rightIntake", rightIntake);

		int shooterLeftSpinnerCanID = config.getInt("shooter.leftSpinner.canID", 11);
		leftLaunchSpinner = new CANTalon(shooterLeftSpinnerCanID);
		leftLaunchSpinner.setSensorPhase(true);
		leftLaunchSpinner.setNeutralMode(NeutralMode.Coast);
		leftLaunchSpinner.setName("shooter", "leftSpinner");
		report.addCAN(shooterLeftSpinnerCanID, "leftSpinner", leftLaunchSpinner);

		int shooterRightSpinnerCanID = config.getInt("shooter.rightSpinner.canID", 12);
		rightLaunchSpinner = new CANTalon(shooterRightSpinnerCanID);
		rightLaunchSpinner.setSensorPhase(true);
		rightLaunchSpinner.setNeutralMode(NeutralMode.Coast);
		rightLaunchSpinner.setName("shooter", "rightSpinner");
		report.addCAN(shooterRightSpinnerCanID, "rightSpinner", rightLaunchSpinner);

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

		int grabberFarLidarDio = config.getInt("grabber.farLidar.dio", 0);
		farLidarSensor = new DigitalInput(grabberFarLidarDio);
		report.addDigitalIO(grabberFarLidarDio, "Grabber Lidar 0", farLidarSensor);
		
		int grabberCloseLidarDio = config.getInt("grabber.closeLidar.dio", 1);
		closeLidarSensor = new DigitalInput(grabberCloseLidarDio);
		report.addDigitalIO(grabberCloseLidarDio, "Grabber Lidar 1", closeLidarSensor);
		
		int chassisTapeSensorDio = config.getInt("chassis.tapeSensor.dio", 4);
		tapeSensor = new DigitalInput(chassisTapeSensorDio);
		report.addDigitalIO(chassisTapeSensorDio, "Tape Finder", tapeSensor);


		int chassisRightSensorDio = config.getInt("chassis.rightSensor.dio", 2);
		cubeSwitch = new DigitalInput(chassisRightSensorDio);
		report.addDigitalIO(chassisRightSensorDio, "Right Sensor", rightSensor);

		// Put Sensor for when cube is loaded here

		// Put Sensor for when cube is in the grabber here

		int grabberCubeSwitchDio = config.getInt("grabber.cubeSwitch.dio", 3);
		cubeSwitch = new DigitalInput(grabberCubeSwitchDio);
		report.addDigitalIO(grabberCubeSwitchDio, "Cube Detector", cubeSwitch);
		
		int grabberDownSensorDio = config.getInt("grabber.downSensor.dio", 6);
		grabberDownPositionSensor = new DigitalInput(grabberDownSensorDio);
		report.addDigitalIO(grabberDownSensorDio, "Grabber Down Position", grabberDownPositionSensor);

		int grabberUpSensorDio = config.getInt("grabber.upSensor.dio", 5);
		grabberUpPositionSensor = new DigitalInput(grabberUpSensorDio);
		report.addDigitalIO(grabberUpSensorDio, "Grabber Up Position", grabberUpPositionSensor);

		try {
			// navXBoard = new AHRS(SPI.Port.kMXP);
			navXBoard = new AHRS(Port.kUSB);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

//		pdp = new PowerDistributionPanel();
//		pdp.clearStickyFaults();

		report.addOtherConfig(Lights.I2C_ADDRESS, "Trinket I2C Address");
		
	    recordingFactory = new RecordingFactory();
	    recordingFactory.add(RobotMap.chassisLeftMaster);
	    recordingFactory.add(RobotMap.chassisRightMaster);
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
		return 0.0; //boundAngle(navXBoard.getAngle());
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

    public static class CANTalon extends WPI_TalonSRX implements Recordable {

        public int encoderMultiplier = 0;
        private double currentSpeed = 0.0;

        public CANTalon(int deviceNumber) {
            super(deviceNumber);
        }

        @Override
        public void set(double speed) {
            if (encoderMultiplier != 0) {
                // if (CLOSED_LOOP_DRIVING) {
                set(ControlMode.Velocity, speed * encoderMultiplier);
                SmartDashboard.putNumber(this + " Target", speed * encoderMultiplier);
            } else {
                set(ControlMode.PercentOutput, speed);
            }
            currentSpeed = speed;
        }

        @Override
        public double get() {
            return currentSpeed;
        }

        @Override
        public String toString() {
            return "CANTalon(" + getDeviceID() + ")";
        }

        public void configEncoderCodesPerRev(int ticks) {
            this.encoderMultiplier = ticks;
        }

        @Override
        public String getLabel() {
            return getName();
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
