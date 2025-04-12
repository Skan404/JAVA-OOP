import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hogweed extends Plant {

    public Hogweed() {
        super(10, 0, 0);
    }

    public void print() {
        System.out.print("h");
    }

    public void action() {
        super.action();

        List<Cell> neighbours = new ArrayList<>();
        m_world.getNeighbours(m_position, (ArrayList<Cell>) neighbours);
        for (int i = 0; i < neighbours.size(); i++) {
            Organism o = neighbours.get(i).getOrganism();
            if (o != null && o.isAnimal()) {
                System.out.println("Barszcz Sosnowskiego zabija " + o.getSpecies() + " na pozycji row: " + o.getPosition().getRow() + " column: " + o.getPosition().getColumn());
                m_world.kill(o);
            }
        }
    }

    public Hogweed clone() {
        return new Hogweed();
    }

    public boolean isPoisonous() {
        return true;
    }

    public String getSpecies() {
        return "Hogweed";
    }

    @Override
    public Color getColor() {
        return Color.white;
    }

    public String printLetter()
    {
        return "h";
    }
}
