package common;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Tools {

    private Tools() {
        // Prevent instantiation
    }

    /**
     * Write the given text to a file.  No error is thrown on failure, just used for debugging.
     * 
     * @param fileName
     * @param text
     */
    public static void writeFile(String fileName, String text) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            writer.write(text);
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                /* ignore */}
        }
    }

}
