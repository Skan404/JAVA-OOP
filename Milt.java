import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Milt extends Plant {

    public Milt() {
        super(0, 0, 0);
    }

    public void print() {
        System.out.print("g");
    }

    public Milt clone() {
        return new Milt();
    }

    public void breed() {
        ArrayList<Cell> neighbours = new ArrayList<>();
        m_world.getNeighbours(m_position, neighbours);

        for (int i = 0; i < 3; i++) {
            int a = (int) (Math.random() * neighbours.size());

            if (neighbours.get(a).getOrganism() == null) {
                m_world.addOrganism(clone(), neighbours.get(a).getRow(), neighbours.get(a).getColumn());
            }
        }
    }

    public String getSpecies() {
        return "Milt";
    }

    @Override
    public Color getColor() {
        return Color.yellow;
    }

    public String printLetter()
    {
        return "m";
    }
}
