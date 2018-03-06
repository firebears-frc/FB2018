//package org.firebears.commands;
//
//import java.io.File;
//import java.io.PrintWriter;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class RecordVelocityCommand extends Command {
//	File f;
//	PrintWriter w;
//	long startTime;
//
//    public RecordVelocityCommand() {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	startTime = System.currentTimeMillis();
//		f = new File("/tmp/Recording.csv");
//		w = new PrintWriter(f);
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    }a
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
