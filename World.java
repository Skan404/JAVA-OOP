
import jdk.dynalink.beans.StaticClass;
import java.awt.desktop.SystemSleepEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class World {

    int m_width, m_height;
    int m_turn = 0;
    Human m_human;
    int m_humanDirectionC = 0;
    int m_humanDirectionR = 0;
    ArrayList<Organism> m_organisms = new ArrayList<>();
    Cell[][] m_cells;
    CollisionListener m_collisionListener;

    public World(int width, int height, int humanR, int humanC)
    {
        start(width, height, humanR, humanC, 5, 0, 0);

    }

    public void addOrganism(Organism o, int row, int column)
    {
        Cell position = m_cells[row][column];
        if (position.getOrganism() != null)
        {
            throw new RuntimeException();
        }
        m_organisms.add(o);
        m_cells[row][column].m_organisms.add(o);
        o.m_position = m_cells[row][column];
        o.m_world = this;
        o.m_birthday = m_turn;
    }

    public void printWorld()
    {
        System.out.println();
        for (int r = 0; r < m_height; r++)
        {
            for (int c = 0; c < m_width; c++)
            {
                Cell cell = m_cells[r][c];
                if (cell.m_organisms.size() == 0)
                {
                    System.out.print(".");

                }
                else if (cell.m_organisms.size() == 1)
                {
                    cell.m_organisms.get(0).print();
                }
                else
                {
                    System.out.print(cell.m_organisms.size());
                }
            }
            System.out.println();

        }
        System.out.println();
    }

    public void getNeighbours(Cell position, ArrayList<Cell> neighbours)
    {
        int[][] offsets = { { -1, 0} , { 1,0 }, {0,-1 }, {0,1} };

        for (int i = 0; i < 4; i++)
        {
            int r = position.getRow() + offsets[i][0];
            int c = position.getColumn() + offsets[i][1];
            Cell neighbour = getCellOrNull(r, c);
            if (neighbour != null)
            {
                neighbours.add(neighbour);
            }
        }
    }

    public Cell getCellOrNull(int row, int column)
    {
        if (row >= m_height || row < 0)
        {
            return null;
        }
        else if (column >= m_width || column < 0)
        {
            return null;
        }

        return m_cells[row][column];
    }

    public void moveTo(Organism o, Cell position)
    {
        Organism occupant = position.getOrganism();
        if (occupant == null)
        {
            moveToEmptyCell(o, position);
        }
        else
        {
            System.out.println("Koliza pomiedzy " + o.getSpecies() + " " + occupant.getSpecies());
            CollisionResult result = o.collision(occupant);
            m_collisionListener.onCollision(o, occupant, result);
            switch (o.collision(occupant))
            {
                case REPEL:
                {
                    System.out.println(o.getSpecies() + " odstrasza " + occupant.getSpecies());
                    ArrayList<Cell> emptyNeighbours = new ArrayList<>();
                    getNeighboursWithoutOrganism(occupant.m_position, emptyNeighbours);

                    if (emptyNeighbours.size() == 0)
                    {
                        Random random = new Random();
                        int a = random.nextInt(emptyNeighbours.size());
                        moveTo(o, emptyNeighbours.get(a));
                    }
                    break;
                }
                case KILLS:
                    kill(occupant);
                    moveToEmptyCell(o, position);
                    System.out.println(o.getSpecies() + " zabija " + occupant.getSpecies());
                    break;
                case DIES:
                    kill(o);
                    System.out.println(o.getSpecies() + " zabija " + occupant.getSpecies());
                    break;
                case DIES_AND_KILLS:
                    kill(o);
                    kill(occupant);
                    System.out.println(o.getSpecies() + " zabija " + occupant.getSpecies() + " i sam ginie ");
                    break;
                case RETURNS:
                    System.out.println(o.getSpecies() + " powraca na swoje miejsce");
                    break;
                case ESCAPED:
                    System.out.println(o.getSpecies() + " ucieka ");
                    break;
                case BREED:
                    System.out.println(o.getSpecies() + " rozmnozyl sie ");
                    break;
            }
        }
    }

    public void action()
    {
        ArrayList<Organism> o = new ArrayList<>();
        for (int i = 0; i<m_organisms.size(); i++)
        {
            o.add(m_organisms.get(i));
        }

        m_turn++;

        class OrganismComparator implements Comparator<Organism> {
            @Override
            public int compare(Organism o1, Organism o2) {

                if (o1.getInitiative() > o2.getInitiative()) {
                    return -1;
                }
                if (o1.getInitiative() < o2.getInitiative()) {
                    return 1;
                }

                int o1age = o1.getAge();
                int o2age = o2.getAge();

                if (o1age > o2age)
                    return -1;
                else if (o1age < o2age)
                    return 1;
                else
                    return 0;
            }

        }

        o.sort(new OrganismComparator());

        for (int i = 0; i< o.size(); i++)
        {
            if(o.get(i).m_destroyed == true )
            {
                continue;
            }

            o.get(i).action();
        }

        for (int i = 0; i < m_organisms.size();)
        {
            if (m_organisms.get(i).m_destroyed == true)
            {
                if (m_organisms.get(i) == m_human) // umieranie czlowieka - konczenie gry
                {
                    m_human = null;
                }
                m_organisms.remove(i);
            }
		    else
            {
                i++;
            }
        }

    }

    public Human getHuman()
    {
        return m_human;
    }

    public int getHumanDirectionR()
    {
        return m_humanDirectionR;
    }

    public int getHumanDirectionC()
    {
        return m_humanDirectionC;
    }

    public void setHumanDirection(int directionR, int directionC)
    {
        m_humanDirectionR = directionR;
        m_humanDirectionC = directionC;
    }

    public int getTurn()
    {
        return m_turn;
    }

    public void kill(Organism o)
    {
        System.out.println(o.getSpecies() + " zostaje zabity na pozycji row: " + o.getPosition().getRow() + " column: " + o.getPosition().getColumn());

        o.m_destroyed = true;
        for (int i = 0; i < o.m_position.m_organisms.size(); i++)
        {
            if (o.m_position.m_organisms.get(i) == o)
            {
                o.m_position.m_organisms.remove(i);
                o.m_position = null;
                break;
            }
        }
    }

    public void moveToEmptyCell(Organism o, Cell position)
    {
        o.m_position.m_organisms.remove(o);
        position.m_organisms.add(o);
        o.m_position = position;
    }

    public void getNeighboursWithoutOrganism(Cell position, ArrayList<Cell> neighbours)
    {
        int[][] offsets = { { -1, 0} , { 1,0 }, {0,-1 }, {0,1} };

        for (int i = 0; i < 4; i++)
        {
            int r = position.getRow() + offsets[i][0];
            int c = position.getColumn() + offsets[i][1];

            Cell neighbour = getCellOrNull(r, c);
            if (neighbour != null && neighbour.getOrganism() == null)
            {
                neighbours.add(neighbour);
            }
        }
    }

    public void printInformations()
    {
        System.out.println("Szymon Kaniewski, 193423");

        if (getHuman().getspecialAbilityRemaining() != 0)
        {
            System.out.println("Pozostala dlugosc spejcalnej umiejetnosci: " + getHuman().getspecialAbilityRemaining() + " tur ");
            System.out.println("Sila czlowieka: " + getHuman().getStrength());
        }
	else if (getHuman().getspecialAbilityPauseRemaining() != 0)
        {
            System.out.println("Pozostala pauza specjalnej umiejetnosci: " + getHuman().getspecialAbilityPauseRemaining() + " tur ");
            System.out.println("Sila czlowieka: " + getHuman().getStrength());
        }
	else
        {
            System.out.println("Specjalna umiejetnosc jest gotowa ");
            System.out.println("Sila czlowieka: " + getHuman().getStrength());
        }
    }

    public boolean isGameFinished()
    {
        if (m_human != null)
        {
            return false;
        }
        return true;
    }

    public void start(int width, int height, int humanR, int humanC, int strength, int specialAbilityRemaning, int specialAbilityPauseRemaning)
    {
        m_width = width;
        m_height = height;
        m_human = new Human (strength, specialAbilityRemaning, specialAbilityPauseRemaning);
        m_turn = 0;
        m_organisms = new ArrayList<>();


        m_cells = new Cell[m_height][m_width];
        for (int r = 0; r < m_height; r++)
        {
            //m_cells[r].resize(m_width);

            for (int c = 0; c < m_width; c++)
            {
                m_cells[r][c] = new Cell();
                m_cells[r][c].setColumn(c);
                m_cells[r][c].setRow(r);


            }
        }
        addOrganism(m_human, humanR, humanC);

    }


    public void clear()
    {
        m_organisms = null;
        m_cells = null;

    }


     public void saveState(String path) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(path);

        file.print(m_height + " ");
        file.print(m_width + " ");
        file.println(m_turn);
        file.print(m_human.getPosition().getRow() + " " + m_human.getPosition().getColumn() + " ");
        file.print(m_human.getStrength() + " ");
        file.println(m_human.getspecialAbilityRemaining() + " " + m_human.getspecialAbilityPauseRemaining());
        file.println(m_organisms.size() - 1);

        for (int i = 0; i < m_organisms.size(); i++)
        {
             Organism o = m_organisms.get(i);
             if (o != m_human)
             {
                 file.println(o.getSpecies() + " " + o.getPosition().getRow() + " " + o.getPosition().getColumn() + " " + o.m_strength + " " + o.m_birthday);
             }
        }
        file.close();

    }



    public void loadState(String path) throws FileNotFoundException {

        Scanner file = new Scanner(new File(path));

        clear();
        int height, width, turn, humanRow, humanColumn, humanStrength, humanSpecialAbilityRamining, humanSpecialAbilityPauseRemaining;
        height = file.nextInt();
        width = file.nextInt();
        turn = file.nextInt();
        humanRow = file.nextInt();
        humanColumn = file.nextInt();
        humanStrength = file.nextInt();
        humanSpecialAbilityRamining = file.nextInt();
        humanSpecialAbilityPauseRemaining = file.nextInt();

        start(width, height, humanRow, humanColumn, humanStrength, humanSpecialAbilityRamining, humanSpecialAbilityPauseRemaining);
        m_turn = turn;
        int organismsSize, oRow, oColumn, oStrength, oBirthday;
        organismsSize = file.nextInt();

        for (int i = 0; i<organismsSize; i++) {
            String species;
            species = file.next();
            oRow = file.nextInt();
            oColumn = file.nextInt();
            oStrength = file.nextInt();
            oBirthday = file.nextInt();
            Organism o = null;

            if (species.equals("Antelope"))
            {
                o = new Antelope();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Belladonna"))
            {
                o = new Belladonna();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Fox"))
            {
                o = new Fox();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Grass"))
            {
                o = new Grass();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Guarana"))
            {
                o = new Guarana();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Hogweed"))
            {
                o = new Hogweed();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Milt"))
            {
                o = new Milt();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Sheep"))
            {
                o = new Sheep();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Turtle"))
            {
                o = new Turtle();
                addOrganism(o, oRow, oColumn);
            }
            else if (species.equals("Wolf"))
            {
                o = new Wolf();
                addOrganism(o, oRow, oColumn);
            }
            o.m_strength = oStrength;
            o.m_birthday = oBirthday;


        }
    }

    public int getWidth()
    {
        return m_width;
    }

    public int getHeight()
    {
        return m_height;
    }

    public void setCollisionListener(CollisionListener collisionListener){
        m_collisionListener = collisionListener;
    };

}
