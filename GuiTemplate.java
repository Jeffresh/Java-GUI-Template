import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


    /**
     * GuiTemplate.java
     * Purpose: this program
     * implements a Gui template that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */



public class GuiTemplate extends Frame implements ActionListener, FocusListener
{


    private static final long serialVersionUID = 1L;

    /** Image that will be printed on the Gui showing the visualization */
    private static BufferedImage image;

    private class CanvasClassTemplate extends JPanel 
    {

        private static final long serialVersionUID = 1L;



        /** Object of the class that Needs Visualization (ONV)  */
        private ClassNV objectNV;


        /** Constructor of the class that works as a link between the classNV and the GUI */

        public CanvasObjectNV()
        {
        

            objectNV = new ClassNV(/*parameters*/);
            objectNV.plug(this);

            image = new BufferedImage(1000, 1000,BufferedImage.TYPE_BYTE_INDEXED);

        }

        /**
         * This method process the information of the ONV and draw it in the image.
         * This method is called in {@link #paintComponent(Graphics)} method.
         * Change to represent what you want, in this example we will draw a chessboard.
         * @return A BufferedImage with the information drew on it.
         */

        private  BufferedImage GenerateImage()
        {
            Color color;
            
            for(int x=0;x<xMax;x++)
            {
                for(int y=0;y<yMax;y++)
                {
                    if(objectNV.sigma[x][y]%2==1)
                        color = Color.BLUE; // azul
                    else if(objectNV.sigma[x][y]%2==0 && objectNV.sigma[x][y]!=0)
                            color = Color.GREEN; // verde
                    else
                        color = Color.BLACK;

                    image.setRGB(x,y,color.getRGB());



                }
            }

            return image;
        }



        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
      
             
            // Dimension size = getSize();
            // Insets insets = getInsets();

            Graphics g2 = (Graphics2D)g;

            // int w = size.width - insets.left - insets.right ;
            // int h = size.height - insets.top - insets.bottom;
            // int rad = 2;

            g2.drawImage(GenerateImage(),1000,1000,this);




        }
    }



    
    /** The general frame */
    private static JFrame frame;


    /** Generic menu option 1 */
    private static JMenuItem itemMenu1;
    /** Generic menu option 2 */
    private static JMenuItem itemMenu2;

 
  

    /**  */
    private static JSplitPane todo;
    private static JSplitPane botonera;
    private static JMenuBar BarraSuperior;
    private static JMenu menuFile ; 
    private static JMenu menuAbout ; 
    private static JMenu menuHelp ;




    private JMenuBar createBar()
    {

        BarraSuperior = new JMenuBar();
        menuFile =  new JMenu("File");
        menuAbout = new JMenu("Help");
        menuHelp = new JMenu("About");


        BarraSuperior.setOpaque(true);
        BarraSuperior.setBackground(new Color(0,0,0));
        BarraSuperior.setPreferredSize(new Dimension(200,20));

        BarraSuperior.add(menuFile);

        BarraSuperior.add(Box.createHorizontalGlue());
        BarraSuperior.add(menuHelp);
        BarraSuperior.add(menuAbout);


        itemFilecpmlt = new JMenuItem("CPM MODEL");
        itemFilecpmlt.addActionListener(this);

        menuFile.add(itemFilecpmlt);

        menuHelp.add("Busca en Google");
        menuAbout.add("Contacte con la UCA");



        return BarraSuperior;
    }

    private static double lambdaVAreaLight = 5 ;
	private static double lambdaVAreaDark = 10;
	private static double targetVolumeFactorLight =78.5;
    private static double targetVolumeFactorDark = 78.5;

    /**Elasticity Parameters */
	private static double lambdaLAreaLight = 8;
	private static double lambdaLAreaDark = 5;
    private static double lambdaLAreaMixed = 8;
    

	private static double targetElasFactorLight = 50;
	private static double targetElasFactorDark = 50;
    private static double targetElasFactorMixed = 5;
    

	/** Apotosis */

	private static double apoptosis = 0.0;

	/**Proliferation */

	private static int ratiomitotic = 4;
	private static int timemitotic = 32;


    
    



    private static JTextField tMaxX, tMaxY, tMCS,tMCSS, tMaxSigma, tMdensity,
    /*CPM Calculations*/ 
    tJEcm, tjLightCells,tjDarkCells,tJOtherCells,tLambdaSAreaLight, tLambdaSAreaDark, tLambdaVAreaLight,tLambdaVAreaDark,
    tTargetAreaFactorLight,tTargetAreaFactorDark,tTargetVolumeFactorLight,tTargetVolumeFactorDark,
    tLambdaLAreaLight,tLambdaLAreaDark,tLambdaLAreaMixed,
    tTargetElasFactorLight,tTargetElasFactorDark,tTargetElasFactorMixed,

    tdarkCellDecrease,tTemperature,tRatioDarkToLightLcells,

    tApoptosis, tTimemitotic, tRatiomitotic;

    private static JLabel    lMaxX, lMaxY, lMCS,lMCSS, lMaxSigma, lMdensity,
    /*CPM Calculations*/
    lJEcm, ljLightCells,ljDarkCells,lJOtherCells,lLambdaSAreaLight, lLambdaSAreaDark, lLambdaVAreaLight,lLambdaVAreaDark,
    lTargetAreaFactorLight,lTargetAreaFactorDark,lTargetVolumeFactorLight,lTargetVolumeFactorDark,
    lLambdaLAreaLight,lLambdaLAreaDark,lLambdaLAreaMixed,
    lTargetElasFactorLight,lTargetElasFactorDark,lTargetElasFactorMixed,

    ldarkCellDecrease,lTemperature,lRatioDarkToLightLcells,

    lApoptosis, lTimemitotic,lRatiomitotic;



    private static JButton initialize, startcpmlt, stopcpmlt;
    



    private JSplitPane createTextFieldscpmlt()
    {
       

        tMaxX  = new JTextField();
        tMaxX.setText(Integer.toString(xMax));
        tMaxX.addFocusListener(this);

        tMaxY = new JTextField();
        tMaxY.setText(Integer.toString(yMax));
        tMaxY.addFocusListener(this);

        tMCS = new JTextField();
        tMCS.setText(Integer.toString(mcs));
        tMCS.addFocusListener(this);

        tMCSS = new JTextField();
        tMCSS.setText(Integer.toString(mcSubsteps));
        tMCSS.addFocusListener(this);

        tMaxSigma = new JTextField();
        tMaxSigma.setText(Integer.toString(sigmaMax));
        tMaxSigma.addFocusListener(this);

        tMdensity = new JTextField();
        tMdensity.setText(Double.toString(initialMatrixDensity));
        tMdensity.addFocusListener(this);


        /*CPM calculations*/

        tJEcm = new JTextField();
        tJEcm.setText(Double.toString(jEcm));
        tJEcm.addFocusListener(this);

        tjLightCells = new JTextField();
        tjLightCells.setText(Double.toString(jLightCells));
        tjLightCells.addFocusListener(this);

        tjDarkCells = new JTextField();
        tjDarkCells.setText(Double.toString(jDarkCells));
        tjDarkCells.addFocusListener(this);

        tJOtherCells = new JTextField();
        tJOtherCells.setText(Double.toString(jDifferentCells));
        tJOtherCells.addFocusListener(this);

        /**Area */

        tLambdaSAreaLight = new JTextField();
        tLambdaSAreaLight.setText(Double.toString(lambdaSAreaLight));
        tLambdaSAreaLight.addFocusListener(this);

        tLambdaSAreaDark = new JTextField();
        tLambdaSAreaDark.setText(Double.toString(lambdaSAreaDark));
        tLambdaSAreaDark.addFocusListener(this);

        /**Volume */

        tLambdaVAreaLight = new JTextField();
        tLambdaVAreaLight.setText(Double.toString(lambdaVAreaLight));
        tLambdaVAreaLight.addFocusListener(this);
        
        tLambdaVAreaDark = new JTextField();
        tLambdaVAreaDark.setText(Double.toString(lambdaVAreaDark));
        tLambdaVAreaDark.addFocusListener(this);

        /**L */


        tLambdaLAreaLight = new JTextField();
        tLambdaLAreaLight.setText(Double.toString(lambdaLAreaLight));
        tLambdaLAreaLight.addFocusListener(this);
        
        tLambdaLAreaDark = new JTextField();
        tLambdaLAreaDark.setText(Double.toString(lambdaLAreaDark));
        tLambdaLAreaDark.addFocusListener(this);

        tLambdaLAreaMixed = new JTextField();
        tLambdaLAreaMixed.setText(Double.toString(lambdaLAreaMixed));
        tLambdaLAreaMixed.addFocusListener(this);


        /**AreaT */
        tTargetAreaFactorLight = new JTextField();
        tTargetAreaFactorLight.setText(Double.toString(targetAreaFactorLight));
        tTargetAreaFactorLight.addFocusListener(this);

        tTargetAreaFactorDark = new JTextField();
        tTargetAreaFactorDark.setText(Double.toString(targetAreaFactorDark));
        tTargetAreaFactorDark.addFocusListener(this);

        /**VolumeT */

        tTargetVolumeFactorLight = new JTextField();
        tTargetVolumeFactorLight.setText(Double.toString(targetVolumeFactorLight));
        tTargetVolumeFactorLight.addFocusListener(this);

        tTargetVolumeFactorDark = new JTextField();
        tTargetVolumeFactorDark.setText(Double.toString(targetVolumeFactorDark));
        tTargetVolumeFactorDark.addFocusListener(this);

        /** LT */
        tTargetElasFactorLight = new JTextField();
        tTargetElasFactorLight.setText(Double.toString(targetElasFactorLight));
        tTargetElasFactorLight.addFocusListener(this);

        tTargetElasFactorDark = new JTextField();
        tTargetElasFactorDark.setText(Double.toString(targetElasFactorDark));
        tTargetElasFactorDark.addFocusListener(this);

        
        tTargetElasFactorMixed = new JTextField();
        tTargetElasFactorMixed.setText(Double.toString(targetElasFactorMixed));
        tTargetElasFactorMixed.addFocusListener(this);
        
        
        tTemperature = new JTextField();
        tTemperature.setText(Double.toString(temperature));
        tTemperature.addFocusListener(this);

        tRatioDarkToLightLcells = new JTextField();
        tRatioDarkToLightLcells.setText(Integer.toString(ratioDarkToLightCells));
        tRatioDarkToLightLcells.addFocusListener(this);
        
        
        // listDarkCellDecrease = new JComboBox(si_no);
        // listDarkCellDecrease.addFocusListener(this);


        //the new ones

        tApoptosis = new JTextField();
        tApoptosis.setText(Double.toString(apoptosis));
        tApoptosis.addFocusListener(this);

        tTimemitotic = new JTextField();
        tTimemitotic.setText(Integer.toString(timemitotic));
        tTimemitotic.addFocusListener(this);

        tRatiomitotic = new JTextField();
        tRatiomitotic.setText(Integer.toString(ratiomitotic));
        tRatiomitotic.addFocusListener(this);

   

        lMaxX = new JLabel("Max X: ");
        lMaxX.setLabelFor(tMaxX);

        lMaxY = new JLabel("Max Y: ");
        lMaxY.setLabelFor(tMaxY);

        lMCS = new JLabel("Monte-Carlo-Steps: ");
        lMCS.setLabelFor(tMCS);

        lMCSS = new JLabel("Monte-Carlo-SubSteps: ");
        lMCSS.setLabelFor(tMCSS);

        lMaxSigma = new JLabel("Max Sigma: ");
        lMaxSigma.setLabelFor(tMaxSigma);

        lMdensity = new JLabel("Matrix Density: ");
        lMdensity.setLabelFor(tMdensity);

        

        /*CPM Calculations*/
        lJEcm = new JLabel("Emc's energy: ");
        lJEcm.setLabelFor(tJEcm);

        ljLightCells = new JLabel("Light cell's energy");
        ljLightCells.setLabelFor(tjLightCells);
        
        ljDarkCells = new JLabel("Dark cell's energy: ");
        ljDarkCells.setLabelFor(tjDarkCells);

        lJOtherCells = new JLabel("Mixed cell's energy: ");
        lJOtherCells.setLabelFor(tJOtherCells);


        lLambdaSAreaLight = new JLabel("Lambda area Light: ");
        lLambdaSAreaLight.setLabelFor(tLambdaSAreaLight);

        lTargetAreaFactorLight = new JLabel("Light cell's target area factor: ");
        lTargetAreaFactorLight.setLabelFor(tTargetAreaFactorLight);

        lLambdaSAreaDark = new JLabel("Lambda area Dark: ");
        lLambdaSAreaDark.setLabelFor(tLambdaSAreaDark);

        lTargetAreaFactorDark = new JLabel("Dark cell's target area factor: ");
        lTargetAreaFactorDark.setLabelFor(tTargetAreaFactorDark);

        //////////////////////////////////////////////////

        lLambdaVAreaLight = new JLabel("Lambda Volume Light: ");
        lLambdaVAreaLight.setLabelFor(tLambdaVAreaLight);

        lTargetVolumeFactorLight = new JLabel("Light cell's target Volume factor: ");
        lTargetVolumeFactorLight.setLabelFor(tTargetVolumeFactorLight);

        lLambdaVAreaDark = new JLabel("Lambda Volume Dark: ");
        lLambdaVAreaDark.setLabelFor(tLambdaVAreaDark);

        lTargetVolumeFactorDark = new JLabel("Dark cell's target Volume factor: ");
        lTargetVolumeFactorDark.setLabelFor(tTargetVolumeFactorDark);

        ///////////////////////////////////////////////////////

        lLambdaLAreaLight = new JLabel("Lambda Elasticity Light: ");
        lLambdaLAreaLight.setLabelFor(tLambdaLAreaLight);

        lTargetElasFactorLight = new JLabel("Light cell's target Distance factor: ");
        lTargetElasFactorLight.setLabelFor(tTargetElasFactorLight);

        lLambdaLAreaDark = new JLabel("Lambda Elasticity Dark: ");
        lLambdaLAreaDark.setLabelFor(tLambdaLAreaDark);

        lTargetElasFactorDark = new JLabel("Dark cell's target Distance factor: ");
        lTargetElasFactorDark.setLabelFor(tTargetElasFactorDark);

        
        lLambdaLAreaMixed = new JLabel("Lambda Elasticity Mixed: ");
        lLambdaLAreaMixed.setLabelFor(tLambdaLAreaMixed);

        lTargetElasFactorMixed = new JLabel("Mixed cell's target Distance factor: ");
        lTargetElasFactorMixed.setLabelFor(tTargetElasFactorMixed);

        ////////////////////////////////////////////////////////

        lTemperature = new JLabel("Temperature: ");
        lTemperature.setLabelFor(tTemperature);

        lRatioDarkToLightLcells = new JLabel("Light/Dark ratio (1/x): ");
        lRatioDarkToLightLcells.setLabelFor(tRatioDarkToLightLcells);
        
        ldarkCellDecrease = new JLabel("Dark cells can be erradicated: ");
        ldarkCellDecrease.setLabelFor(tdarkCellDecrease);

        // the new ones

        lApoptosis = new JLabel("Probability Apoptosis: ");
        lApoptosis.setLabelFor(tApoptosis);

        lTimemitotic = new JLabel("Time mitotic events: ");
        lTimemitotic.setLabelFor(tTimemitotic);

        lRatiomitotic = new JLabel("Cells/mitotic cells ratio (1/x): ");
        lRatiomitotic.setLabelFor(tRatiomitotic);


        //Lay out the text controls and the labels
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        textControlsPane.setLayout(gridbag);
        textControlsPane.setPreferredSize(new Dimension(100, 900));
        textControlsPane.setMinimumSize(new Dimension(100, 900));

        JLabel[] labels = { lMaxX, lMaxY, lMCS, lMCSS, lMaxSigma, lMdensity,
        /*CPM Calculations*/
        lTemperature, lJEcm, ljLightCells,ljDarkCells,lJOtherCells,lLambdaSAreaLight,lLambdaSAreaDark,
        lTargetAreaFactorLight,lTargetAreaFactorDark,

        // lLambdaVAreaLight,lLambdaVAreaDark,
        // lTargetVolumeFactorLight,lTargetVolumeFactorDark,

        lLambdaLAreaLight, lLambdaLAreaDark,lLambdaLAreaMixed,
        lTargetElasFactorLight,lTargetElasFactorDark,lTargetElasFactorMixed,
        
        lRatioDarkToLightLcells,lTimemitotic,lRatiomitotic,lApoptosis}; 

        JTextField[] textFields = {tMaxX, tMaxY, tMCS, tMCSS, tMaxSigma, tMdensity,
        /*CPM Calculations*/ 
        tTemperature, tJEcm, tjLightCells,tjDarkCells,tJOtherCells,tLambdaSAreaLight, tLambdaSAreaDark,
        tTargetAreaFactorLight,tTargetAreaFactorDark,

        // tLambdaVAreaLight, tLambdaVAreaDark,
        // tTargetVolumeFactorLight,tTargetVolumeFactorDark,

        tLambdaLAreaLight, tLambdaLAreaDark,tLambdaLAreaMixed,
        tTargetElasFactorLight,tTargetElasFactorDark,tTargetElasFactorMixed,



        tRatioDarkToLightLcells,tTimemitotic,tRatiomitotic,tApoptosis}; 
   
        
        
        // JComboBox[] lists = {listDarkCellDecrease};

        // addLabelTextRows(labels,textFields,new JComboBox,textControlsPane);
        addLabelTextRows(labels,textFields,textControlsPane);

        textControlsPane.setBorder(
                                   BorderFactory.createCompoundBorder(
                                                                      BorderFactory.createTitledBorder("Variables"),
                                                                      BorderFactory.createEmptyBorder(5,5,5,5)));
        initialize = new JButton("Initialize");
        initialize.addActionListener(this);

        startcpmlt = new JButton("Start");
        startcpmlt.addActionListener(this);

        stopcpmlt = new JButton("Stop");
        stopcpmlt.addActionListener(this);

        JPanel botonesPane = new JPanel();

        botonesPane.add(initialize,BorderLayout.CENTER);
        botonesPane.add(startcpmlt,BorderLayout.CENTER);
        botonesPane.add(stopcpmlt,BorderLayout.CENTER);

        botonesPane.setPreferredSize(new Dimension(100, 5));
        botonesPane.setMaximumSize(new Dimension(100, 5));

        botonesPane.setBorder(
                  BorderFactory.createCompoundBorder(
                                                     BorderFactory.createTitledBorder("Control"),
                                                     BorderFactory.createEmptyBorder(5,5,5,5)));



        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              textControlsPane,
                                              botonesPane);
        
        


        splitPane.setOneTouchExpandable(true);


        return splitPane;   
    }    
 

    private void addLabelTextRows(JLabel[] labels,
                                  JTextField[] textFields, 
                                //   JComboBox[] list,
                                  Container container)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;
        // int numlist = list.length;
        
 
        for (int i = 0; i < numLabels; i++) 
        {
        	labels[i].setFont(new Font(null,0,20));
        	textFields[i].setFont(new Font(null,0,20));
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 1.0;                       //reset to default
//            c.gridheight = 19;
            container.add(labels[i], c);
 
            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1.0;
            textFields[i].setColumns(3);
            container.add(textFields[i], c);
        }
        
    }


    private static  void createAndShowGUI()
    {
        frame = new JFrame("CPM-Secuencial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setJMenuBar(new GuiSecuencial().createBar());
        
        caCells = new GuiSecuencial().new CanvasCells();
        caCells.setPreferredSize(new Dimension(1000, 1000));

        botonera =  new GuiSecuencial().createTextFieldscpmlt();
        


        todo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,  caCells,botonera);

        todo.setOneTouchExpandable(true);
        
        frame.setMinimumSize(new Dimension(500,500));
        frame.setResizable(true);



        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setContentPane(todo);

        frame.validate();
        frame.repaint();
    }


    private static SwingWorker<Void,GuiSecuencial> worker;


        /**CPM calculations */
    /**  Energy interaction for the ecm. */
    private static double jEcm = 20;

    /**  Energy interaction for even cells. */
    private static double jLightCells = -20;

    /**  Energy interaction for other cells. */
    private static double jDarkCells = -5;

    /**  Energy interaction for even with odd cells. */
    private static double jDifferentCells = -10;

    /** The lambda for area calculation. */
    // private static double lambdaArea = 1.0;

    private static double lambdaSAreaLight = 1.0;

    /**  The factor for the targetArea of light cells. */
    private static  double targetAreaFactorLight =31.4 ;

    private static double lambdaSAreaDark = 1.0;

    /**  The factor for the targetArea of dark cells. */
    private static double targetAreaFactorDark =31.4 ;

    /** cells can die */
    private static int darkCellDecrease = 1;

    /** The beta or temperature within the lattice */
    private static double temperature = 20;

    /** relative proportion of dark and light cells */
    private static int ratioDarkToLightCells = 5;
    /** end of CPMcalculations*/

    /** CPMLattice*/
    
    /** Number of rows of lattice */
    private static int xMax =1000;
    
    /** Number of columns of lattice */
    private static int yMax = 1000 ;
    
    /** Number of monte-carlo-steps. */
    private static int mcs = 100 ;
    
    /** The amount of sub-steps per monte-carlo-step*/
    private static int mcSubsteps = 25; // or smaller down to 25 if you want to have a slower growth rate.

    /** Maximum value of sigma (cells) including sigma=0=ECM */
    private static int sigmaMax = 100;
    
    /** The initial density for ECM (celltype == 0) to other cells (cellType != 0)*/
    private static double initialMatrixDensity = 0.9;


    private static CanvasCells caCells;




    public void actionPerformed(ActionEvent e)
    {



        if(e.getSource() == itemFilecpmlt)
        {

            
            frame.remove(todo);
          

        }
        
        if(e.getSource()==stopcpmlt)
        {
            worker.cancel(true);
         
            worker.cancel(false);


        }


        if(e.getSource() == initialize)
        {



            caCells.cl = new CellsSecuencial(xMax,yMax,mcs,mcSubsteps,sigmaMax,initialMatrixDensity,
            jEcm,jLightCells,jDarkCells,jDifferentCells,lambdaSAreaLight,lambdaSAreaDark,lambdaVAreaLight,lambdaVAreaDark,
            targetAreaFactorLight,targetAreaFactorDark,targetVolumeFactorLight,targetVolumeFactorDark,darkCellDecrease,temperature,
            ratioDarkToLightCells,apoptosis,timemitotic,ratiomitotic);

            caCells.cl.Initializer();
            caCells.validate();
            caCells.repaint();
            

        }


        if(e.getSource()==startcpmlt)
        {

            worker = new SwingWorker<Void, GuiSecuencial>() 
            {
                @Override
                protected Void doInBackground() 
                {
                    try{ caCells.cl.computeCPM();}catch(Exception ex){};              
                    return null;
              
                 }
            };

            worker.execute();

        
           
            // System.out.println("\n");

            // System.out.println("Generaciones: "+ gens);
            //  System.out.println("Max X: "+ xMax);
            //  System.out.println("Max Y: "+ yMax);
            // System.out.println("Monte-Carlo-Steps"+mcs);
            // System.out.println("Monte-Carlo-Substeps: "+mcSubsteps);
            //  System.out.println("Max Sigma: "+ Cells.ncells());
            // System.out.println("Matrix Density: "+ initialMatrixDensity);
            // // CPMcalculations

            // System.out.println("Temperature: "+ temperature);
            // System.out.println("emc's energy: "+ jEcm);
            // System.out.println("Light cell's energy: "+ jLightCells);
            // System.out.println("Dark cell's energy: "+ jDarkCells);
            // System.out.println("Mixed cell's energy: "+ jDifferentCells);
            // System.out.println("lambda for area calc: "+ lambdaArea);
            // System.out.println("lambda for Light: "+ lambdaSAreaLight);
            // System.out.println("lambda for Dark: "+ lambdaSAreaDark);
            // System.out.println("ligth cell's target area factor: "+ targetAreaFactorLight);
            // System.out.println("dark cell's target area factor: "+ targetAreaFactorDark);
            // System.out.println("light/dark ratio 1/(X): "+ ratioDarkToLightCells);
            // System.out.println("darkCellDecrease: "+ darkCellDecrease);

            // caCells.validate();
            // caCells.repaint();


        } 
    }
    
    
    public void focusGained(FocusEvent e) 
	{
    	//nothing
	}
	 public void focusLost(FocusEvent e) 
	 {
		
	                       
	        if(e.getSource() == tMaxX)
	        {

	            String nump=tMaxX.getText();
	            xMax = Integer.parseInt(nump);
	        }

	        if(e.getSource() == tMaxY)
	        {
	            String nump=tMaxY.getText();
	            yMax= Integer.parseInt(nump);
	        }

	        if(e.getSource() == tMCS)
	        {
	            String nump=tMCS.getText();
                mcs= Integer.parseInt(nump);
                CellsSecuencial.setMcs(mcs);
	        }

	        if(e.getSource() == tMCSS)
	        {
	            String nump=tMCSS.getText();
                mcSubsteps= Integer.parseInt(nump);
                CellsSecuencial.setsMcs(mcSubsteps);
	        }

	        if(e.getSource() == tMaxSigma)
	        {

	            String nump=tMaxSigma.getText();
	            sigmaMax= Integer.parseInt(nump);
	        }

	        if(e.getSource() == tMdensity)
	        {

	            String nump=tMdensity.getText();
	            initialMatrixDensity= Double.parseDouble(nump);
	        }

	        if(e.getSource() == tJEcm)
	        {

	            String nump=tJEcm.getText();
	            jEcm= Double.parseDouble(nump);
	        }


	        if(e.getSource() == tjLightCells)
	        {

	            String nump=tjLightCells.getText();
	            jLightCells= Double.parseDouble(nump);
	        }

	        if(e.getSource() == tjDarkCells)
	        {

	            String nump=tjDarkCells.getText();
	            jDarkCells= Double.parseDouble(nump);
	        }

	        if(e.getSource() == tJOtherCells)
	        {
	            String nump = tJOtherCells.getText();
	            jDifferentCells = Double.parseDouble(nump);
	        }

	    
            
            if(e.getSource() == tLambdaSAreaLight)
            {
                String nump = tLambdaSAreaLight.getText();
                lambdaSAreaLight = Double.parseDouble(nump);                

            }

                 
            if(e.getSource() == tLambdaVAreaLight)
            {
                String nump = tLambdaVAreaLight.getText();
                lambdaVAreaLight = Double.parseDouble(nump);                

            }

	        if(e.getSource() == tTargetAreaFactorLight)
	        {
	            String nump=tTargetAreaFactorLight.getText();
	            targetAreaFactorLight= Double.parseDouble(nump);
            }

            if(e.getSource() == tTargetVolumeFactorLight)
	        {
	            String nump=tTargetVolumeFactorLight.getText();
	            targetVolumeFactorLight= Double.parseDouble(nump);
            }
            
            if(e.getSource() == tLambdaSAreaDark)
            {
                String nump = tLambdaSAreaDark.getText();
                lambdaSAreaDark = Double.parseDouble(nump);                
            }

            if(e.getSource() == tLambdaVAreaDark)
            {
                String nump = tLambdaVAreaDark.getText();
                lambdaVAreaDark = Double.parseDouble(nump);                
            }


	        if(e.getSource() == tTargetAreaFactorDark)
	        {
	            String nump=tTargetAreaFactorDark.getText();
	            targetAreaFactorDark= Double.parseDouble(nump);
            }
            
            if(e.getSource() == tTargetVolumeFactorDark)
	        {
	            String nump=tTargetVolumeFactorDark.getText();
	            targetVolumeFactorDark= Double.parseDouble(nump);
	        }

	        if(e.getSource() == tTemperature)
	        {

	            String nump=tTemperature.getText();
	            temperature= Double.parseDouble(nump);
	        }
	        if(e.getSource() == tRatioDarkToLightLcells)
	        {

	            String nump=tRatioDarkToLightLcells.getText();
	            ratioDarkToLightCells= Integer.parseInt(nump);
            }
            if(e.getSource() == tTimemitotic)
	        {

	            String nump=tTimemitotic.getText();
	            timemitotic= Integer.parseInt(nump);
            }

            if(e.getSource() == tRatiomitotic)
	        {

	            String nump=tRatiomitotic.getText();
	            ratiomitotic= Integer.parseInt(nump);
            }

            if(e.getSource() == tApoptosis)
	        {

	            String nump=tApoptosis.getText();
	            apoptosis= Double.parseDouble(nump);
	        }






	       
	       
	 }
    
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });
    }
}

