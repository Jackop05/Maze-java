import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Main extends JFrame implements ActionListener {

    JFileChooser fileChooser;
    LabyrinthPanel labyrinthPanel;
    JScrollPane scrollPane;
    JButton shortestPathButton;
    JButton changeStartButton;
    JButton changeEndButton;
    Point startPoint;
    Point endPoint;
    boolean changingStart = false;
    boolean changingEnd = false;

    public Main() {
        setTitle("Labirynt");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        labyrinthPanel = new LabyrinthPanel(this);
        scrollPane = new JScrollPane(labyrinthPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        createMenu();
        createToolbar();

        fileChooser = new JFileChooser();

        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        fileMenu.setFont(new Font("Arial", Font.BOLD, 16));
        fileMenu.setIcon(new ImageIcon("menu-icon.png"));
        fileMenu.setToolTipText("Click to see options");
        fileMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JMenuItem openTextMenuItem = new JMenuItem("Wczytaj z pliku tekstowego");
        openTextMenuItem.addActionListener(this);
        fileMenu.add(openTextMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Zapisz");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Zakończ program");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar("Panel narzędziowy");

        shortestPathButton = new JButton("Znajdź najkrótszą ścieżkę");
        shortestPathButton.addActionListener(this);
        toolBar.add(shortestPathButton);

        changeStartButton = new JButton("Zmień punkt startu");
        changeStartButton.addActionListener(this);
        toolBar.add(changeStartButton);

        changeEndButton = new JButton("Zmień punkt końcowy");
        changeEndButton.addActionListener(this);
        toolBar.add(changeEndButton);

        getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Wczytaj z pliku tekstowego")) {
            FileOperations.loadLabyrinthFromFile(this);
        } else if (e.getActionCommand().equals("Zapisz")) {
            FileOperations.saveLabyrinth(this);
        } else if (e.getActionCommand().equals("Znajdź najkrótszą ścieżkę")) {
            LabyrinthSolver.findShortestPath(this);
        } else if (e.getActionCommand().equals("Zmień punkt startu")) {
            changingStart = true;
            changingEnd = false;
        } else if (e.getActionCommand().equals("Zmień punkt końcowy")) {
            changingStart = false;
            changingEnd = true;
        } else if (e.getActionCommand().equals("Zakończ program")) {
            System.exit(0);
        }
    }

    public void clearPath() {
        if (labyrinthPanel.getLabyrinth() != null) {
            char[][] labyrinth = labyrinthPanel.getLabyrinth();
            for (int i = 0; i < labyrinth.length; i++) {
                for (int j = 0; j < labyrinth[i].length; j++) {
                    if (labyrinth[i][j] == '@') {
                        labyrinth[i][j] = ' ';
                    }
                }
            }
            labyrinthPanel.setLabyrinth(labyrinth);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}