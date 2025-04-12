import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WorldView extends JComponent {
    private String text;
    private World m_world;
    private static final int CELL_SIZE = 20;

    public WorldView(World w) {
        m_world = w;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    WorldView.this.mousePressed(e.getX(), e.getY());
                }
            }

        });
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, w, h);

        g2d.setColor(Color.red);
        g2d.drawRect(0, 0, w - 1, h - 1);

        for (int r = 0; r < m_world.getHeight(); r++)
        {
            for (int c = 0; c < m_world.getWidth(); c++)
            {
                Cell cell = m_world.getCellOrNull(r, c);
                Organism o = cell.getOrganism();
                if(o != null)
                {
                    g2d.setColor(o.getColor());
                    g2d.fillRect(CELL_SIZE * c, CELL_SIZE * r, CELL_SIZE, CELL_SIZE);


                    if (o.getColor() == Color.WHITE || o.getColor() == Color.YELLOW) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }

                    //g2d.drawString(o.printLetter(), CELL_SIZE * c + CELL_SIZE / 2, CELL_SIZE * r + CELL_SIZE / 2);
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = CELL_SIZE * c + (CELL_SIZE - fm.stringWidth(o.printLetter())) / 2;
                    int y = CELL_SIZE * r + (CELL_SIZE - fm.getHeight()) / 2 + fm.getAscent();
                    g2d.drawString(o.printLetter(), x, y);
                }
            }
        }
        /*
        //gridbox layout / gridlayout
        Legenda:
        Orange A - Antylopa
        Darker Blue b - Belladona
        Orange F - Fox
        Green g - Grass
        Brighter Red g - Guarana
        White h - Hogweed
        Magenta H - Human
        Yellow m - Milt
        White S - Sheep
        Green T - Turtle
        Gray W - Wolf
        */

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(m_world.getWidth()*CELL_SIZE, m_world.getHeight() * CELL_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(m_world.getWidth()*CELL_SIZE, m_world.getHeight() * CELL_SIZE);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(m_world.getWidth()*CELL_SIZE, m_world.getHeight() * CELL_SIZE);
    }

    private void mousePressed(int x, int y) {
        int r = y / CELL_SIZE;
        int c = x / CELL_SIZE;
        JOptionPane.showMessageDialog(this, "Kliknieto " + r + " " + c );
        if (r < m_world.getHeight() && c < m_world.getWidth() && m_world.getCellOrNull(r, c).getOrganism() == null)
        {
            final JPopupMenu popup = new JPopupMenu();

            JMenuItem menuItem = new JMenuItem("Antelope");
            menuItem.addActionListener(e -> addOrganism(new Antelope(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Belladona");
            menuItem.addActionListener(e -> addOrganism(new Belladonna(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Fox");
            menuItem.addActionListener(e -> addOrganism(new Fox(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Grass");
            menuItem.addActionListener(e -> addOrganism(new Grass(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Guarana");
            menuItem.addActionListener(e -> addOrganism(new Guarana(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Hogweed");
            menuItem.addActionListener(e -> addOrganism(new Hogweed(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Milt");
            menuItem.addActionListener(e -> addOrganism(new Milt(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Sheep");
            menuItem.addActionListener(e -> addOrganism(new Sheep(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Turtle");
            menuItem.addActionListener(e -> addOrganism(new Turtle(), r, c));
            popup.add(menuItem);

            menuItem = new JMenuItem("Wolf");
            menuItem.addActionListener(e -> addOrganism(new Wolf(), r, c));
            popup.add(menuItem);


            popup.add(menuItem);
            popup.show(this, x, y );

        }
    }

    private void addOrganism (Organism o, int row, int column)
    {
        m_world.addOrganism(o, row, column);
        repaint();
    }
}