package org.firebears.util;

import static org.firebears.util.Config.BOOLEAN;
import static org.firebears.util.Config.DOUBLE;
import static org.firebears.util.Config.FLOAT;
import static org.firebears.util.Config.INTEGER;
import static org.firebears.util.Config.LONG;
import static org.firebears.util.Config.loadProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigTest {

    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    void testloadProperties_null_stream() throws IOException {
        // Arrange
        InputStream inStream = null;
        
        // Act
        Properties properties = loadProperties(inStream);
        
        // Assert
        assertEquals(0, properties.size());
    }
    
    @Test
    void testloadProperties() throws IOException {
        // Arrange
        InputStream inStream = new StringBufferInputStream(
                        "# Test\n" + 
                        "weight=100\n" + 
                        "height=150.25\n" +
                        "color=blue\n" +
                        "alive=true\n" +
                        "xyzzy=\n"  );
        
        // Act
        Properties properties = loadProperties(inStream);
        
        // Assert
        assertEquals(5, properties.size());
        assertEquals("blue", properties.getProperty("color"));
        assertEquals("150.25", properties.getProperty("height"));
        assertEquals("", properties.getProperty("xyzzy"));
    }
    
    @Test
    void testRegex() {
        assertTrue("true".matches(BOOLEAN));
        assertTrue("false".matches(BOOLEAN));
        assertFalse("".matches(BOOLEAN));
        assertFalse("1234L".matches(BOOLEAN));
        assertFalse(" true ".matches(BOOLEAN));
        
        assertTrue("0".matches(INTEGER));
        assertTrue("1234".matches(INTEGER));
        assertTrue("-9876".matches(INTEGER));
        assertFalse("".matches(INTEGER));
        assertFalse("    ".matches(INTEGER));
        assertFalse(" 5 ".matches(INTEGER));
        assertFalse("true".matches(INTEGER));
        assertFalse("1234L".matches(INTEGER));
        assertFalse("2.3".matches(INTEGER));
        
        assertTrue("0L".matches(LONG));
        assertTrue("1234L".matches(LONG));
        assertTrue("-9876L".matches(LONG));
        assertFalse("".matches(LONG));
        assertFalse("    ".matches(LONG));
        assertFalse(" 5L ".matches(LONG));
        assertFalse("true".matches(LONG));
        assertFalse("1234".matches(LONG));
        assertFalse("2.3".matches(LONG));
        
        assertTrue("0.0F".matches(FLOAT));
        assertTrue("3.1415F".matches(FLOAT));
        assertTrue("-2.1718F".matches(FLOAT));
        assertFalse("".matches(FLOAT));
        assertFalse("    ".matches(FLOAT));
        assertFalse(" 5.1 ".matches(FLOAT));
        assertFalse("true".matches(FLOAT));
        assertFalse("1234".matches(FLOAT));
        assertFalse("1234F".matches(FLOAT));
        
        assertTrue("0.0".matches(DOUBLE));
        assertTrue("0.0D".matches(DOUBLE));
        assertTrue("-10.1D".matches(DOUBLE));
        assertTrue("3.1415".matches(DOUBLE));
        assertTrue("-2.1718".matches(DOUBLE));
        assertFalse("3.1415F".matches(DOUBLE));
        assertFalse("".matches(DOUBLE));
        assertFalse("    ".matches(DOUBLE));
        assertFalse(" 5.1 ".matches(DOUBLE));
        assertFalse("true".matches(DOUBLE));
        assertFalse("1234".matches(DOUBLE));
    }

}
