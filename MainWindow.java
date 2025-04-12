import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame implements  CollisionListener{

    private World m_world;
    private WorldView m_worldView;
    private JLabel m_humanDirection;
    private JLabel info;
    private JLabel specialAbilityLabel;
    private JPanel labelsPanel;
    private JLabel m_collisionLabel;

    public MainWindow(World world){
        m_world = world;
        m_world.setCollisionListener(this);
        getContentPane().setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));
        JButton nextTurn = new JButton("Next turn");
        nextTurn.addActionListener(e -> nextTurnClicked());
        nextTurn.setFocusable(false);

        JButton save = new JButton("Save");
        save.addActionListener(e -> saveClicked());
        save.setFocusable(false);

        JButton load = new JButton("Load");
        load.addActionListener(e -> loadClicked());
        load.setFocusable(false);

        buttonsPanel.add(nextTurn);
        buttonsPanel.add(save);
        buttonsPanel.add(load);

        getContentPane().add(buttonsPanel, BorderLayout.NORTH);

        specialAbilityLabel = new JLabel();

        labelsPanel = new JPanel(new GridLayout(6, 1)); // Increase the grid layout to accommodate the new label
        m_humanDirection = new JLabel("Kierunek: nie wybrano");
        info = new JLabel("Szymon Kaniewski, 193423");
        specialAbilityLabel = new JLabel("Specjalna umiejętność jest gotowa");
        m_collisionLabel = new JLabel();

        labelsPanel.add(m_humanDirection);
        labelsPanel.add(info);
        labelsPanel.add(specialAbilityLabel);
        labelsPanel.add(m_collisionLabel);

        getContentPane().add(labelsPanel, BorderLayout.CENTER);

        m_worldView = new WorldView(world);
        m_worldView.setFocusable(false);

        getContentPane().add(m_worldView, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        m_world.setHumanDirection(0, -1);
                        m_humanDirection.setText("Direction: LEFT");
                        break;
                    case KeyEvent.VK_RIGHT:
                        m_world.setHumanDirection(0, 1);
                        m_humanDirection.setText("Direction: RIGHT");
                        break;
                    case KeyEvent.VK_UP:
                        m_world.setHumanDirection(-1, 0);
                        m_humanDirection.setText("Direction: UPWARD");
                        break;
                    case KeyEvent.VK_DOWN:
                        m_world.setHumanDirection(1, 0);
                        m_humanDirection.setText("Direction: DOWN");
                        break;
                    case KeyEvent.VK_Y:
                        if (m_world.getHuman().canActivateSpecialAbility())
                        {
                            m_world.getHuman().activateSpecialAbility();
                        }
                    case KeyEvent.VK_X:
                        m_world.setHumanDirection(0, 0);
                        m_humanDirection.setText("Direction: not chosen");
                        break;
                    default:
                        super.keyPressed(e);
                        break;
                }
            }
        });
    }

    private void nextTurnClicked(){
        m_world.action();
        updateSpecialAbilityLabel();
        m_worldView.repaint();
    }

    private void saveClicked(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                m_world.saveState(fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Zapisano stan gry");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Nie udało się zapisać");
            }
        }
    }

    private void loadClicked(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                m_world.loadState(fileToLoad.getAbsolutePath());
                m_worldView.repaint();
                m_world.printWorld();
                JOptionPane.showMessageDialog(this, "Wczytano stan gry");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Nie udało się wczytać");
            }
        }
    }

    private void updateSpecialAbilityLabel() {
        Human human = m_world.getHuman();
        int specialAbilityRemaining = human.getspecialAbilityRemaining();
        int specialAbilityPauseRemaining = human.getspecialAbilityPauseRemaining();

        if (specialAbilityRemaining != 0) {
            specialAbilityLabel.setText("Pozostała długość specjalnej umiejętności: " + specialAbilityRemaining + " tur");
        } else if (specialAbilityPauseRemaining != 0) {
            specialAbilityLabel.setText("Pozostała pauza specjalnej umiejętności: " + specialAbilityPauseRemaining + " tur");
        } else {
            specialAbilityLabel.setText("Specjalna umiejętność jest gotowa");
        }


        labelsPanel.revalidate();
        labelsPanel.repaint();
    }

    @Override
    public void onCollision(Organism attacker, Organism defender, CollisionResult result) {
        String message = "";
        switch (result)
        {
            case REPEL:
            {
                message = attacker.getSpecies() + " odstrasza " + defender.getSpecies();
                break;
            }
            case KILLS:
                message = attacker.getSpecies() + " zabija " + defender.getSpecies();
                break;
            case DIES:
                message = defender.getSpecies() + " zabija " + attacker.getSpecies();
                break;
            case DIES_AND_KILLS:
                message = defender.getSpecies() + " zabija " + attacker.getSpecies() + " i sam ginie ";
                break;
            case RETURNS:
                message = attacker.getSpecies() + " powraca na swoje miejsce";
                break;
            case ESCAPED:
                message = attacker.getSpecies() + " ucieka";
                break;
            case BREED:
                message = attacker.getSpecies() + " rozmnozyl sie ";
                break;
        }
        JOptionPane.showMessageDialog(this, message);
    }
}
