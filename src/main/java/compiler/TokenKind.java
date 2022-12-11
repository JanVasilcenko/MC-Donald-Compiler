package compiler;

public enum TokenKind {
    IDENTIFIER,
    INTEGERLITERAL,
    OPERATOR,

    IMLOVINIT("ImLovinIt"),
    BURGER("burger"),
    CHEESE("cheese"),
    EATIN("eatIn"),
    EATOUT("eatOut"),
    SHAKE("shake"),
    MC("MC"),
    NUGGETS("nuggets"),
    ICE("ice"),
    MEAL("meal"),
    SERVE("serve"),
    HAPPYMEAL("happymeal"),
    HARDOPENINGLEFT("["),
    HARDOPENINGRIGHT("]"),

    LEFTOPENING("<"),
    RIGHTOPENING(">"),
    COMMA(","),
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
