package compiler;

public class Scanner {
    private SourceFile sourceFile;

    private char currentChar;
    private StringBuffer currentSpelling = new StringBuffer();
    private int position;

    public Scanner(SourceFile sourceFile) {
        this.sourceFile = sourceFile;
        currentChar = sourceFile.getSource();
        position = 0;
    }

    private void takeIt() {
        currentSpelling.append(currentChar);
        currentChar = sourceFile.getSource();
    }

    private boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private void scanSeparator() {
        switch (currentChar) {
            case '#':
                takeIt();
                while (currentChar != SourceFile.endOfLine && currentChar != SourceFile.endOfText) {
                    takeIt();
                }

                if (currentChar == SourceFile.endOfLine) {
                    takeIt();
                }
                break;

            case ' ':
            case '\n':
            case '\r':
            case '\t':
                takeIt();
                break;
        }
    }

    private TokenKind scanToken() {
        if (isLetter(currentChar)) {
            takeIt();
            while (isLetter(currentChar) || isDigit(currentChar)) {
                takeIt();
            }
            return TokenKind.IDENTIFIER;
        } else if (isDigit(currentChar)) {
            takeIt();
            while (isDigit(currentChar)) {
                takeIt();
            }
            return TokenKind.INTEGERLITERAL;
        } else if(currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
            takeIt();
            return TokenKind.OPERATOR;
        } else if (currentChar == ',') {
            takeIt();
            return TokenKind.COMMA;
        } else if (currentChar == ';') {
            takeIt();
            return TokenKind.SEMICOLON;
        } else if (currentChar == '=') {
            takeIt();
            return TokenKind.OPERATOR;
        } else if (currentChar == '<') {
            takeIt();
            return TokenKind.LEFTOPENING;
        } else if (currentChar == '>') {
            takeIt();
            return TokenKind.RIGHTOPENING;
        } else if (currentChar == '(') {
            takeIt();
            return TokenKind.LEFTPAREN;
        } else if (currentChar == ')') {
            takeIt();
            return TokenKind.RIGHTPAREN;
        } else if (currentChar == SourceFile.endOfText) {
            return TokenKind.EOT;
        } else {
            takeIt();
            return TokenKind.ERROR;
        }
    }

    public Token scan() {
        while (currentChar == '#' || currentChar == '\n' || currentChar == '\r' || currentChar == '\t' || currentChar == ' ') {
            scanSeparator();
        }

        currentSpelling = new StringBuffer("");
        TokenKind kind = scanToken();

        position++;
        return new Token(kind, new String(currentSpelling));
    }

    public int getPosition() {
        return position;
    }
}
