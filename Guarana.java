import java.awt.*;

public class Guarana extends Plant {

    public Guarana() {
        super(0, 0.1, 3);
    }

    public void print() {
        System.out.print("g");
    }

    public Guarana clone() {
        return new Guarana();
    }

    public String getSpecies() {
        return "Guarana";
    }

    @Override
    public Color getColor() {
        return Color.RED.brighter();
    }

    public String printLetter()
    {
        return "g";
    }
}
