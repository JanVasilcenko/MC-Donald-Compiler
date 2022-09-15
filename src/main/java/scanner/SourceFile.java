package scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SourceFile {
    public static final char endOfLine = '\n';
    public static final char endOfTransmission = 0;

    private FileInputStream sourceFileInputStream;

    public SourceFile(String sourceFileName) {
        try {
            sourceFileInputStream = new FileInputStream(sourceFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find source file " + e.getMessage());
            System.exit(0);
        }
    }

    public char getSource() {
        try {
            int c = sourceFileInputStream.read();
            if (c < 0)
                return endOfTransmission;
            else
                return (char) c;
        } catch (IOException ex) {
            return endOfTransmission;
        }
    }
}
