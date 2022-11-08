import ast.AST;
import compiler.*;

import javax.swing.*;

public class Main {

    private static final String EXAMPLES_DIR = "C:\\Uni\\Semester7\\CMC1\\MC Donald 1";


    public static void main( String args[] )
    {
        JFileChooser fc = new JFileChooser( EXAMPLES_DIR );

        if( fc.showOpenDialog( null ) == fc.APPROVE_OPTION ) {
            SourceFile in = new SourceFile( fc.getSelectedFile().getAbsolutePath() );
            Scanner s = new Scanner( in );
            Parser p = new Parser( s );

            AST ast = p.parseProgram();

            new ASTViewer( ast );
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
