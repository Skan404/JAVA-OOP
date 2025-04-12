import java.awt.*;

public class Human extends Animal {
    private int m_specialAbilityRemaining;
    private int m_specialAbilityPauseRemaining;

    public Human(int strength, int specialAbilityRemaining, int specialAbilityPauseRemaining) {
        super(strength, 4);
        m_specialAbilityRemaining = specialAbilityRemaining;
        m_specialAbilityPauseRemaining = specialAbilityPauseRemaining;
    }

    public void action() {
        int directionR = getWorld().getHumanDirectionR();
        int directionC = getWorld().getHumanDirectionC();

        if (directionR == 0 && directionC == 0) {
            return;
        }

        Cell destination = getWorld().getCellOrNull(getPosition().getRow() + directionR, getPosition().getColumn() + directionC);
        //Cell destination = m_world.getCellOrNull(m_position.m_row + directionR, m_position.m_column + directionC);

        if (destination != null) {
            getWorld().moveTo(this, destination);
            //m_world.moveTo(this, destination);
        }

        if (m_specialAbilityRemaining != 0) {
            m_specialAbilityRemaining--;

            if (m_specialAbilityRemaining == 0) {
                m_specialAbilityPauseRemaining = 5;
            }
        } else if (m_specialAbilityPauseRemaining != 0) {
            m_specialAbilityPauseRemaining--;
        }
    }

    public CollisionResult collision(Organism o) {
        return super.collision(o);
    }

    public void print() {
        System.out.print("H");
    }

    public String getSpecies() {
        return "Human";
    }

    public boolean canActivateSpecialAbility() {
        if (m_specialAbilityPauseRemaining == 0 && m_specialAbilityRemaining == 0) {
            return true;
        }
        return false;
    }

    public void activateSpecialAbility() {
        m_specialAbilityRemaining = 5;
    }

    public boolean isAttackRepelled() {
        if (m_specialAbilityRemaining != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getspecialAbilityPauseRemaining() {
        return m_specialAbilityPauseRemaining;
    }

    public int getspecialAbilityRemaining() {
        return m_specialAbilityRemaining;
    }

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    public String printLetter()
    {
        return "H";
    }
}
