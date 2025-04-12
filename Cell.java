import java.util.ArrayList;

public class Cell {

    private int m_row;
    private int m_column;
    public ArrayList<Organism> m_organisms = new ArrayList<>();

    public Organism getOrganism() {
        if (!m_organisms.isEmpty()) {
            return m_organisms.get(0);
        }

        return null;
    }

    public int getColumn() {
        return m_column;
    }

    public int getRow() {
        return m_row;
    }

    public void setRow(int row) {
        m_row = row;
    }

    public void setColumn(int column) {
        m_column = column;
    }
}
