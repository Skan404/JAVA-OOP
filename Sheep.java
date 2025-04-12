import java.awt.*;

public class Sheep extends Animal {

    public Sheep() {
        super(4, 4);
    }

    public void action() {
        super.action();
    }

    public void print() {
        System.out.print("S");
    }

    public String getSpecies() {
        return "Sheep";
    }

    public Sheep clone() {
        return new Sheep();
    }

    @Override
    public Color getColor() {
        return Color.white;
    }

    public String printLetter()
    {
        return "S";
    }
}
