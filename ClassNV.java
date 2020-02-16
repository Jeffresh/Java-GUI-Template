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
        System.out.println(matrix[0][0]);

    }

    /** */
    public void computeClassNV()
    {

    }
    
}