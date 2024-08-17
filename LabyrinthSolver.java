import java.awt.*;
import java.util.*;
import javax.swing.*;



public class LabyrinthSolver {

    public static void findShortestPath(Main mainFrame) {
        if (mainFrame.labyrinthPanel.getLabyrinth() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Najpierw musisz wczytać labirynt!");
        } else {
            char[][] labyrinth = mainFrame.labyrinthPanel.getLabyrinth();
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            Queue<Point> queue = new LinkedList<>();
            boolean[][] visited = new boolean[labyrinth.length][labyrinth[0].length];
            Point[][] prev = new Point[labyrinth.length][labyrinth[0].length];

            queue.offer(mainFrame.startPoint);
            visited[mainFrame.startPoint.x][mainFrame.startPoint.y] = true;


            while (!queue.isEmpty()) {
                Point current = queue.poll();
                for (int[] dir : directions) {
                    int newX = current.x + dir[0];
                    int newY = current.y + dir[1];
                    if (isValid(newX, newY, labyrinth.length, labyrinth[0].length) && !visited[newX][newY] && labyrinth[newX][newY] != 'X') {
                        queue.offer(new Point(newX, newY));
                        visited[newX][newY] = true;
                        prev[newX][newY] = current;
                    }
                }
            }

            Point current = mainFrame.endPoint;
            while (!current.equals(mainFrame.startPoint)) {
                if (labyrinth[current.x][current.y] != 'K') {
                    labyrinth[current.x][current.y] = '@';
                }
                current = prev[current.x][current.y];
            }

            mainFrame.labyrinthPanel.setLabyrinth(labyrinth);
        }
    }

    private static boolean isValid(int x, int y, int numRows, int numCols) {
        return x >= 0 && x < numRows && y >= 0 && y < numCols;
    }
}