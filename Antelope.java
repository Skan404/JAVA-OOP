import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antelope extends Animal {

    public Antelope() {
        super(2, 4);
    }

    public void action() {
        List<Cell> neighbours = new ArrayList<>();
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-2, 0}, {2, 0}, {0, -2}, {0, 2}};

        for (int i = 0; i < 8; i++) {
            int r = getPosition().getRow() + offsets[i][0];
            int c = getPosition().getColumn() + offsets[i][1];

            Cell neighbour = getWorld().getCellOrNull(r, c);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }

        if (!neighbours.isEmpty()) {
            int a = new Random().nextInt(neighbours.size());
            getWorld().moveTo(this, neighbours.get(a));
        }
    }

    public void print() {
        System.out.print("A");
    }

    public boolean escapeAttack() {
        List<Cell> neighbours = new ArrayList<>();
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < 4; i++) {
            int r = getPosition().getRow() + offsets[i][0];
            int c = getPosition().getColumn() + offsets[i][1];

            Cell neighbour = getWorld().getCellOrNull(r, c);
            if (neighbour != null && neighbour.getOrganism() == null) {
                neighbours.add(neighbour);
            }
        }

        if (!neighbours.isEmpty()) {
            int a = new Random().nextInt(neighbours.size());
            getWorld().moveTo(this, neighbours.get(a));
            return true;
        }
        return false;
    }

    public String getSpecies() {
        return "Antelope";
    }

    public Antelope clone() {
        return new Antelope();
    }

    @Override
    public Color getColor() {
        return Color.ORANGE.darker();
    }

    public String printLetter()
    {
        return "A";
    }
}
