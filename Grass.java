import java.awt.*;

public class Grass extends Plant {
    public Grass() {
        super(0, 0.1, 0);
    }

    public void print() {
        System.out.print("g");
    }

    public Grass clone() {
        return new Grass();
    }

    public String getSpecies() {
        return "Grass";
    }

    @Override
    public Color getColor() {
        return Color.green;
    }

    public String printLetter()
    {
        return "g";
    }
}
