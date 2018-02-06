package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import java.io.*;
import java.util.Scanner;

import org.firebears.Robot;

/**
 * Play a previously recorded command.
 */
public class PlayMirroredRecording extends Command {
	InputStream stream;
	Scanner scanner;
	long time;
	long startTime;
	double forwardAmount;
	double rotateAmount;
	boolean hasMore;
	final String fileName;

	public PlayMirroredRecording() {
		this("/tmp/Recording.csv");
	}

	public PlayMirroredRecording(String name) {
		requires(Robot.chassis);
		fileName = name;
	}

	public boolean readLine() {
		if (!scanner.hasNext()) {
			return false;
		}
		time = scanner.nextLong();
		forwardAmount = scanner.nextDouble();
		rotateAmount = scanner.nextDouble();
		scanner.nextLine();
		return true;

	}

	protected void initialize() {
		try {
			startTime = System.currentTimeMillis();
			if (fileName.startsWith("/tmp/") || fileName.startsWith("/home/lvuser/") || fileName.startsWith("/U/")) {
				File f = new File(fileName);
				stream = new FileInputStream(f);
			} else {
				stream = ClassLoader.getSystemResourceAsStream(fileName);
			}
			scanner = (new Scanner(stream)).useDelimiter(",");
			hasMore = readLine();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	protected void execute() {
		long currentTime = System.currentTimeMillis() - startTime;
		while ((currentTime > time) && (hasMore)) {
			Robot.chassis.drive(forwardAmount, -rotateAmount,true);
			hasMore = readLine();
		}
	}

	protected boolean isFinished() {
		return !hasMore;
	}

	protected void end() {
		scanner.close();
	}

	protected void interrupted() {
		scanner.close();
	}
}
