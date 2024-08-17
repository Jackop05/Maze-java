import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;



public class FileOperations {

    public static void loadLabyrinthFromFile(Main mainFrame) {
        int returnValue = mainFrame.fileChooser.showOpenDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = mainFrame.fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                StringBuilder contentBuilder = new StringBuilder();
                int rows = 0;
                int cols = 0;
                boolean hasStart = false;
                boolean hasEnd = false;


                while ((line = reader.readLine()) != null) {
                    if (rows > 0 && line.length() != cols) {
                        JOptionPane.showMessageDialog(mainFrame, "Inconsistent row lengths in labyrinth file!", "Error", JOptionPane.ERROR_MESSAGE);
                        reader.close();
                        return;
                    }
                    contentBuilder.append(line).append("\n");
                    rows++;
                    cols = Math.max(cols, line.length());

                    if (line.contains("P")) hasStart = true;
                    if (line.contains("K")) hasEnd = true;
                }
                reader.close();


                if (rows == 0 || cols == 0 || !hasStart || !hasEnd) {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid labyrinth file!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                char[][] labyrinth = new char[rows][cols];
                String[] lines = contentBuilder.toString().split("\n");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < lines[i].length(); j++) {
                        if (lines[i].charAt(j) == 'P') {
                            mainFrame.startPoint = new Point(i, j);
                        }
                        if (lines[i].charAt(j) == 'K') {
                            mainFrame.endPoint = new Point(i, j);
                        }
                        labyrinth[i][j] = lines[i].charAt(j);
                    }
                }
                mainFrame.labyrinthPanel.setLabyrinth(labyrinth);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void saveLabyrinth(Main mainFrame) {
        int returnValue = mainFrame.fileChooser.showSaveDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = mainFrame.fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                char[][] labyrinth = mainFrame.labyrinthPanel.getLabyrinth();
                for (char[] row : labyrinth) {
                    writer.write(row);
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(mainFrame, "Labyrinth saved successfully!");

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error saving labyrinth!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveLabyrinthAsPNG(Main mainFrame) {
        int returnValue = mainFrame.fileChooser.showSaveDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = mainFrame.fileChooser.getSelectedFile();
            try {
                char[][] labyrinth = mainFrame.labyrinthPanel.getLabyrinth();
                int cellSize = 20;
                int width = labyrinth[0].length * cellSize;
                int height = labyrinth.length * cellSize;

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();


                for (int i = 0; i < labyrinth.length; i++) {
                    for (int j = 0; j < labyrinth[i].length; j++) {
                        switch (labyrinth[i][j]) {
                            case '1':
                                g2d.setColor(Color.BLACK);
                                break;
                            case '0':
                                g2d.setColor(Color.WHITE);
                                break;
                            case 'P':
                                g2d.setColor(Color.GREEN);
                                break;
                            case 'K':
                                g2d.setColor(Color.RED);
                                break;
                            case '@':
                                g2d.setColor(Color.BLUE);
                                break;
                            default:
                                g2d.setColor(Color.WHITE);
                                break;
                        }
                        g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
                g2d.setColor(Color.GRAY);

                for (int i = 0; i <= labyrinth.length; i++) {
                    g2d.drawLine(0, i * cellSize, width, i * cellSize);
                }
                for (int j = 0; j <= labyrinth[0].length; j++) {
                    g2d.drawLine(j * cellSize, 0, j * cellSize, height);
                }
                g2d.dispose();
                ImageIO.write(image, "png", selectedFile);
                JOptionPane.showMessageDialog(mainFrame, "Labyrinth saved as PNG successfully!");

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error saving labyrinth as PNG!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}