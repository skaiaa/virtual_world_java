package edu.pg.virtualworld;

import edu.pg.virtualworld.organisms.Human;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

public class MainDialog extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton saveButton;
    public static JButton[] labels;
    private JTextArea logTextArea;
    private JScrollPane logsScrollPane;
    private JPanel board;
    private JButton loadButton;
    private JButton newGameButton;
    private JTextField authorAnnaPrzybycien172126TextField;
    private MouseListener mouseListener;
    private static Drawer drawer;

    private void createLabels(int width, int height,World world) {
        labels = new JButton[height * width];
        for(int i=0;i<labels.length;i++){
            labels[i] = new JButton();
            labels[i].setOpaque(true);
            labels[i].setSize(50,50);
        }
        drawBoard(width,height,world,labels);
    }

    public MainDialog(int width, int height) {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Logger logger=new Logger(logTextArea,logsScrollPane);
        World world=new World(width,height,logger);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                logger.log("Wcisniete!!!!!!!!!");
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                logger.log("Wcisniete!!!!!!!!!");
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                        if(keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                            if(keyEvent.getKeyCode()==KeyEvent.VK_ESCAPE)  onCancel();
                            Human myHuman=world.getHuman();
                            if(myHuman!=null)myHuman.keyTyped(keyEvent);
                        }
                        return true;
                    }
                });
        createLabels(width, height,world);
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

        saveButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            logMessage("Game saved!");
        });
        loadButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            logMessage("Game loaded!");
        });
        newGameButton.addActionListener(actionEvent -> {//wywolanie save ze swiata
            logMessage("New game!");
        });
        Timer timer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                world.playRound();
                //drawBoard(height,width,labels);
                board.updateUI();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public static void drawBoard(int width, int height, World world, JButton[] labels) {
        int i = 0;

        for (int k = 0; k < height; k++) {
            for (int j = 0; j < width; j++) {
                for (int l = 0; l < world.organisms.size(); l++) {
                    if (world.organisms.elementAt(l).getLocation().equals(new Location(k, j))) {
                        labels[i].setText(world.organisms.elementAt(l).getSymbol() + "" + l);
                        labels[i].setBackground(world.organisms.elementAt(l).getColor());
                        break;
                    } else {
                        labels[i].setText("" + i);
                        labels[i].setBackground(Color.GRAY);
                    }
                }
                i++;
            }
        }
    }
    public static void main(String[] args) {
        drawer=new Drawer();
        int height=Integer.parseInt(JOptionPane.showInputDialog("Height: "));
        int width=Integer.parseInt(JOptionPane.showInputDialog("Width: "));

        MainDialog dialog = new MainDialog(width,height);
        dialog.requestFocusInWindow();
        dialog.pack();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.requestFocusInWindow();
            }
        });
        dialog.setVisible(true);
        //JOptionPane.showMessageDialog(dialog, "Eggs are not supposed to be green.");
        //System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void logMessage(String message) {
        logTextArea.append(message+"\n");
        logsScrollPane.getVerticalScrollBar().setValue(logsScrollPane.getVerticalScrollBar().getMaximum());
    }
}
