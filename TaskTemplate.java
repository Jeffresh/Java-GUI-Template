   /**
     * ClassNV.java
     * Purpose: generic Class that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */

public class TaskTemplate
{

    private static int[][] matrix;

    private static CanvasClassTemplate canvasTemplateRef;

    public int[][] getData() { return matrix; }

    public void plug(CanvasClassTemplate ref) { canvasTemplateRef = ref; }

    public void initializer(int value) {
        matrix = new int[1000][1000];

        for (int i = 0; i < 1000 ; i++) {
            for (int j = 0; j < 1000 ; j++) {
                matrix[i][j] = value;

            }

        }
    }
    public static Boolean abort = false;

    public static void stop() {
        abort = true;
    }

    public void computeTask(int line)
    {
        abort = false;

        if(matrix[0][0]==2)
        {
            for (int j = 1; j < 1000; j++ ) {
                if(abort)
                    break;
                matrix[line][j] = 1;
                canvasTemplateRef.paintImmediately(0,0,1000,1000);

            }

        }
        else if(matrix[0][0]==3){
            for (int j = 1; j < 1000; j++ ) {
                if(abort)
                    break;
                matrix[j][line] = 1;
                canvasTemplateRef.paintImmediately(0,0,1000,1000);

            }
        }

    }
    
}