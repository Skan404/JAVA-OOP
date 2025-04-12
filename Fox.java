import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fox extends Animal {

    public Fox() {
        super(3, 7);
    }

    public void action() {
        List<Cell> neighbours = new ArrayList<>();
        m_world.getNeighbours(m_position, (ArrayList<Cell>) neighbours);

        for (int i = 0; i < neighbours.size(); ) {
            Organism o = neighbours.get(i).getOrganism();
            if (o != null && o.getStrength() > this.getStrength()) {
                neighbours.remove(i);
            } else {
                i++;
            }
        }

        if (!neighbours.isEmpty()) {
            int a = new Random().nextInt(neighbours.size());
            getWorld().moveTo(this, neighbours.get(a));
        }
    }

    public void print() {
        System.out.print("F");
    }

    public String getSpecies() {
        return "Fox";
    }

    public Fox clone() {
        return new Fox();
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    public String printLetter()
    {
        return "F";
    }
}
