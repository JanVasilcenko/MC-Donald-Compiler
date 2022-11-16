package checker;

import ast.Declaration;

import java.util.Vector;

public class IdentificationTable {
    private Vector<IdEntry> table = new Vector<>();
    private int level = 0;

    public IdentificationTable() {
    }

    public void enter(String id, Declaration attribute) {
        IdEntry entry = find(id);

        if (entry != null && entry.level == level) {
            System.out.println("Id " + id + " is declared twice!");
        } else {
            table.add(new IdEntry(level, id, attribute));
        }
    }

    public Declaration retrieve(String id) {
        IdEntry entry = find(id);

        if (entry != null) {
            return entry.attribute;
        }

        return null;
    }

    public void openScope() {
        ++level;
    }

    public void closeScope() {
        int position = table.size() - 1;
        while (position >= 0 && table.get(position).level == level) {
            table.remove(position);
            position--;
        }
        level--;
    }

    private IdEntry find(String id) {
        for (int i = table.size() - 1; i >= 0; i--) {
            if (table.get(i).id.equals(id)) {
                return table.get(i);
            }
        }
        return null;
    }
}
