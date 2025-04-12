import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends Organism {
    public Animal(int strength, int initiative) {
        super(strength, initiative);
    }

    @Override
    public void action() {
        ArrayList<Cell> neighbours = new ArrayList<>();
        m_world.getNeighbours(m_position, neighbours);
        Random rand = new Random();
        int a = rand.nextInt(neighbours.size());
        m_world.moveTo(this, neighbours.get(a));
    }

    @Override
    public CollisionResult collision(Organism o) {
        if (o.isPlant()) {
            Plant p = (Plant)(o); // jak zrobić rzutowanie w java?
            if (p.isPoisonous()) {
                return CollisionResult.DIES_AND_KILLS;
            } else {
                m_strength += p . getNutritionValue();
                return CollisionResult.KILLS;
            }
        } else {
            Animal a = (Animal)(o); // rzutowanie
            if (a.getSpecies() != getSpecies()) {
                if (a.isAttackCountered(this)) {
                    return CollisionResult.RETURNS;
                } else if (a.isAttackRepelled(this)) {
                    return CollisionResult.REPEL;
                } else if (a.escapeAttack()) {
                    return CollisionResult.ESCAPED;
                } else if (m_strength < a.getStrength()) {
                    return CollisionResult.DIES;
                } else {
                    return CollisionResult.KILLS;
                }
            } else {
                //ArrayList<Cell> neigbours;
                //m_world.getNeighbours(m_position, neigbours);
                ArrayList<Cell> neighbours = new ArrayList<>();
                m_world.getNeighbours(m_position, neighbours);
                for (int i = 0; i < neighbours.size(); ) {
                    if (neighbours.get(i).getOrganism() != null)
                    {
                        neighbours.remove(i);
                    }
                else
                    {
                        i++;
                    }
                }
                if (neighbours.size() != 0) {
                    Random random = new Random();
                    int b = random.nextInt(neighbours.size());
                    m_world . addOrganism(clone(), neighbours.get(b).getRow(), neighbours.get(b).getColumn());
                    //m_world.addOrganism(clone(), neigbours[a].m_row, neigbours[a].m_column);

                }
                return CollisionResult.BREED;
            }
        }
    }

    @Override
    public void print() {
            System.out.print("a");
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public boolean isPlant() {
        return false;
    }

    public boolean isAttackCountered(Animal a) {
        return false;
    }

    public boolean isAttackRepelled(Animal a) {
        return false;
    }

    public boolean escapeAttack() {
        return false;
    }

    public String printLetter()
    {
        return "A";
    }
}