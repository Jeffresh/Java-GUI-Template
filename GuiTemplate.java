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
    private static String[] buttons_names;
    private static Map<String, JButton> gui_buttons = new HashMap<String, JButton>();
    public static Map<String, String> textfields_and_labels = new HashMap<>();
    private static JComboBox<String>  generator_list_combo_box;
    private static String[] comobox_options = {"Option 1", "Option 2", "Option 3", "Option 4",};
    private static String combobox_value = "Option 1";



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

    private Map<String, JButton> createButtons(String[] button_names){

        Map<String, JButton> buttons_dict = new HashMap<String, JButton>();
        JButton button;

        for (String name: button_names) {
            button = new JButton(name);
            button.addActionListener(this);
            buttons_dict.put(name, button);
        }

        return buttons_dict;
    }

    private JPanel createButtonsPane(){

        gui_buttons = createButtons(buttons_names);
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

    private Object[] createTextFieldsAndLabels(Map<String, String> texts_labels){
        JLabel[] labels = new JLabel[texts_labels.size()];
        JTextField[] textFields = new JTextField[texts_labels.size()];
        int index = 0;

        for(Map.Entry<String, String> text_label: texts_labels.entrySet()){
            textFields[index] = new JTextField();
            textFields[index].setText(text_label.getValue());
            textFields[index].addFocusListener(this);
            labels[index] = new JLabel(text_label.getKey());
            labels[index].setLabelFor(textFields[index]);
            index++;
        }

        return new Object[]{labels, textFields};
    }

    private static JTextField[] input_variables_textfields;
    private static JLabel [] input_variables_labels;
    private static JLabel [] combobox_labels= {new JLabel("ComboBox Options")};

    private static void initializeInputTextFieldsAndLabels(){
        textfields_and_labels.put("Numeric Variable: ", "30");
        textfields_and_labels.put("String Variable: ", "Hello World");
        combobox_labels[0].setLabelFor(generator_list_combo_box);
    }
    private static void initializeButtonNames(){
        buttons_names = new String[]{"Initialize", "Start", "Stop"};
    }

    private JSplitPane createGuiPanels() {


        Object[]  labels_and_textfields_list = createTextFieldsAndLabels(textfields_and_labels);

        generator_list_combo_box = new JComboBox<>(comobox_options);
        generator_list_combo_box.addFocusListener(this);

        JComboBox[] combo_box_list = {generator_list_combo_box};

        JPanel input_variables_pane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        input_variables_pane.setLayout(gridbag);
        input_variables_pane.setPreferredSize(new Dimension(100, 900));
        input_variables_pane.setMinimumSize(new Dimension(100, 900));

        input_variables_labels = (JLabel[]) labels_and_textfields_list[0];
        input_variables_textfields = (JTextField[]) labels_and_textfields_list[1];

        addLabelTextRows(input_variables_labels,input_variables_textfields, combobox_labels, combo_box_list, input_variables_pane);

        input_variables_pane.setBorder(
                                   BorderFactory.createCompoundBorder(
                                                                      BorderFactory.createTitledBorder("Variables"),
                                                                      BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttons_pane = createButtonsPane();

        JSplitPane control_center_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              input_variables_pane,
                                              buttons_pane);
        control_center_pane.setMaximumSize(new Dimension(800,800));
        control_center_pane.setMinimumSize(new Dimension(800,800));
        input_variables_pane.setMaximumSize(new Dimension(800,800));
        input_variables_pane.setMinimumSize(new Dimension(800,800));

        control_center_pane.setOneTouchExpandable(true);

        return control_center_pane;
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, JLabel[] combobox_labels, JComboBox<String>[] combo_box_list,
                                  Container container){

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;
        int num_labels_combobox = combobox_labels.length;

        for (int i = 0; i < numLabels; i++){

        	labels[i].setFont(new Font(null, Font.PLAIN,20));
        	textFields[i].setFont(new Font(null, Font.PLAIN,20));
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 1.0;                       //reset to default
            container.add(labels[i], c);
 
            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1.0;
            textFields[i].setColumns(3);
            container.add(textFields[i], c);
        }

        for(int i =0; i < num_labels_combobox; i ++) {
            GuiTemplate.combobox_labels[i].setFont(new Font(null, Font.PLAIN,20));
            combo_box_list[i].setFont(new Font(null, Font.PLAIN,20));
            ((JLabel)combo_box_list[i].getRenderer()).setHorizontalAlignment(JLabel.CENTER);
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 1.0;                       //reset to default
            container.add(GuiTemplate.combobox_labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1.0;
            container.add(combo_box_list[i], c);
        }

    }

    private static  void createAndShowGUI(){

        chooseInputVariables(1,1,2);
        initializeButtonNames();
        initializeInputTextFieldsAndLabels();

        JFrame frame = new JFrame("Generic-Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setJMenuBar(new GuiTemplate().createNavBar());

        int xMax = 1000;
        int yMax = 1000;
        canvas_template = new CanvasClassTemplate(xMax, yMax);
        canvas_template.setPreferredSize(new Dimension(1000, 1000));

        JSplitPane buttons = new GuiTemplate().createGuiPanels();
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

    private static void chooseInputVariables(int n_string_variables, int n_numeric_variables, int n_label_variables){
        input_numeric_variables = new Double[n_numeric_variables];
        input_string_variables = new String[n_string_variables];
        input_label_variables = new JLabel[n_label_variables];
    }

    private static String[] input_string_variables;
    private static Double[] input_numeric_variables;
    private static JLabel[] input_label_variables;

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

        if(label_numeric_var_value != null) canvas_template.remove(label_numeric_var_value);
        if(label_string_var_value != null) canvas_template.remove(label_string_var_value);
    }

    public void actionPerformed(@NotNull ActionEvent e) {

        if(e.getSource() == nav_bar.getMenu(0).getItem(0)) {
//      frame.remove(window);
            value = 2;
            deleteCanvasLabels(input_variables_labels);
            CanvasClassTemplate.task.initializer(value);
            canvas_template.updateCanvas();
        }

        if(e.getSource() == nav_bar.getMenu(0).getItem(1)) {
            value = 3;
            deleteCanvasLabels(input_variables_labels);
            CanvasClassTemplate.task.initializer(value);
            canvas_template.updateCanvas();
        }

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
            TaskTemplate.stop();
        }

        if(e.getSource() == gui_buttons.get(buttons_names[0])) {

            deleteCanvasLabels(input_variables_labels);
            CanvasClassTemplate.task = new TaskTemplate();
            CanvasClassTemplate.task.plug(canvas_template);
            CanvasClassTemplate.task.initializer(value);

            label_numeric_var_value = new JLabel(input_variables_textfields[0].getText());
            label_numeric_var_value.setFont(new Font(null, Font.PLAIN,50));
            canvas_template.add(label_numeric_var_value);

            label_string_var_value = new JLabel(input_variables_textfields[1].getText());
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
                        deleteCanvasLabels(input_variables_labels);
                        CanvasClassTemplate.task.computeTask((int)floor(numeric_var));
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

        if(e.getSource() == input_variables_textfields[0]) {
            string_var = input_variables_textfields[0].getText();
            input_variables_textfields[0].setText(string_var);
        }

            try {
                double nump_value;
                if (e.getSource() == input_variables_textfields[1]) {
                    nump = input_variables_textfields[1].getText();
                    nump_value = Double.parseDouble(nump);
                    if (nump.equals("") || (nump_value < 0 || nump_value >=1000)) {
                        numeric_var = 0;
                        throw new Exception("Invalid Number");
                    }
                    numeric_var = nump_value;
                    input_variables_textfields[1].setText(nump);
                }
            }
            catch (Exception ex){
                String message = "\"Invalid Number\"\n"
                        + "Enter a number between 0 and 1000\n"
                        + " setted 0 by default";
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }
        if(e.getSource() == generator_list_combo_box) {
            JComboBox<String> cb = (JComboBox<String>)e.getSource();
            String op = (String)cb.getSelectedItem();
            assert op != null;
            combobox_value = op;
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

