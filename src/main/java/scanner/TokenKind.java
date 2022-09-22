package scanner;

public enum TokenKind {
    IDENTIFIER,
    INTEGERLITERAL,
    OPERATOR,

    BURGER("burger"),
    CHEESE("cheese"),
    EATIN("eatIn"),
    EATOUT("eatOut"),
    SHAKE("shake"),
    MC("MC"),

    LEFTOPENING("<"),
    RIGHTOPENING(">"),
    DASH("-"),
    SEMICOLON(";"),
    LEFTPAREN("("),
    RIGHTPAREN(")"),
    ASTERISK("*"),
    EOT,
    ERROR;


    private String spelling;

    TokenKind() {
    }

    TokenKind(String spelling) {
        this.spelling = spelling;
    }

    public String getSpelling() {
        return spelling;
    }
}
