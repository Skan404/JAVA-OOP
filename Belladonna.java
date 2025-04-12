import java.awt.*;

public class Belladonna extends Plant {

    public Belladonna() {
        super(0, 0.1, 0);
    }

    public void print() {
        System.out.print("b");
    }

    public Belladonna clone() {
        return new Belladonna();
    }

    public boolean isPoisonous() {
        return true;
    }

    public String getSpecies() {
        return "Belladonna";
    }

    @Override
    public Color getColor() {
        return Color.BLUE.darker();
    }

    public String printLetter()
    {
        return "b";
    }
}
