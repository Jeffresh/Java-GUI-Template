import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;


/**
     * GuiTemplate.java
     * Purpose: this program
     * implements a Gui template that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */



public class GuiTemplate extends Frame implements ActionListener, FocusListener {

    private static final long serialVersionUID = 1L;

    /** The general frame */
    private static JFrame frame;
    /**  */
    private static JSplitPane window; // window
    /** */
    private static JMenuBar nav_bar;


    @NotNull
    private JMenuBar createTopBar(Color color, Dimension dimension) {

        JMenuBar top_bar = new JMenuBar();
        top_bar.setOpaque(true);
        top_bar.setBackground(color);
        top_bar.setPreferredSize(dimension);

        return top_bar;
    }

    @NotNull
    private JMenu createMenu(@NotNull String menu_name, Font font, Color color) {

        JMenu menu = new JMenu(menu_name);
        menu.setFont(font);
        menu.setForeground(color);
        return menu;
    }

    @NotNull
    private  Map<String, JMenu> createMenusItems(@NotNull Map<String,String[]> items, Color color, Font font) {

        JMenuItem item;
        JMenu m;
        Map<String, JMenu> menus = new HashMap<>();

        for(Map.Entry<String,String[]> menu: items.entrySet()){
            String menu_name = menu.getKey();
            m = createMenu(menu_name, font , color);
            for(String item_name :menu.getValue()) {
                item = new JMenuItem(item_name);
                item.setFont(font);
                item.addActionListener(this);
                m.add(item);
            }
            menus.put(menu_name, m);
        }

        return menus;

    }

    private JMenuBar createNavBar() {

        Font menu_font = new Font("Dialog", Font.PLAIN, 20);
        Color menu_font_color = new Color(168, 168, 168);
        Color navbar_color = new Color(0,0,0);
        Dimension navbar_dimension = new Dimension(200,40);
        Map<String, String[] > menu_items = new HashMap<>();

        menu_items.put("File", new String[]{"Item menu 1", "Item menu 2"});
        menu_items.put("Help", new String[]{"Help message"});
        menu_items.put("About", new String[]{"About message"});

        nav_bar = createTopBar(navbar_color, navbar_dimension);

        Map<String, JMenu> menus = createMenusItems(menu_items, menu_font_color, menu_font);

        nav_bar.add(menus.get("File"));
        nav_bar.add(Box.createHorizontalGlue());
        nav_bar.add(menus.get("Help"));
        nav_bar.add(menus.get("About"));

        return nav_bar;
    }

    private static JTextField tnumericVar, tstringVar;

    private static JButton initialize, startcpmlt, stopcpmlt;

    private JSplitPane createTextFields() {

        tnumericVar  = new JTextField();
        tnumericVar.setText(Double.toString(numericVar));
        tnumericVar.addFocusListener(this);

        tstringVar = new JTextField();
        tstringVar.setText(stringVar);
        tstringVar.addFocusListener(this);

        JLabel lnumericVar = new JLabel("Numeric Variable: ");
        lnumericVar.setLabelFor(tnumericVar);

        JLabel lstringVar = new JLabel("String Variable: ");
        lstringVar.setLabelFor(tstringVar);

        //Lay out the text controls and the labels
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        textControlsPane.setLayout(gridbag);
        textControlsPane.setPreferredSize(new Dimension(100, 900));
        textControlsPane.setMinimumSize(new Dimension(100, 900));

        JLabel[] labels = {lnumericVar, lstringVar};

        JTextField[] textFields = {tnumericVar, tstringVar}; 

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
 

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields,
                                  //   JComboBox[] list,
                                  Container container){

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;
        // int numlist = list.length;

        for (int i = 0; i < numLabels; i++){

        	labels[i].setFont(new Font(null, Font.PLAIN,20));
        	textFields[i].setFont(new Font(null, Font.PLAIN,20));
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


    private static  void createAndShowGUI(){

        frame = new JFrame("Generic-Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new GuiTemplate().createNavBar());

        int xMax = 1000;
        int yMax = 1000;
        caClassTemplate = new CanvasClassTemplate(xMax, yMax);
        caClassTemplate.setPreferredSize(new Dimension(1000, 1000));

        //buttons
        JSplitPane buttons = new GuiTemplate().createTextFields();

        window = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,  caClassTemplate, buttons);
        window.setOneTouchExpandable(true);
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
    private static JLabel[] canvas_labels;


    public void actionPerformed(@NotNull ActionEvent e) {

        if(e.getSource() == nav_bar.getMenu(0).getItem(0)) {
            frame.remove(window);
        }

        if(e.getSource() == nav_bar.getMenu(0).getItem(1)) {
            frame.remove(window);
        }
        
        if(e.getSource()==stopcpmlt) {
            worker.cancel(true);
            worker.cancel(false);
        }

        if(e.getSource() == initialize) {

            CanvasClassTemplate.objectNV = new ClassNV();
            caClassTemplate.objectNV.plug(caClassTemplate);
            caClassTemplate.objectNV.initializer();
            caClassTemplate.validate();
            caClassTemplate.repaint();

        }

        if(e.getSource()==startcpmlt) {
            worker = new SwingWorker<Void, GuiTemplate>() 
            {
                @Override
                protected Void doInBackground() {
                    try{

                        CanvasClassTemplate.objectNV.computeClassNV();
                    }
                    catch(Exception ex){System.out.println("Worker exception");}
                    return null;
                }
            };
            worker.execute();
        }

    }



    /**
     * 
     */
    public void focusGained(FocusEvent e) {
    	//nothing

	}
	public void focusLost(FocusEvent e) {
            String nump;

            if(e.getSource() == tnumericVar){
                nump = tnumericVar.getText();
                numericVar = Double.parseDouble(nump);
                JLabel lnumeric_var_value = new JLabel(nump);
                lnumeric_var_value.setFont(new Font(null, Font.PLAIN,50));
                caClassTemplate.add(lnumeric_var_value);
            }

            if(e.getSource() == tstringVar) {
                nump = tstringVar.getText();
                stringVar = nump;
                JLabel lstring_var_value = new JLabel(stringVar);
                lstring_var_value.setFont(new Font(null, Font.PLAIN,50));
                caClassTemplate.add(lstring_var_value);
            }

    }
    
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.
                SwingUtilities.
                invokeLater(GuiTemplate::createAndShowGUI);
    }
}

