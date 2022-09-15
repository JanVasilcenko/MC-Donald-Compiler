package scanner;

public enum TokenKind {
    IDENTIFIER,
    INTEGERLITERAL,
    OPERATOR,

    BURGER("burger"),
    CHEESE("cheese"),
    EATIN("eat.in"),
    EATOUT("eat.out"),
    SHAKE("shake"),

    LEFTOPENING("<"),
    RIGHTOPENING(">"),
    DASH("-"),
    SEMICOLON(";"),
    LEFTPAREN("("),
    RIGHTPAREN(")"),
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
