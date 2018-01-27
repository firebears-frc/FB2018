package org.firebears.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * A report about the current configuration of one robot. Generates text in
 * Markdown format.
 */
public class RobotReport {

	private final String name;
	private String description = null;
	private Map<Integer, Tuple> digitalIoMap = new TreeMap<>();
	private Map<Integer, Tuple> analogInputMap = new TreeMap<>();
	private Map<Integer, Tuple> pwmMap = new TreeMap<>();
	private Map<Integer, Tuple> canMap = new TreeMap<>();
	private Map<Integer, Tuple> relayMap = new TreeMap<>();
	private Map<Integer, Tuple> joystickMap = new TreeMap<>();
	private Map<Integer, Map<Integer, Tuple>> joystickButtonMap = new TreeMap<>();
	private Map<Integer, String> otherConfigMap = new TreeMap<>();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private final DecimalFormat numberFormat = new DecimalFormat("###,###,###,###,##0");

	public RobotReport(String name) {
		this.name = name;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void addDigitalIO(Integer id, String desc, Object component) {
		digitalIoMap.put(id, new Tuple(desc, component));
	}

	public void addAnalogInput(Integer id, String desc, Object component) {
		analogInputMap.put(id, new Tuple(desc, component));
	}

	public void addPWM(Integer id, String desc, Object component) {
		pwmMap.put(id, new Tuple(desc, component));
	}

	public void addCAN(Integer id, String desc, Object component) {
		canMap.put(id, new Tuple(desc, component));
	}

	public void addRelay(Integer id, String desc, Object component) {
		relayMap.put(id, new Tuple(desc, component));
	}

	public void addOtherConfig(Integer id, String desc) {
		otherConfigMap.put(id, desc);
	}

	public void addJoystick(Integer joystickId, String desc, Joystick stick) {
		joystickMap.put(joystickId, new Tuple(desc, stick));
		joystickButtonMap.put(joystickId, new TreeMap<Integer, Tuple>());
	}

	public void addJoystickButton(Integer joystickId, Integer buttonId, String desc, Object command) {
		Map<Integer, Tuple> buttonMap = joystickButtonMap.get(joystickId);
		if (joystickButtonMap.get(joystickId) == null) {
			buttonMap = new TreeMap<Integer, Tuple>();
			joystickButtonMap.put(joystickId, buttonMap);
		}
		buttonMap.put(buttonId, new Tuple(desc, command));
	}

	public void write(OutputStream strm) {
		write(strm, false);
	}

	public void write(OutputStream strm, boolean verbose) {
		final PrintStream out = new PrintStream(strm);
		out.printf("# %s, as of %s%n", name, dateFormat.format(new Date()));
		out.println();

		if (isNotBlank(System.getenv("serialnum"))) {
			out.printf("roboRIO serial number: %s%n", System.getenv("serialnum"));
			out.println();
		}

		if (isNotBlank(description)) {
			out.println(description);
			out.println();
		}

		if (!canMap.isEmpty()) {
			out.println("## CAN");
			out.println();
			for (Integer id : canMap.keySet()) {
				out.printf("* %d = %s%n", id, canMap.get(id));
			}
			out.println();
		}

		if (!digitalIoMap.isEmpty()) {
			out.println("## DIO");
			out.println();
			for (Integer id : digitalIoMap.keySet()) {
				out.printf("* %d = %s%n", id, digitalIoMap.get(id));
			}
			out.println();
		}

		if (!relayMap.isEmpty()) {
			out.println("## RELAY");
			out.println();
			for (Integer id : relayMap.keySet()) {
				out.printf("* %d = %s%n", id, relayMap.get(id));
			}
			out.println();
		}

		if (!analogInputMap.isEmpty()) {
			out.println("## ANALOG IN");
			out.println();
			for (Integer id : analogInputMap.keySet()) {
				out.printf("* %d = %s%n", id, analogInputMap.get(id));
			}
			out.println();
		}

		if (!pwmMap.isEmpty()) {
			out.println("## PWM");
			out.println();
			for (Integer id : pwmMap.keySet()) {
				out.printf("* %d = %s%n", id, pwmMap.get(id));
			}
			out.println();
		}

		if (!otherConfigMap.isEmpty()) {
			out.println("## Other Configuration");
			out.println();
			for (Integer id : otherConfigMap.keySet()) {
				out.printf("* %d = %s%n", id, otherConfigMap.get(id));
			}
			out.println();
		}

		if (!joystickMap.isEmpty()) {
			out.println("## Joysticks");
			out.println();
			for (Integer joystickId : joystickMap.keySet()) {
				out.printf("* %d = %s%n", joystickId, joystickMap.get(joystickId));
				Map<Integer, Tuple> buttonMap = joystickButtonMap.get(joystickId);
				if (buttonMap != null && !buttonMap.isEmpty()) {
					for (Integer buttonId : buttonMap.keySet()) {
						out.printf("    * %d = %s%n", buttonId, buttonMap.get(buttonId));
					}
				}
			}
			out.println();
		}

		if (verbose) {
			out.println("## DriverStation");
			out.println();
			DriverStation driverStation = DriverStation.getInstance();
			out.printf("* DS voltage = %5.2f%n", driverStation.getBatteryVoltage());
			out.printf("* alliance = %s%n", driverStation.getAlliance());
			out.println();

			out.println("## Power Distribution Panel");
			out.println();
			PowerDistributionPanel pdp = new PowerDistributionPanel();
			out.printf("* PDP voltage = %5.2f%n", pdp.getVoltage());
			out.printf("* PDP current = %5.2f%n", pdp.getTotalCurrent());
			out.printf("* PDP temp = %5.2f C%n", pdp.getTemperature());
			out.println();

			out.println("## System Properties");
			out.println();
			for (String propName : (new TreeSet<String>(System.getProperties().stringPropertyNames()))) {
				if (propName.equals("line.separator"))
					continue;
				out.printf("* %s = %s%n", propName, System.getProperty(propName));
			}
			out.println();

			out.println("## Environment Variables");
			out.println();
			for (String varName : (new TreeSet<String>(System.getenv().keySet()))) {
				out.printf("* %s = %s%n", varName, System.getenv(varName));
			}
			out.println();

			out.println("## Java Runtime");
			out.println();
			out.printf("* availableProcessors = %d%n", Runtime.getRuntime().availableProcessors());
			out.printf("* totalMemory = %s%n", numberFormat.format(Runtime.getRuntime().totalMemory()));
			out.printf("* maxMemory = %s%n", numberFormat.format(Runtime.getRuntime().maxMemory()));
			out.printf("* freeMemory = %s%n", numberFormat.format(Runtime.getRuntime().freeMemory()));
			out.println();

			out.println("## Java classpath");
			out.println();
			URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
			for (URL url : cl.getURLs()) {
				out.printf("* %s%n", url.getFile());
			}
			out.println();
		}

		out.flush();
	}
	
	public void write(File file) {
		write(file, false);
	}

	public void write(File file, boolean verbose) {
		try {
			OutputStream strm = new FileOutputStream(file);
			this.write(strm, verbose);
			strm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isNotBlank(String s) {
		return s != null && s.trim().length() > 0;
	}

	private class Tuple {
		public final String description;
		public final Object component;

		public Tuple(String desc, Object component) {
			this.description = desc;
			this.component = component;
		}

		public String toString() {
			return description + (component == null ? "" : " : " + component);
		}
	}
}
