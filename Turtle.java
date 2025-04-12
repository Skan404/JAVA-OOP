import java.awt.*;

public class Turtle extends Animal {

    public Turtle() {
        super(2, 1);
    }

    public void action() {
        int a = (int) (Math.random() * 4);
        if (a == 0) {
            super.action();
        }
    }

    public void print() {
        System.out.print("T");
    }

    public boolean isAttackCountered(Animal a) {
        return a.getStrength() < 5;
    }

    public String getSpecies() {
        return "Turtle";
    }

    public Turtle clone() {
        return new Turtle();
    }

    @Override
    public Color getColor() {
        return Color.green;
    }

    public String printLetter()
    {
        return "T";
    }
}
