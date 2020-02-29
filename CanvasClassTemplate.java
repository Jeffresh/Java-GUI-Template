import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * CanvasClassTemplate.java
 * Purpose: generic Class that represent the canvas where you will show the stuff.
 * @author: Jeffrey Pallarés Núñez.
 * @version: 1.0 23/07/19
 */

class CanvasClassTemplate extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Object of the class that Needs Visualization (ONV)  */
    public static TaskTemplate task;
    public static BufferedImage image_ref;
    public static int xMax;
    public static int yMax;

    public void updateCanvas(){
        this.revalidate();
        this.repaint();
    }

    /** Constructor of the class that works as a link between the classNV and the GUI */
    public CanvasClassTemplate(int x_max, int y_max) {

        task = new TaskTemplate();
        task.plug(this);
        xMax = x_max;
        yMax = y_max;

        image_ref = new BufferedImage(xMax, yMax,BufferedImage.TYPE_BYTE_INDEXED);

    }

    /**
     * This method process the information of the ONV and draw it in the image.
     * This method is called in {@link #paintComponent(Graphics)} method.
     * Change to represent what you want, in this example we will draw a chessboard.
     * @return A BufferedImage with the information drew on it.
     */

    private  BufferedImage GenerateImage() {
        Color color;

        int[][] matrix = task.getData();

        for(int x = 0; x < xMax; x++)
        {
            for(int y = 0; y < yMax; y++)
            {

                if(matrix[x][y]  == 1)
                    color = Color.GREEN;
                else if(matrix[x][y] == 0)
                    color = Color.BLACK;
                else if(matrix[x][y] == 2)
                    color = Color.BLUE;
                else if(matrix[x][y] == 3)
                    color = Color.gray;

                else
                    color = Color.BLACK;

                image_ref.setRGB(x, y, color.getRGB());
            }
        }

        return image_ref;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = (Graphics2D)g;
        if(task.getData()!=null)
            g2.drawImage(GenerateImage(),0,0,1000,1000,this);

    }
}