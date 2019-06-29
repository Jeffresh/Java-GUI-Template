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
    private static int xMax, yMax;

    class CanvasClassTemplate extends JPanel 
    {

        private static final long serialVersionUID = 1L;



        /** Object of the class that Needs Visualization (ONV)  */
        private ClassNV objectNV;


        /** Constructor of the class that works as a link between the classNV and the GUI */

        public CanvasClassTemplate()
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

            int[][] matrix = objectNV.getData();
            
            for(int x=0;x<xMax;x++)
            {
                for(int y=0;y<yMax;y++)
                {
                    if(matrix[x][y]%2==1)
                        color = Color.WHITE; // azul
                    else if(matrix[x][y]%2==0 && matrix[x][y]!=0)
                            color = Color.BLACK; // verde
                    else
                        color = Color.BLACK;

                    image.setRGB(x,y,color.getRGB());



                }
            }

            return image;
        }

      
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics g2 = (Graphics2D)g;
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
    private static JSplitPane window; // window
    /** */
    private static JSplitPane buttons; //buttons
    /** */
    private static JMenuBar topToolBar; //topToolBar
    /** */
    private static JMenu menuFile ; 
    /** */
    private static JMenu menuAbout ; 
    /** */
    private static JMenu menuHelp ;



    /**
     * 
     * @return
     */
    private JMenuBar createBar()
    {

        topToolBar = new JMenuBar();
        menuFile =  new JMenu("File");
        menuAbout = new JMenu("Help");
        menuHelp = new JMenu("About");


        topToolBar.setOpaque(true);
        topToolBar.setBackground(new Color(0,0,0));
        topToolBar.setPreferredSize(new Dimension(200,20));

        topToolBar.add(menuFile);

        topToolBar.add(Box.createHorizontalGlue());
        topToolBar.add(menuHelp);
        topToolBar.add(menuAbout);


        itemMenu1 = new JMenuItem("Item menu 1");
        itemMenu1.addActionListener(this);
        menuFile.add(itemMenu1);

        itemMenu2 = new JMenuItem("Item menu 2");
        itemMenu2.addActionListener(this);
        menuFile.add(itemMenu2);

        menuHelp.add("Help message");
        menuAbout.add("Contact message");



        return topToolBar;
    }


 


    
    


    /**  */

    private static JTextField tnumericVar, tstringVar;
    
    /** */
    private static JLabel lnumericVar, lstringVar;
  


    /** */

    private static JButton initialize, startcpmlt, stopcpmlt;
    


    /**
     * 
     * @return
     */
    private JSplitPane createTextFieldscpmlt()
    {
       

        tnumericVar  = new JTextField();
        tnumericVar.setText(Double.toString(numericVar));
        tnumericVar.addFocusListener(this);

        tstringVar = new JTextField();
        tstringVar.setText(stringVar);
        tstringVar.addFocusListener(this);

        
   

        lnumericVar = new JLabel("Numeric Variable: ");
        lnumericVar.setLabelFor(tnumericVar);

        lstringVar = new JLabel("String Variable: ");
        lstringVar.setLabelFor(tstringVar);

        

        //Lay out the text controls and the labels
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        textControlsPane.setLayout(gridbag);
        textControlsPane.setPreferredSize(new Dimension(100, 900));
        textControlsPane.setMinimumSize(new Dimension(100, 900));

        JLabel[] labels = { lnumericVar, lstringVar};

        JTextField[] textFields = {tnumericVar, tstringVar}; 
   
        
        
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


        frame.setJMenuBar(new GuiTemplate().createBar());
        
        caClassTemplate = new GuiTemplate().new CanvasClassTemplate();
        caClassTemplate.setPreferredSize(new Dimension(1000, 1000));

        buttons =  new GuiTemplate().createTextFieldscpmlt();
        


        window = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,  caClassTemplate,buttons);

        window.setOneTouchExpandable(true);
        
        frame.setMinimumSize(new Dimension(500,500));
        frame.setResizable(true);



        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setContentPane(window);

        frame.validate();
        frame.repaint();
    }

    /** */

    private static SwingWorker<Void,GuiTemplate> worker;

    /**  */
    private static CanvasClassTemplate caClassTemplate;


    /** */

    private static double numericVar = 33 ;

    /**  */

    private static String stringVar = "Hello World";




    public void actionPerformed(ActionEvent e)
    {



        if(e.getSource() == itemMenu1)
        {

            
            frame.remove(window);
          

        }
        
        if(e.getSource()==stopcpmlt)
        {
            worker.cancel(true);
         
            worker.cancel(false);


        }


        if(e.getSource() == initialize)
        {



            caClassTemplate.objectNV = new ClassNV();

            caClassTemplate.objectNV.initializer();
            caClassTemplate.validate();
            caClassTemplate.repaint();
            

        }


        if(e.getSource()==startcpmlt)
        {

            worker = new SwingWorker<Void, GuiTemplate>() 
            {
                @Override
                protected Void doInBackground() 
                {
                    try{ caClassTemplate.objectNV.computeClassNV();}catch(Exception ex){};              
                    return null;
              
                 }
            };

            worker.execute();


        } 
    }
    
    
    /**
     * 
     */
    public void focusGained(FocusEvent e) 
	{
    	//nothing
	}
	 public void focusLost(FocusEvent e) 
	 {
		
	                       
	        if(e.getSource() == tnumericVar)
	        {

	            String nump=tnumericVar.getText();
	            numericVar = Double.parseDouble(nump);
	        }

	        if(e.getSource() == tstringVar)
	        {
	            String nump=tstringVar.getText();
	            stringVar = nump;
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

