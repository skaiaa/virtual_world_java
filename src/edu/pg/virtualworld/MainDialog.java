package edu.pg.virtualworld;

import edu.pg.virtualworld.buttons.ButtonGenerator;
import edu.pg.virtualworld.buttons.HexButtonGenerator;
import edu.pg.virtualworld.buttons.SquareButtonGenerator;
import edu.pg.virtualworld.organisms.Human;
import edu.pg.virtualworld.organisms.Organism;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.Vector;

public class MainDialog extends JFrame {
    private final Component modalToComponent = new Component() {
    };
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton saveButton;
    private JButton[] labels;
    private JTextArea logTextArea;
    private JScrollPane logsScrollPane;
    private JPanel board;
    private JButton loadButton;
    private JButton newGameButton;
    private JTextField legend; //used by form
    private JButton pauseButton;
    private Timer timer;
    private JFileChooser fileChooser = new JFileChooser();

    private void createLabels(int width, int height, World world, ButtonGenerator buttonGenerator) {
        labels = new JButton[height * width];
        for (int i = 0; i < labels.length; i++) {
            int idx = i;
            labels[i] = buttonGenerator.create();
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    Location l = new Location(idx / width, idx % width);
                    if (world.whoIsThere(l) == null) {
                        timer.stop();
                        popUp(world, l);
                        drawBoard(width, height, world, labels);
                        timer.start();
                    } else logMessage("Field taken");
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                }
            });
            labels[i].setOpaque(true);
            labels[i].setSize(50, 50);
        }
        drawBoard(width, height, world, labels);
    }

    private void drawBoard(int width, int height, World world, JButton[] labels) {
        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int j = 0; j < width; j++) {
                for (int l = 0; l < world.organisms.size(); l++) {
                    if (world.organisms.elementAt(l).getLocation().equals(new Location(k, j))) {
                        labels[i].setText(world.organisms.elementAt(l).getSymbol() + "");
                        labels[i].setBackground(world.organisms.elementAt(l).getColor());
                        break;
                    } else {
                        labels[i].setText("");
                        labels[i].setBackground(Color.GRAY);
                    }
                }
                i++;
            }
        }
        board.updateUI();
    }

    private void popUp(World world, Location location) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Please make a selection:"));
        DefaultComboBoxModel box = new DefaultComboBoxModel<String>();
        String s = "WATSFgusbd";
        Vector<Organism> org = new Vector<>();
        for (int i = 0; i < s.length(); i++) {
            org.add(OrganismGenerator.getOrganism(s.charAt(i)));
            box.addElement(org.elementAt(i).getName());
        }
        JComboBox comboBox = new JComboBox(box);
        panel.add(comboBox);
        int result = JOptionPane.showConfirmDialog(null, panel, "Organism:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                org.elementAt(comboBox.getSelectedIndex()).setLocation(location);
                world.organisms.add(org.elementAt(comboBox.getSelectedIndex()));
                Collections.sort(world.organisms);
                drawBoard(world.getWidth(), world.getHeight(), world, labels);
                break;
        }
    }

    private MainDialog(int width, int height, ButtonGenerator bg) {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Logger logger = new Logger(logTextArea, logsScrollPane);
        World world = new World(width, height, logger);
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(keyEvent -> {
                            if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) onCancel();
                                Human myHuman = world.getHuman();
                                if (myHuman != null) myHuman.keyTyped(keyEvent);
                            }
                            return false;
                        }
                );
        createLabels(width, height, world, bg);
        board.setLayout(new GridLayout(width, height));
        for (int i = 0; i < width * height; i++) {
            board.add(labels[i]);
        }
        board.updateUI();
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        ((DefaultCaret) logTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logTextArea.updateUI();

        saveButton.addActionListener(actionEvent -> {
            timer.stop();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();//  save to file
                File out = new File(file.getAbsolutePath());
                ObjectOutputStream output = null;
                try {
                    world.saveToFile(new FileOutputStream(out));
                } catch (IOException e) {
                    logger.log("Error saving file! " + e.getMessage());
                }
            }
            logMessage("Game saved!");
            timer.start();
        });
        loadButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            timer.stop();
            if (fileChooser.showOpenDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    world.loadFromFile(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    logger.log("Cannot open file!");
                }
                // load from file
            }
            logMessage("Game loaded!");
            timer.start();
        });
        newGameButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            world.createNewWorld();
            logTextArea.setText("");
            logMessage("New game!");
        });
        pauseButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            if (timer.isRunning()) {
                timer.stop();
                pauseButton.setText("Resume");
                logMessage("Game paused!");
            } else {
                timer.start();
                pauseButton.setText("Pause");
                logMessage("Game resumed!");
            }

        });
        timer = new Timer(1500, actionEvent -> {
            drawBoard(world.getWidth(), world.getHeight(), world, labels);
            world.playRound();
            drawBoard(world.getWidth(), world.getHeight(), world, labels);
            board.updateUI();
        }
        );
        timer.setRepeats(true);
        timer.start();
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        int height = Integer.parseInt(JOptionPane.showInputDialog("Height: "));
        int width = Integer.parseInt(JOptionPane.showInputDialog("Width: "));
        String whichButton = JOptionPane.showInputDialog("What kind of button (hex or square, default square): ");
        ButtonGenerator bg;

        if (whichButton.equals("hex")) bg = new HexButtonGenerator();
        else bg = new SquareButtonGenerator();

        MainDialog dialog = new MainDialog(width, height, bg);
        dialog.requestFocusInWindow();
        dialog.pack();
        SwingUtilities.invokeLater(() -> dialog.requestFocusInWindow());
        dialog.setVisible(true);
    }

    private void createUIComponents() { //do forma
    }

    private void logMessage(String message) {
        logTextArea.append(message + "\n");
        logsScrollPane.getVerticalScrollBar().setValue(logsScrollPane.getVerticalScrollBar().getMaximum());
    }
}
