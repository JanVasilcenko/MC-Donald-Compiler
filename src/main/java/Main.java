import scanner.Scanner;
import scanner.SourceFile;
import scanner.Token;
import scanner.TokenKind;

import javax.swing.*;

public class Main {
    private static final String SOURCEDIRECTORY = "C:\\Users\\45502\\Downloads\\lang";

    public static void main(String[] args) {
        JFileChooser fc = new JFileChooser(SOURCEDIRECTORY);

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            SourceFile sourceFile = new SourceFile(fc.getSelectedFile().getAbsolutePath());
            Scanner scanner = new Scanner(sourceFile);

            Token token = scanner.scan();
            while (token.kind != TokenKind.EOT) {
                System.out.println(token.kind + " " + token.spelling);

                token = scanner.scan();
            }
        }
    }
}
