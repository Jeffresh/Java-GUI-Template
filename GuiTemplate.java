import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;

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

    private static JMenuBar nav_bar;
    private static GenericChart chart;
    private static String[] buttons_names = {"Initialize", "Start", "Stop"};


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
        menu_items.put("Plot", new String[]{"Chart 1"});
        menu_items.put("Help", new String[]{"Help message"});
        menu_items.put("About", new String[]{"About message"});

        nav_bar = createTopBar(navbar_color, navbar_dimension);

        Map<String, JMenu> menus = createMenusItems(menu_items, menu_font_color, menu_font);

        nav_bar.add(menus.get("File"));
        nav_bar.add(menus.get("Plot"));
        nav_bar.add(Box.createHorizontalGlue());
        nav_bar.add(menus.get("Help"));
        nav_bar.add(menus.get("About"));

        return nav_bar;
    }

    private static JTextField textfield_numericVar, textfield_stringVar;

    private static JButton initialize_button, start_button, stop_button;
    private static Map<String, JButton> gui_buttons = new HashMap<String, JButton>();

    private Map<String, JButton> create_buttons(String[] button_names){

        Map<String, JButton> buttons_dict = new HashMap<String, JButton>();
        JButton button;

        for (String name: button_names) {
            button = new JButton(name);
            button.addActionListener(this);
            buttons_dict.put(name, button);
        }

        return buttons_dict;
    }

    public static Map<String, String> textfields = new HashMap<>();
    private static String[] textfield_labels = {"Numeric Variable: ", "String Variable: "};

    private JPanel createButtonsPane(){

        gui_buttons = create_buttons(buttons_names);
        JPanel buttons_pane = new JPanel();
        for(String button_name: buttons_names)
            buttons_pane.add(gui_buttons.get(button_name), BorderLayout.CENTER);

        buttons_pane.setPreferredSize(new Dimension(100, 5));
        buttons_pane.setMaximumSize(new Dimension(100, 5));
        buttons_pane.setMinimumSize(new Dimension(100, 5));

        buttons_pane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Control"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        return buttons_pane;

    }

    private Object[] createAuxTextFields(Map<String, String> textfields, String[] label_names){
        JLabel[] labels = new JLabel[label_names.length];
        JTextField[] textFields = new JTextField[label_names.length];
        int index = 0;

        for(String label: label_names){
            textFields[index] = new JTextField();
            textFields[index].setText(textfields.get(label));
            textFields[index].addFocusListener(this);
            labels[index] = new JLabel(label);
            labels[index].setLabelFor(textFields[index]);
            index++;
        }

        return new Object[]{labels, textFields};

    }

    private JSplitPane createTextFields() {

        textfields.put("Numeric Variable: ", "30");
        textfields.put("String Variable: ", "Hello World");

//        textfield_numericVar = new JTextField();
//        textfield_numericVar.setText(Double.toString(numeric_var));
//        textfield_numericVar.addFocusListener(this);
//
//        textfield_stringVar = new JTextField();
//        textfield_stringVar.setText(string_var);
//        textfield_stringVar.addFocusListener(this);
//
//        JLabel numeric_variable_label = new JLabel("Numeric Variable: ");
//        numeric_variable_label.setLabelFor(textfield_numericVar);
//
//        JLabel string_variable_label = new JLabel("String Variable: ");
//        string_variable_label.setLabelFor(textfield_stringVar);

        Object[]  labels_textfields = createAuxTextFields(textfields, textfield_labels);

        JPanel input_variables_pane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        input_variables_pane.setLayout(gridbag);
        input_variables_pane.setPreferredSize(new Dimension(100, 900));
        input_variables_pane.setMinimumSize(new Dimension(100, 900));

        JLabel[] input_variables_labels = (JLabel[]) labels_textfields[0];

        JTextField[] input_variables_textfields = (JTextField[]) labels_textfields[1];

        addLabelTextRows(input_variables_labels,input_variables_textfields,input_variables_pane);

        input_variables_pane.setBorder(
                                   BorderFactory.createCompoundBorder(
                                                                      BorderFactory.createTitledBorder("Variables"),
                                                                      BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttons_pane = createButtonsPane();

        JSplitPane control_center_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              input_variables_pane,
                                              buttons_pane);
        control_center_panel.setMaximumSize(new Dimension(800,800));
        control_center_panel.setMinimumSize(new Dimension(800,800));
        input_variables_pane.setMaximumSize(new Dimension(800,800));
        input_variables_pane.setMinimumSize(new Dimension(800,800));

        control_center_panel.setOneTouchExpandable(true);

        return control_center_panel;
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

        JFrame frame = new JFrame("Generic-Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setJMenuBar(new GuiTemplate().createNavBar());

        int xMax = 1000;
        int yMax = 1000;
        canvas_template = new CanvasClassTemplate(xMax, yMax);
        canvas_template.setPreferredSize(new Dimension(1000, 1000));


        JSplitPane buttons = new GuiTemplate().createTextFields();
        chart = new GenericChart();
//        JSplitPane tools = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttons, chart.chartpanel);

        JSplitPane window = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas_template, buttons);
        window.setOneTouchExpandable(true);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setContentPane(window);
        frame.validate();
        frame.repaint();


    }

    private static SwingWorker<Void,GuiTemplate> worker;

    private static CanvasClassTemplate canvas_template;

    private static double numeric_var = 33 ;

    private static String string_var = "Hello World";
    private static JLabel label_numeric_var_value;
    private static JLabel label_string_var_value;
    private static int value = 0;

    public void showURI(String uri){
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void deleteCanvasLabels(@NotNull JLabel[] labels){

        for(JLabel label: labels){
            if(label_string_var_value !=null) canvas_template.remove(label);

        }

    }

    public void deleteCanvasLabels(){
        if(label_string_var_value !=null) canvas_template.remove(label_string_var_value);
        if(label_numeric_var_value !=null) canvas_template.remove(label_numeric_var_value);

    }

    public void actionPerformed(@NotNull ActionEvent e) {

        if(e.getSource() == nav_bar.getMenu(0).getItem(0)) {
//      frame.remove(window);
            value = 2;
            deleteCanvasLabels();
            CanvasClassTemplate.objectNV.initializer(value);
            canvas_template.updateCanvas();
        }

        if(e.getSource() == nav_bar.getMenu(0).getItem(1)) {
            value = 3;
            deleteCanvasLabels();
            CanvasClassTemplate.objectNV.initializer(value);
            canvas_template.updateCanvas();
        }
//
        if(e.getSource() == nav_bar.getMenu(1).getItem(0)){
            worker = new SwingWorker<Void, GuiTemplate>()
            {
                @Override
                protected Void doInBackground() {
                    try{
                        RealTimeChart realTimeChart = new RealTimeChart();

                        realTimeChart.show();

                    }
                    catch(Exception ex){System.out.println("Worker exception");}
                    return null;
                }
            };
            worker.execute();
        }

        if(e.getSource()==nav_bar.getMenu(3).getItem(0)) {
            String uri = "https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html";
            showURI(uri);
        }

        if(e.getSource()==nav_bar.getMenu(4).getItem(0)) {
            String uri = "https://github.com/Jeffresh";
            showURI(uri);
        }
        
        if(e.getSource()== gui_buttons.get(buttons_names[2])) {
            worker.cancel(true);
            worker.cancel(false);
            ClassNV.stop();
        }

        if(e.getSource() == gui_buttons.get(buttons_names[0])) {

            deleteCanvasLabels();
            CanvasClassTemplate.objectNV = new ClassNV();
            CanvasClassTemplate.objectNV.plug(canvas_template);
            CanvasClassTemplate.objectNV.initializer(value);

            label_numeric_var_value = new JLabel(Double.toString(numeric_var));
            label_numeric_var_value.setFont(new Font(null, Font.PLAIN,50));
            canvas_template.add(label_numeric_var_value);

            label_string_var_value = new JLabel(string_var);
            label_string_var_value.setFont(new Font(null, Font.PLAIN,50));
            canvas_template.add(label_string_var_value);
            canvas_template.updateCanvas();

        }

        if(e.getSource()== gui_buttons.get(buttons_names[1])) {
            worker = new SwingWorker<Void, GuiTemplate>() 
            {
                @Override
                protected Void doInBackground() {
                    try{
                        deleteCanvasLabels();
                        CanvasClassTemplate.objectNV.computeClassNV((int)floor(numeric_var));
                    }
                    catch(Exception ex){System.out.println("Worker exception");}
                    return null;
                }
            };
            worker.execute();
        }

    }

    public void focusGained(FocusEvent e) {
    	//nothing
	}
	public void focusLost(FocusEvent e) {
            String nump;
            try {
                double nump_value;
                if (e.getSource() == textfield_numericVar) {
                    nump = textfield_numericVar.getText();
                    nump_value = Double.parseDouble(nump);
                    if (nump.equals("") || (nump_value < 0 || nump_value >=1000)) {
                        numeric_var = 0;
                        throw new Exception("Invalid Number");
                    }
                    numeric_var = nump_value;
                }
            }
            catch (Exception ex){
                String message = "\"Invalid Number\"\n"
                        + "Enter a number between 0 and 1000\n"
                        + " setted 0 by default";
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }

            if(e.getSource() == textfield_stringVar)
                string_var = textfield_stringVar.getText();

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

