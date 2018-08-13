package org.firebears.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Configuration properties loaded from property files. A suggested pattern
 * would be to have main properties, and then robot-specific properties, 
 * and some overrides: <p>
   <code>
      public static Config config = new Config("config.properties", "robot.properties", "override.properties");
   </code> <p>
 * Where <tt>config.properties</tt> would contain general properties and
 * would be in the source code at <tt>src/main/resources/config.properties</tt>.
 * The optional <tt>robot.properties</tt> file would contain properties specific
 * to a robot and would be saved on the roboRIO at location
 * <tt>/home/lvuser/robot.properties</tt>. The optional
 * <tt>override.properties</tt> file would be on a USB drive inserted into the
 * roboRIO.
 * <p>
 * Note that all the "get*" methods have two variations, both with and without a
 * backup value. For mandatory configuration properties (such as IDs for motors
 * and DIO and Analog input pins) it is better to <em>not</em> specify a backup
 * value. This provides better assurance that a valid configuration value is in
 * one of the property files.
 */
public class Config extends Properties {
    private static final long serialVersionUID = 1L;
    public boolean DEBUG = true;

    /**
     * Load a sequence of property files.
     * 
     * @param fileNames a sequence of file names to be loaded.
     */
    public Config(String... fileNames) {
        for (String fileName : fileNames) {
            load(fileName);
            DEBUG = getBoolean("debug", true);
        }
    }

    /**
     * Load a properties file in. If the file cannot be found in the file system,
     * attempt to load a file resource from the classpath. The new data may
     * overwrite keys that were previously loaded.
     * 
     * @param fileName relative or absolute file name or resource name.
     */
    protected void load(String fileName) {
        try {
            InputStream inStream = findStream(null, fileName);
            if (inStream == null) {
                inStream = findStream("/u", fileName);
            }
            if (inStream == null) {
                inStream = findStream("/home/lvuser", fileName);
            }
            if (inStream == null) {
                inStream = ClassLoader.getSystemResourceAsStream(fileName);
            }
            if (inStream == null) {
                if (DEBUG) {
                    System.out.println("Config: failed to read file: " + fileName);
                }
            } else {
                this.load(inStream);
            }
        } catch (IOException iox) {
            System.out.println("Config: failed reading file " + fileName);
            iox.printStackTrace();
        }
    }

    /**
     * If a file exists in the given directory, return a {@code FileInputStream} for
     * it.
     * 
     * @param dirName  a directory name or {@code null}.
     * @param fileName a file name or file path.
     * @return an {@code InputStream} or {@code null}.
     * @exception IOException if file input stream cannot be opened.
     */
    protected InputStream findStream(String dirName, String fileName) throws IOException {
        File dir = null;
        if (dirName != null) {
            dir = new File(dirName);
            if (!dir.exists()) {
                dir = null;
            }
        }
        File file = (dir != null) ? new File(dir, fileName) : new File(fileName);
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        if (DEBUG) {
            System.out.println("Reading properties from " + file);
        }
        return new FileInputStream(file);
    }

    /**
     * Returns the boolean at the given key. If this table does not have a value for
     * that position, then an error occurs, and the robot will not start.
     * 
     * @param key the key.
     * @return either the value in the table.
     * @exception RuntimeException if key is not in the table.
     */
    public boolean getBoolean(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Returns the boolean at the given key. If this table does not have a value for
     * that position, then the given backup value will be returned.
     * 
     * @param key the key.
     * @param backup the value to return if none exists in the table.
     * @return either the value in the table, or the backup.
     */
    public boolean getBoolean(String key, boolean backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getBoolean(key);
    }

    /**
     * Returns a Double number for the given key. If this table does not have a
     * value for that position, then an error occurs, and the robot will not start.
     * 
     * @param key the key.
     * @return either the value in the table.
     * @exception RuntimeException if key is not in the table. 
     */
    public double getDouble(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Double.parseDouble(getProperty(key));
    }

    /**
     * Returns a Double number for the given key. If this table does not have a
     * value for that position, then the given backup value will be returned.
     * 
     * @param key the key.
     * @param backup the value to return if none exists in the table.
     * @return either the value in the table, or the backup.
     */
    public double getDouble(String key, double backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getDouble(key);
    }

    /**
     * Returns a Float number for the given key. If this table does not have a value
     * for that position, then an error occurs, and the robot will not start.
     * 
     * @param key the key.
     * @return either the value in the table.
     * @exception RuntimeException if key is not in the table.
     */
    public float getFloat(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Float.parseFloat(getProperty(key));
    }

    /**
     * Returns a Float number for the given key. If this table does not have a value
     * for that position, then the given backup value will be returned.
     * 
     * @param key the key.
     * @param backup the value to return if none exists in the table.
     * @return either the value in the table, or the backup.
     */
    public float getFloat(String key, float backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getFloat(key);
    }

    /**
     * Returns an Integer for the given key. If this table does not have a value for
     * that position, then an error occurs, and the robot will not start.
     * 
     * @param key the key.
     * @return either the value in the table.
     * @exception RuntimeException if key is not in the table.
     */
    public int getInt(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Returns an Integer for the given key. If this table does not have a value for
     * that position, then the given backup value will be returned.
     * 
     * @param key the key.
     * @param backup the value to return if none exists in the table.
     * @return either the value in the table, or the backup.
     */
    public int getInt(String key, int backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getInt(key);
    }

    /**
     * Returns a Long integer for the given key. If this table does not have a value
     * for that position, then an error occurs, and the robot will not start.
     * 
     * @param key the key.
     * @return either the value in the table.
     * @exception RuntimeException if key is not in the table.
     */
    public long getLong(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Long.parseLong(getProperty(key));
    }

    /**
     * Returns a Long integer for the given key. If this table does not have a value
     * for that position, then the given backup value will be returned.
     * 
     * @param key the key.
     * @param backup the value to return if none exists in the table.
     * @return either the value in the table, or the backup
     */
    public long getLong(String key, long backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getLong(key);
    }

    /**
     * Print all the key/value pairs, with the keys sorted alphabetically.
     * 
     * @param outStream Stream for printing.  You may put <code>System.out</code> here.
     */
    public void print(PrintStream outStream) {
        outStream.println("# CONFIG PROPERTIES");
        SortedSet<String> keys = new TreeSet<String>(this.stringPropertyNames());
        for (String key : keys) {
            outStream.printf("%s=%s%n", key, this.getProperty(key));
        }
        outStream.println("#");
        outStream.flush();
    }
}
