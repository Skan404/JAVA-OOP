import java.awt.*;

public abstract class Organism implements Cloneable {

    protected World m_world;
    protected Cell m_position;
    protected int m_strength;
    protected int m_initiative;
    protected int m_birthday;
    protected boolean m_destroyed;

    public Organism(int strength, int initiative) {
        m_strength = strength;
        m_initiative = initiative;
        m_destroyed = false;
    }

    public int getStrength() {

        return m_strength;
    }

    public int getInitiative() {
        return m_initiative;
    }

    public Organism clone() {
        return null;
    }
    public abstract void action();

    public CollisionResult collision(Organism o) {
        return null;
    }

    public void print() {
        System.out.print("o");
    }

    public int getAge() {
        return m_world.getTurn() - m_birthday;
    }

    public boolean isAttackCountered(Organism o) {
        return false;
    }

    public World getWorld() {
        return m_world;
    }

    public void setWorld(World world) {
        m_world = world;
    }

    public Cell getPosition() {
        return m_position;
    }

    public void setPosition(Cell position) {
        m_position = position;
    }

    public abstract boolean isPlant();

    public abstract boolean isAnimal();

    public abstract String getSpecies();

    public abstract Color getColor();

    public abstract String printLetter();

}
