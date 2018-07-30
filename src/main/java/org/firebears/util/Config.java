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
 * Configuration properties loaded from property files.  
 */
public class Config extends Properties {
    private static final long serialVersionUID = 1L;

    /**
     * @param fileNames a sequence of file names to be loaded.
     */
    public Config(String... fileNames) {
        for (String fileName : fileNames) {
            load(fileName);
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
                System.out.println("Config: failed to read file: " + fileName);
            } else {
                this.load(inStream);
            }
        } catch (IOException iox) {
            System.out.println("Config: failed reading file " + fileName);
            iox.printStackTrace();
        }
    }
    
    /**
     * If a file exists in the given directory, return a {@code FileInputStream} for it.
     * 
     * @param dirName a directory name or {@code null}.
     * @param fileName a file name or file path.
     * @return an {@code InputStream} or {@code null}.
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
        return (file.exists() && file.canRead()) ? new FileInputStream(file) : null;
    }

    public boolean getBoolean(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Boolean.parseBoolean(getProperty(key));
    }

    public boolean getBoolean(String key, boolean backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getBoolean(key);
    }

    public double getDouble(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Double.parseDouble(getProperty(key));
    }

    public double getDouble(String key, double backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getDouble(key);
    }

    public float getFloat(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Float.parseFloat(getProperty(key));
    }

    public float getFloat(String key, float backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getFloat(key);
    }

    public int getInt(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Integer.parseInt(getProperty(key));
    }

    public int getInt(String key, int backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getInt(key);
    }

    public long getLong(String key) {
        if (!containsKey(key)) {
            throw new RuntimeException("No config for " + key);
        }
        return Long.parseLong(getProperty(key));
    }

    public long getLong(String key, long backup) {
        if (!containsKey(key)) {
            return backup;
        }
        return getLong(key);
    }

    /**
     * Print all the key/value pairs, with the keys sorted alphabetically.
     */
    public void print(PrintStream outStream) {
        SortedSet<String> keys = new TreeSet<String>(this.stringPropertyNames()); 
        for (String key : keys) {
            outStream.printf("%s=%s%n", key, this.getProperty(key));
        }
        outStream.flush();
    }
}
