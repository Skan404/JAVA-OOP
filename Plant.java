import java.util.ArrayList;
import java.util.Random;

public abstract class Plant extends Organism {
    private double probabilityOfCloning;
    private int nutritionValue;

    public Plant(int strength, double probabilityOfCloning, int nutritionValue) {
        super(strength, 0);
        this.probabilityOfCloning = probabilityOfCloning;
        this.nutritionValue = nutritionValue;
    }

    @Override
    public void action() {
        if (new Random().nextDouble() >= probabilityOfCloning) {
            return;
        }

        breed();
    }
    @Override
    public boolean isPlant() {
        return true;
    }
    @Override
    public boolean isAnimal() {
        return false;
    }

    public boolean isPoisonous() {
        return false;
    }

    public void breed() {
        ArrayList<Cell> neighbours = new ArrayList<>();
        m_world.getNeighbours(m_position, neighbours);
        int a = new Random().nextInt(neighbours.size());

        if (neighbours.get(a).getOrganism() == null) {
            m_world.addOrganism(clone(), neighbours.get(a).getRow(), neighbours.get(a).getColumn());
        }
    }

    public void setProbabilityOfCloning(double probabilityOfCloning) {
        this.probabilityOfCloning = probabilityOfCloning;
    }

    public void setNutritionValue(int nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public double getProbabilityOfCloning() {
        return probabilityOfCloning;
    }

    public int getNutritionValue() {
        return nutritionValue;
    }

    public String printLetter()
    {
        return "p";
    }

}
