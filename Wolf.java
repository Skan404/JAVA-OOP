import java.awt.*;

public class Wolf extends Animal {
    public Wolf() {
        super(9, 5);
    }

    public void action() {
        super.action();
    }

    public void print() {
        System.out.print("W");
    }

    public String getSpecies() {
        return "Wolf";
    }

    public Wolf clone() {
        return new Wolf();
    }

    @Override
    public Color getColor() {
        return Color.GRAY;
    }

    public String printLetter()
    {
        return "W";
    }
}
