package checker;

import ast.Declaration;

public class IdEntry {
    public int level;
    public String id;
    public Declaration attribute;

    public IdEntry(int level, String id, Declaration attribute) {
        this.level = level;
        this.id = id;
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return level + "," + id;
    }
}
