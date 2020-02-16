   /**
     * ClassNV.java
     * Purpose: generic Class that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */



public class ClassNV
{


    /** */
    private static int[][] matrix;

    /** */

    private static CanvasClassTemplate canvasTemplateRef;

    /**
     * 
     * @return
     */

    public int[][] getData() { return matrix; }

    /**
     * 
     * @param ref
     */
    public void plug(CanvasClassTemplate ref) { canvasTemplateRef = ref; }

    /** 
     * 
    */
    public void initializer() {

        matrix = new int[1000][1000];

    }

    /** */
    public void computeClassNV()
    {

        for (int i = 1; i < 1000; i=i+10) {
            for (int j = 1; j < 1000; j++ ) {
                matrix[j][i] = 1;
                canvasTemplateRef.paintImmediately(0,0,1000,1000);

            }

        }

    }
    
}