# FB2018, as of 2018.04.07

roboRIO serial number: 030a4a34

## CAN

* 2 = Left Master - PDP:3 : CANTalon(2)
* 3 = Left Slave - PDP:2 : CANTalon(3)
* 4 = Right Master - PDP:1 : CANTalon(4)
* 5 = Right Slave - PDP:0 : CANTalon(5)
* 11 = leftSpinner : CANTalon(11)
* 12 = rightSpinner : CANTalon(12)
* 13 = leftIntake : CANTalon(13)
* 14 = rightIntake : CANTalon(14)

## DIO

* 0 = Grabber Down Position : edu.wpi.first.wpilibj.DigitalInput@163006a
* 1 = Grabber Up Position : edu.wpi.first.wpilibj.DigitalInput@1be847c
* 2 = Tape Finder : edu.wpi.first.wpilibj.DigitalInput@f0a63f
* 4 = Cube Detector : edu.wpi.first.wpilibj.DigitalInput@1f4c3

## ANALOG IN

* 0 = Range Finder : edu.wpi.first.wpilibj.AnalogInput@104e3b2
* 1 = Range Finder 2 : edu.wpi.first.wpilibj.AnalogInput@19619aa
* 2 = Pressure Sensor : edu.wpi.first.wpilibj.AnalogInput@ac736f

## Other Configuration

* 4 = Trinket I2C Address

## Pneumatics

* [0,1,0] = leftPneumatics : edu.wpi.first.wpilibj.DoubleSolenoid@774085
* [0,3,2] = rightPneumatics : edu.wpi.first.wpilibj.DoubleSolenoid@8102c8
* [0,5,4] = leftOpenClose : edu.wpi.first.wpilibj.DoubleSolenoid@996db8
* [0,7,6] = rightOpenClose : edu.wpi.first.wpilibj.DoubleSolenoid@163006a
* [1,1,0] = leftUpDown : edu.wpi.first.wpilibj.DoubleSolenoid@1be847c
* [1,3,2] = rightUpDown : edu.wpi.first.wpilibj.DoubleSolenoid@1975e01

## Joysticks

* 0 = Joystick 1 : edu.wpi.first.wpilibj.Joystick@d072a9
    * 7 = Test Motors Forward : TestMotors
    * 9 = Test Motors Backward : TestMotors
* 1 = Joystick 2 : edu.wpi.first.wpilibj.Joystick@75222b
    * 1 = Shoot : FireCubeCommand
    * 7 = Arm Down : GrabberDownCommand
    * 9 = Arm Up : GrabberUpCommand
    * 11 = Spinner wheels : SpinGrabberWheelsCommand
    * 12 = Grabber wheels : SpinGrabberWheelsCommand
    * 13 = Celebrate : CelebrateCommand
    * 14 = Toggle arms open/closed : OpenGrabberCommand
    * 2 = Right-side : Autonomous
    * 3 = Left-side : Autonomous
    * 8 = Split : Autonomous
    * 15 = Switch/Scale : Autonomous
    * 16 = Cross/Dont-cross : Autonomous

