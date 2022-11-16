import ast.AST;
import ast.Program;
import checker.Checker;
import compiler.*;
import encoder.Encoder;

import javax.swing.*;

public class Main {

    private static final String EXAMPLES_DIR = "C:\\Uni\\Semester7\\CMC1\\MC Donald 1";


    public static void main(String args[]) {
        JFileChooser fc = new JFileChooser(EXAMPLES_DIR);

        if (fc.showOpenDialog(null) == fc.APPROVE_OPTION) {
            String sourceName = fc.getSelectedFile().getAbsolutePath();

            SourceFile in = new SourceFile(sourceName);

            Scanner s = new Scanner(in);
            Parser p = new Parser(s);
            Checker c = new Checker();
            Encoder e = new Encoder();

            Program program = (Program) p.parseProgram();
            c.check(program);
            e.encode(program);

            String targetName;
            if (sourceName.endsWith(".txt")) {
                targetName = sourceName.substring(0, sourceName.length() - 4) + ".tam";
            } else {
                targetName = sourceName + ".tam";
            }
            e.saveTargetProgram(targetName);
        }
    }

//    private static final String EXAMPLES_DIR = "C:\\Uni\\Semester7\\CMC1\\MC Donald 1";
//
//
//    public static void main( String args[] )
//    {
//        JFileChooser fc = new JFileChooser( EXAMPLES_DIR );
//
//        if( fc.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
//            SourceFile in = new SourceFile( fc.getSelectedFile().getAbsolutePath() );
//            Scanner s = new Scanner( in );
//            Parser p = new Parser( s );
//
//            p.parseProgram();
//        }
//    }

//    private static final String SOURCEDIRECTORY = "C:\\Users\\Karrtii\\Downloads";
//
//    public static void main(String[] args) {
//        JFileChooser fc = new JFileChooser(SOURCEDIRECTORY);
//
//        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//            SourceFile sourceFile = new SourceFile(fc.getSelectedFile().getAbsolutePath());
//            Scanner scanner = new Scanner(sourceFile);
//
//            Token token = scanner.scan();
//            while (token.kind != TokenKind.EOT) {
//                System.out.println(token.kind + " " + token.spelling);
//
//                token = scanner.scan();
//            }
//        }
//    }
}
