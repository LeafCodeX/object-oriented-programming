package Project2;

import javax.swing.*;
import javax.swing.text.*;
import Project2.Extensions.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Program implements ActionListener, KeyListener {
    private World world;
    private JFrame menuFrame, gameFrame;
    private JPanel menuPanel, gamePanel;
    private JButton[] buttonMain, gameButtons;
    private BoardDesign boardDesign = null;
    private InformationDesign informationDesign = null;
    private static final int BUTTON_COUNT = 7, BUTTON_COUNT_GAME = 2;

    public World getWorld() {
        return world;
    }

    public BoardDesign getBoardDesign() {
        return boardDesign;
    }

    public InformationDesign getInformationDesign() {
        return informationDesign;
    }

    public void display() {
        gameFrame.setVisible(true);
    }

    public Program(String title) {
        gameFrame = new JFrame();
        gameFrame.setTitle("Project 2 - Virtual world design in JAVA");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);
        gameFrame.setLocationRelativeTo(null);
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 75)));
        buttonMain = new JButton[BUTTON_COUNT];
        buttonMain[0] = new JButton("Java Virtual World - Project 2");
        buttonMain[1] = new JButton("Name: Marcin Bajkowski");
        buttonMain[2] = new JButton("Index: 193696");
        buttonMain[3] = new JButton("Start New Game");
        buttonMain[3].addActionListener(this);
        buttonMain[4] = new JButton("Load Game");
        buttonMain[4].addActionListener(this);
        buttonMain[5] = new JButton("Exit");
        buttonMain[5].addActionListener(this);
        buttonMain[6] = new JButton("Gdańsk University of Technology, 2023, Computer Science");
        for (int i = 0; i < BUTTON_COUNT; i++) {
            if (i < 3 || i == 6) buttonMain[i].setForeground(new Color(135,206,250));
            else {
                buttonMain[i].setForeground(new Color(246, 142, 126));
                if(i == 3 || i == 5) menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
            }
            buttonMain[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            buttonMain[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonMain[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            buttonMain[i].setMaximumSize(new Dimension(600, 50));
            buttonMain[i].setMinimumSize(new Dimension(600, 50));
            menuPanel.add(buttonMain[i]);
        }
        gameFrame.addKeyListener(this);
        gameFrame.add(menuPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
        menuFrame = new JFrame(title);
        menuFrame.setSize(1124, 768);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gameButtons = new JButton[BUTTON_COUNT_GAME];
        gameButtons[0] = new JButton("Save");
        gameButtons[0].addActionListener(this);
        gameButtons[1] = new JButton("Exit");
        gameButtons[1].addActionListener(this);
        gameButtons[0].setBounds(739, 674, 182, 55);
        gameButtons[1].setBounds(935, 674, 182, 55);
        for (int i = 0; i < BUTTON_COUNT_GAME; i++) {
            gameButtons[i].setForeground(new Color(246, 142, 126));
            gameButtons[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            gameButtons[i].setBackground(Color.WHITE);
            gamePanel.add(gameButtons[i]);
        }
        menuFrame.addKeyListener(this);
        menuFrame.add(gamePanel);
        menuFrame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent buttonKey) {
        if (buttonKey.getSource() == buttonMain[3]) {
            JTextField sizeXField = new JTextField("20",1);
            JTextField sizeYField = new JTextField("20",1);
            Object[] inputFields = { "Enter the size of the world (X):", sizeXField, "Enter the size of the world (Y):", sizeYField};
            int result = JOptionPane.showOptionDialog(menuFrame, inputFields, "Enter World Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            menuFrame.setVisible(true);
            gameFrame.setVisible(false);
            Information.clearInformation();
            if (result == JOptionPane.OK_OPTION) {
                int sizeX = Integer.parseInt(sizeXField.getText());
                int sizeY = Integer.parseInt(sizeYField.getText());
                double probability = 0.175;
                world = new World(sizeX, sizeY, this);
                world.CreateWorld(probability);
                if (boardDesign != null) gamePanel.remove(boardDesign);
                if (informationDesign != null) gamePanel.remove(informationDesign);
                startGame();
            }
        }
        if (buttonKey.getSource() == buttonMain[4]) {
            Information.clearInformation();
            menuFrame.setVisible(true);
            gameFrame.setVisible(false);
            world = World.OpenSavedWorld("save");
            world.setProgram(this);
            boardDesign = new BoardDesign(world);
            informationDesign = new InformationDesign();
            if (boardDesign != null) gamePanel.remove(boardDesign);
            if (informationDesign != null) gamePanel.remove(informationDesign);
            startGame();
        }
        if (buttonKey.getSource() == gameButtons[0]) {
            world.SaveWorld("save");
            Information.AddInformation("World saved successfully!");
            informationDesign.refreshInformation();
            refreshWorld();
        }
        if (buttonKey.getSource() == gameButtons[1] || buttonKey.getSource() == buttonMain[5]) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyButton) {
        if (world != null && world.isPause()) {
            int key = keyButton.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
            } else if (world.getIsHumanAlive()) {
                if (key == KeyEvent.VK_UP) world.getHuman().setDirectionMovement(Organism.Direction.TURN_UP);
                else if (key == KeyEvent.VK_DOWN) world.getHuman().setDirectionMovement(Organism.Direction.TURN_DOWN);
                else if (key == KeyEvent.VK_LEFT) world.getHuman().setDirectionMovement(Organism.Direction.TURN_LEFT);
                else if (key == KeyEvent.VK_RIGHT) world.getHuman().setDirectionMovement(Organism.Direction.TURN_RIGHT);
                else if (key == KeyEvent.VK_SPACE) {
                    SpecialAbility specialAbility = world.getHuman().getSpecialAbility();
                    if (specialAbility.getCanMagicElixirBeActivated()) {
                        specialAbility.ActiveMagicElixir();
                        Information.AddInformation("The magic Elixir has been activated! Remaining: " + specialAbility.getMagicElixirDuration() + " round/s!");
                        world.getHuman().setStrength(world.getHuman().getStrength() + 6);
                    } else if (specialAbility.getIsMagicElixirActive()) {
                        Information.AddInformation("The Magic Elixir is active! Remaining: " + specialAbility.getMagicElixirDuration() + " round/s!");
                        informationDesign.refreshInformation();
                        return;
                    } else {
                        Information.AddInformation("The Magic Elixir can be re-activated in " + specialAbility.getCooldown() + " round/s!");
                        informationDesign.refreshInformation();
                        return;
                    }
                } else return;
            } else if (!world.getIsHumanAlive() && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_SPACE)) {
                Information.AddInformation("Game over! The human is dead!");
                informationDesign.refreshInformation();
                return;
            } else return;
            SpecialAbility tmpUmiejetnosc = world.getHuman().getSpecialAbility();
            if (tmpUmiejetnosc.getIsMagicElixirActive()) world.getHuman().setStrength(world.getHuman().getStrength() - 1);
            if (key != KeyEvent.VK_SPACE) {
                Information.clearInformation();
                world.setPause(false);
                world.MakeRound();
                refreshWorld();
                world.setPause(true);
            }
            else refreshWorld();
        }
    }

    @Override
    public void keyTyped(KeyEvent key) {}

    @Override
    public void keyReleased(KeyEvent key) {}

    private class BoardDesign extends JPanel {
        private final int sizeX, sizeY;
        private BoardField[][] boardFields;

        public BoardDesign(World world) {
            super();
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setBounds(gamePanel.getX(), gamePanel.getY(), gamePanel.getHeight() - 1, gamePanel.getHeight() - 1);
            this.sizeX = world.getSizeX();
            this.sizeY = world.getSizeY();
            boardFields = new BoardField[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    boardFields[i][j] = new BoardField(j, i);
                    boardFields[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent key) {
                            if (key.getSource() instanceof BoardField) {
                                BoardField tmpPlacement = (BoardField) key.getSource();
                            if (tmpPlacement.isEmpty == true) {
                                String[] listOfOrganisms = new String[]{"Sosnowsky Hogweed", "Guarana", "Dandelion", "Grass", "Deadly nightshade", "Antelope", "Fox", "Sheep", "Wolf", "Turtle"};
                                Organism.OrganismType[] listOrganismType = new Organism.OrganismType[]{Organism.OrganismType.SOSNOWSKY_HOGWEED, Organism.OrganismType.GUARANA, Organism.OrganismType.DANDELION, Organism.OrganismType.GRASS, Organism.OrganismType.DEADLY_NIGHTSHADE, Organism.OrganismType.ANTELOPE, Organism.OrganismType.FOX, Organism.OrganismType.SHEPP, Organism.OrganismType.WOLF, Organism.OrganismType.TURTLE};
                                JList<String> organismsJList = new JList<>(listOfOrganisms);
                                organismsJList.setVisibleRowCount(listOfOrganisms.length);
                                organismsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                                JScrollPane sp = new JScrollPane(organismsJList);
                                JFrame frame = new JFrame("List of organisms!");
                                frame.setBounds(tmpPlacement.getX() + menuFrame.getX(), tmpPlacement.getY() + menuFrame.getY(), 100, 300);
                                frame.add(sp);
                                frame.setVisible(true);
                                organismsJList.addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {
                                        Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism(listOrganismType[organismsJList.getSelectedIndex()], world, new Placement(tmpPlacement.getPosX(), tmpPlacement.getPosY()));
                                        world.AddOrganism(tmpOrganism);
                                        Information.AddInformation("Created new organism " + tmpOrganism.OrganismPlacement() + "!");
                                        refreshWorld();
                                        frame.dispose();
                                    }
                                    });
                                }
                            }
                        }
                    });
                }
            }
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(boardFields[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class BoardField extends JButton {
            private boolean isEmpty;
            private Color color;
            private final int posX;
            private final int posY;

            public BoardField(int X, int Y) {
                super();
                color = Color.WHITE;
                setBackground(color);
                setBorderPainted(false);
                isEmpty = true;
                posX = X;
                posY = Y;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }

            public void setColor(Color color) {
                this.color = color;
                setBackground(color);
            }

            public int getPosX() {
                return posX;
            }

            public int getPosY() {
                return posY;
            }
        }

        public void refreshBoard() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organism tmpOrganism = world.getBoard()[i][j];
                    if (tmpOrganism != null) {
                        boardFields[i][j].setEmpty(false);
                        boardFields[i][j].setEnabled(false);
                        boardFields[i][j].setColor(tmpOrganism.getColor());
                    } else {
                        boardFields[i][j].setEmpty(true);
                        boardFields[i][j].setEnabled(true);
                        boardFields[i][j].setColor(Color.WHITE);
                    }
                }
            }
        }
    }

    private class InformationDesign extends JPanel {
        private JTextPane textPane;

        public InformationDesign() {
            super();
            setBounds(boardDesign.getX() + boardDesign.getWidth(), gamePanel.getY() + 9, gamePanel.getWidth() - boardDesign.getWidth() - 6, gamePanel.getHeight() - 80);
            textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setFont(textPane.getFont());
            setLayout(new CardLayout());
            JScrollPane sp = new JScrollPane(textPane);
            add(sp);
        }

        public void refreshInformation() {
            StyledDocument doc = textPane.getStyledDocument();
            StyleContext styleContext = StyleContext.getDefaultStyleContext();
            AttributeSet SHAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(102, 102, 0));
            AttributeSet GAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(153,0,0));
            AttributeSet DAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(255, 214, 75));
            AttributeSet GRAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(0, 204, 0));
            AttributeSet DNSAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(51, 51, 255));
            AttributeSet AAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(153, 76, 0));
            AttributeSet HAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(204, 0,204));
            AttributeSet FAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(255, 128, 0));
            AttributeSet SAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(192,192,192));
            AttributeSet WAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(32,32,32));
            AttributeSet TAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(0, 153, 0));
            AttributeSet boldAttrSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Bold, true);
            try {
                doc.remove(0, doc.getLength());
                doc.insertString(doc.getLength(), "Character:", boldAttrSet);
                doc.insertString(doc.getLength(), " (Human)\n", HAttrSet);
                doc.insertString(doc.getLength(), "Passive:", boldAttrSet);
                doc.insertString(doc.getLength(), " (Guarana)", GAttrSet);
                doc.insertString(doc.getLength(), " (Dandelion)", DAttrSet);
                doc.insertString(doc.getLength(), " (Grass)", GRAttrSet);
                doc.insertString(doc.getLength(), " (Sheep)\n", SAttrSet);
                doc.insertString(doc.getLength(), "Aggressive:", boldAttrSet);
                doc.insertString(doc.getLength(), " (Antelope)", AAttrSet);
                doc.insertString(doc.getLength(), " (SosnowskyHogweed)", SHAttrSet);
                doc.insertString(doc.getLength(), " (Fox)", FAttrSet);
                doc.insertString(doc.getLength(), " (DeadlyNightshade)", DNSAttrSet);
                doc.insertString(doc.getLength(), " (Wolf)", WAttrSet);
                doc.insertString(doc.getLength(), " (Turtle)", TAttrSet);
                doc.insertString(doc.getLength(), "\nHuman strength: " + world.getHuman().getStrength() + "!\n" + Information.getText(), boldAttrSet);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void startGame() {
        boardDesign = new BoardDesign(world);
        gamePanel.add(boardDesign);
        informationDesign = new InformationDesign();
        gamePanel.add(informationDesign);
        refreshWorld();
    }

    public void refreshWorld() {
        boardDesign.refreshBoard();
        informationDesign.refreshInformation();
        SwingUtilities.updateComponentTreeUI(menuFrame);
        menuFrame.requestFocusInWindow();
    }
}   
