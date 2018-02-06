package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Lights extends Subsystem {
  public static final int MAX_ANIMATIONS = 7;
  public static final int MAX_PIXELSTRIPS = 3;
  public static final int BRIGHTNESS = 128;
  public static final int I2C_ADDRESS = 4;
  public static final int BUILD_ANIMATION = 0;
  public static final int CELEBRATE_ANIMATION = 1;
  public static final int FADE_ANIMATION = 2;
  public static final int FIRE_ANIMATION = 3;
  public static final int FALL_ANIMATION = 4;
  public static final int NULL_ANIMATION = 5;
  public static final int PACMAN_ANIMATION = 6;
  public static final int SHOOTER_STRIP = 0;
  public static final int GRABBER_STRIP = 1;
  public static final int BASE_STRIP = 2;

  final I2C i2c;

  public Lights() {
    i2c =new I2C(Port.kOnboard, I2C_ADDRESS);
  }


    public void initDefaultCommand() {
      
    }
    private byte[] data = new byte[2];
    public synchronized void setAnimation(int s, int a) {
      data[0] = (byte) (s + '0');
      data[1] = (byte) (a + '0');
      i2c.transaction(data, 2, null, 0);
    }
}

