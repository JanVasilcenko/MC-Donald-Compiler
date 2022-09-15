package scanner;

public class Token {
    public TokenKind kind;
    public  String spelling;

    public Token(TokenKind kind, String spelling) {
        this.kind = kind;
        this.spelling = spelling;
    }

    public boolean isAssignOperator() {
        if (kind == TokenKind.OPERATOR) {
            return containsOperator(spelling, assignOperators);
        }
        return false;
    }

    public boolean isAddOperator() {
        if (kind == TokenKind.OPERATOR) {
            return containsOperator(spelling, addOperators);
        }
        return false;
    }

    public boolean isMulOperator() {
        if (kind == TokenKind.OPERATOR) {
            return containsOperator(spelling, multiplicativeOperators);
        }
        return false;
    }

    private boolean containsOperator(String spelling, String[] ops) {
        for (int i = 0; i < ops.length; i++) {
            if (spelling.equals(ops[i])) {
                return true;
            }
        }
        return false;
    }

    private static final String[] assignOperators =
            {
                    "ordered",
                    "doubleordered"
            };


    private static final String[] addOperators =
            {
                    "chicken",
                    "vegan",
            };


    private static final String[] multiplicativeOperators =
            {
                    "beef",
                    "fish",
            };
}
