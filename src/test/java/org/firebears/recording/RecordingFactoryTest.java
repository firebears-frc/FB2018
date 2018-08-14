package org.firebears.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Random;

import org.junit.jupiter.api.Test;

class RecordingFactoryTest {
    
    Random random = new Random();
    
    @Test
    void makeFakeRecordingFile() throws InterruptedException, IOException {
        // Arrange
        RecordingFactory f = new RecordingFactory();
        f.add(new RecordingFactory.FakeRecordable("left"));
        f.add(new RecordingFactory.FakeRecordable("right"));

        // Act
        Recording recording1 = f.newRecording();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            long currentTime = System.currentTimeMillis() - startTime;
            recording1.addLine(currentTime);
            Thread.sleep(19 + random.nextInt(3));
        }
        
        File outFile1 = File.createTempFile("recording_1_", ".csv");
         Writer out = new FileWriter(outFile1);
         f.save(recording1, out, "First test");
         out.close();
         System.out.println("outfile1 = " + outFile1.getCanonicalPath());
         
        Reader in = new FileReader(outFile1);
        Recording recording2 = f.load(in);
        in.close();

        java.io.File outFile2 = File.createTempFile("recording_2_", ".csv");
        out = new FileWriter(outFile2);
        f.save(recording2, out, "second test");
        out.close();
        System.out.println("outfile2 = " + outFile2.getCanonicalPath());

        // Assert
        assertTrue(outFile1.exists());
        assertTrue(outFile2.exists());
        assertEquals(recording1.size(), recording2.size());
    }
}
