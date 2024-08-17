import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class LabyrinthPanel extends JPanel implements MouseListener {
    private char[][] labyrinth;
    private int tileSize = 20;
    private Main mainFrame;

    public LabyrinthPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(800, 600));
        addMouseListener(this);
    }

    public void setLabyrinth(char[][] labyrinth) {
        this.labyrinth = labyrinth;
        setPreferredSize(new Dimension(labyrinth[0].length * tileSize, labyrinth.length * tileSize));
        revalidate();
        repaint();
    }

    public char[][] getLabyrinth() {
        return labyrinth;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (labyrinth != null) {
            int numRows = labyrinth.length;
            int numCols = labyrinth[0].length;


            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    switch (labyrinth[i][j]) {
                        case 'X':
                            g.setColor(Color.BLACK);
                            break;
                        case '@':
                            g.setColor(Color.BLUE);
                            break;
                        case 'P':
                            g.setColor(Color.GREEN);
                            break;
                        case 'K':
                            g.setColor(Color.RED);
                            break;
                        default:
                            g.setColor(Color.WHITE);
                            break;
                    }
                    g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (labyrinth != null) {
            int col = e.getX() / tileSize;
            int row = e.getY() / tileSize;
            if (isValid(row, col, labyrinth.length, labyrinth[0].length)) {
                mainFrame.clearPath(); // Clear the path before changing the start or end point
                if (mainFrame.changingStart) {
                    if (mainFrame.startPoint != null) {
                        labyrinth[mainFrame.startPoint.x][mainFrame.startPoint.y] = 'X'; // Change old start to a wall
                    }
                    mainFrame.startPoint = new Point(row, col);
                    labyrinth[mainFrame.startPoint.x][mainFrame.startPoint.y] = 'P';
                    mainFrame.changingStart = false;
                } else if (mainFrame.changingEnd) {
                    if (mainFrame.endPoint != null) {
                        labyrinth[mainFrame.endPoint.x][mainFrame.endPoint.y] = 'X'; // Change old end to a wall
                    }
                    mainFrame.endPoint = new Point(row, col);
                    labyrinth[mainFrame.endPoint.x][mainFrame.endPoint.y] = 'K';
                    mainFrame.changingEnd = false;
                }
                repaint();
            }
        }
    }

    private boolean isValid(int x, int y, int numRows, int numCols) {
        return x >= 0 && x < numRows && y >= 0 && y < numCols;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}