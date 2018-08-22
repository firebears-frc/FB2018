# Firebears Recording Functions

The classes in `org.firebears.recording` allows robot motion to be recorded
out to text files in, and then later played back on the robot.  These recording
files can be manipulated in a text editor or a spreadsheet  program.  It should 
also be easy to create software that reads and writes recordings.  It should 
also be possible to integrate to other motion profiling packages.

Recording files are in comma-separated-value (CSV) format.  The first column of
the file is the delta-time, i.e. the number of milliseconds since the previous
recording.  Columns after the delta-time represent values from different 
`Recordable` objects on the robot.

## Configuring recording with a RecordingFactory

Your robot program should create one `RecordingFactory` object sometime during
the `robotInit()` call.  This factory object defines all the `Recordable`
sensors, joysticks, or motors that will be recorded.  A typical factory might
be created like this:

```java
RecordingFactory recordingFactory = new RecordingFactory();
recordingFactory.add(leftMotorController, "leftMotor");
recordingFactory.add(rightMotorController, "rightMotor");
recordingFactory.add(joystick, AxisType.kX, "x-axis");
recordingFactory.add(joystick, AxisType.kY, "y-axis");
```

## Recording motion

Typically, set up a command button on the SmartDashboard or ShuffleBoard to 
start and stop recording motion.  These buttons can be set up as follows:

```java
SmartDashboard.putData("Start Recording", new StartRecordingCommand(recordingFactory));
SmartDashboard.putData("Stop Recording", new StopRecordingCommand(recordingFactory));
SmartDashboard.putData("Play Recording", new PlayRecordingCommand(recordingFactory));
```

A typical recording session would be:

1. Position the robot
2. Push the "Start Recording" button
3. Drive the robot
4. Push the "Stop Recording" button
5. Test this recording by pushing "Play Recording"
6. If this recording is worth saving, retrieve the CSV file.

If a USB flash drive is connected to the RoboRIO, then the files will be saved 
out to that drive.  Otherwise the files will be saved to the RoboRIO's `/tmp`
directory.  Note that this `/tmp` directory may be deleted when the robot is
rebooted.

## Using Recordings

It is a good idea to save your CSV recording files with your program code, so
they will be managed by your source code control system (e.g. github).  It is
also possible to keep them on a USB flash drive, so they can be manipulated off
the robot.

Using recording commands might look like this:

```java
autonomousCommand = new PlayRecordingCommand(recordingFactory, "LeftSideDriveStraight.csv");
```


