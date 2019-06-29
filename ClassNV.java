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
    private int[][] matrix;

    /** */

    private static GuiTemplate.CanvasClassTemplate canvasTemplateRef;


    /**
     * 
     * @return
     */

    public int[][] getData()
    {
        return matrix;
    }


    /**
     * 
     * @param ref
     */
    public void plug(GuiTemplate.CanvasClassTemplate ref)
    {

        canvasTemplateRef = ref;

    }


    /** 
     * 
    */
    public void initializer()
    {

    }

    /** */
    public void computeClassNV()
    {

    }
    
}