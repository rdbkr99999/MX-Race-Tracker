/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package christest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

/**
 *
 * @author assessor
 */
public class MainMenu extends JMenuBar{
    Main parent = null;
    JMenu fileMenu = new JMenu("File");
    JMenu configMenu = new JMenu("Configure");
    
    JMenuItem exportItem = new JMenuItem(new AbstractAction("Export HTML") {
        public void actionPerformed(ActionEvent actionEvent) {
            parent.exportHtml();
        }
    });
    
    JMenuItem exitItem = new JMenuItem(new AbstractAction("Exit") {

        public void actionPerformed(ActionEvent e) {
            if(JOptionPane.showConfirmDialog(null, "Do you wish to exit the system?","Exit",JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    });
    
    JMenu titleMenu = new JMenu("Title");
    
    JMenuItem setTitleTextItem = new JMenuItem(new AbstractAction("Set Text") {

        public void actionPerformed(ActionEvent e) {
            String title = JOptionPane.showInputDialog("Enter title");
            parent.setTitle(title, parent.getTitleFont());
        }
    });
    
    JMenuItem increaseTitleSizeItem = new JMenuItem(new AbstractAction("Increase Size") {

        public void actionPerformed(ActionEvent e) {
            int size = parent.getTitleFont().getSize();
            size += 5;
            Font newFont = new Font(parent.getTitleFont().getName(),parent.getTitleFont().getStyle(),size);
            parent.setTitle(parent.getTitle(), newFont);
        }
    });
    
    JMenuItem decreaseTitleSizeItem = new JMenuItem(new AbstractAction("Decrease Size") {

        public void actionPerformed(ActionEvent e) {
            int size = parent.getTitleFont().getSize();
            size -= 5;
            Font newFont = new Font(parent.getTitleFont().getName(),parent.getTitleFont().getStyle(),size);
            parent.setTitle(parent.getTitle(), newFont);
        }
    });
    
    JMenuItem setTitleColorItem = new JMenuItem(new AbstractAction("Set Color") {

        public void actionPerformed(ActionEvent e) {
            Color newColor = JColorChooser.showDialog(parent.mainFrame, "Choose Color", Color.black);
            parent.setTitleColor(newColor.getRGB());
        }
    });
    
    JMenuItem setTitleItem = new JMenuItem(new AbstractAction("Set Title") {

        public void actionPerformed(ActionEvent e) {
            displayTitleFrame();
        }
    });

    public MainMenu(Main parent){
        super();
        this.parent = parent;
        
        fileMenu.add(exitItem);
        
        titleMenu.add(setTitleItem);
        //titleMenu.add(setTitleTextItem);
        //titleMenu.add(setTitleColorItem);
        //titleMenu.add(increaseTitleSizeItem);
        //titleMenu.add(decreaseTitleSizeItem);
        
        configMenu.add(titleMenu);
        
        add(fileMenu);
        add(configMenu);
        
    }
    
    public void displayTitleFrame(){
        
        
        //this is the title configuration frame
        //yes, its ugly
        //it gets the label from the parent, then updates all the changes
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envFonts[] = ge.getAvailableFontFamilyNames();
        JFrame fontFrame = new JFrame("Choose Font");
        JPanel mainPanel = new JPanel(new BorderLayout());
        final JTextField textField = new JTextField();
        final JColorChooser foregroundColorChooser = new JColorChooser();
        final JColorChooser backgroundColorChooser = new JColorChooser();
        final Vector<Integer> fontSizeVector = new Vector<Integer>();
        final JComboBox fontSizeCombo = new JComboBox(fontSizeVector);
        final JComboBox fontCombo = new JComboBox(envFonts);
        JPanel testLabelPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridLayout(4,1));
        JPanel foregroundPanel = new JPanel(new BorderLayout());
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        JPanel mainMiddlePanel = new JPanel(new GridLayout(1,2));
        
        final JLabel parentLabel = new JLabel();
        parentLabel.setText(parent.getTitleLabel().getText());
        parentLabel.setFont(parent.getTitleFont());
        parentLabel.setForeground(parent.getTitleLabel().getForeground());
        parentLabel.setBackground(parent.getTitleLabel().getBackground());
        parentLabel.setOpaque(true);
        
        //trusty save button, can't forget that
        JButton saveButton = new JButton(new AbstractAction("Save") {

            public void actionPerformed(ActionEvent e) {
                parent.setTitle(parentLabel.getText(), parentLabel.getFont());
                parent.setTitleColor(parentLabel.getForeground().getRGB());
                parent.setTitleBackgroundColor(parentLabel.getBackground().getRGB());
            }
        });
        
        //loads font sizes
        for(int i=2;i<500;i+=2){
            fontSizeVector.add(i);
        }
        
        //this builds the left panel, which holds the non-color choices
        leftPanel.add(textField);
        leftPanel.add(fontSizeCombo);
        leftPanel.add(fontCombo);
        leftPanel.add(saveButton);
        leftPanel.setBorder(new TitledBorder("Configuration"));
        
        //this puts the preview label on the south part of the panel
        testLabelPanel.add(parentLabel,BorderLayout.CENTER);
        testLabelPanel.setBorder(new TitledBorder("Preview"));
        
        //this puts the color choosers on their panels
        foregroundPanel.add(foregroundColorChooser,BorderLayout.CENTER);
        backgroundPanel.add(backgroundColorChooser,BorderLayout.CENTER);
        foregroundPanel.setBorder(new TitledBorder("Set Foreground Color"));
        backgroundPanel.setBorder(new TitledBorder("Set Background Color"));
                
        //this is the meat of the panel, a grid layout for the main choices
        //mainMiddlePanel.add(leftPanel);
        mainMiddlePanel.add(foregroundPanel);
        mainMiddlePanel.add(backgroundPanel);
        
        //this puts the sub panels onthe main panel
        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(mainMiddlePanel,BorderLayout.CENTER);
        mainPanel.add(testLabelPanel,BorderLayout.SOUTH);
        
        fontFrame.getContentPane().add(mainPanel,BorderLayout.CENTER);
        
        //key listener for typing changes
        textField.setText(parentLabel.getText());
        textField.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {
                parentLabel.setText(textField.getText());
            }
        });
        
        //listens for font size changes
        fontSizeCombo.setSelectedItem(parentLabel.getFont().getSize());
        fontSizeCombo.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                parentLabel.setFont(new Font(fontCombo.getSelectedItem().toString(),Font.BOLD,(Integer)fontSizeCombo.getSelectedItem()));
            }
        });
        
        //sets up the foreground color chooser
        foregroundColorChooser.setColor(parentLabel.getForeground());
        foregroundColorChooser.setPreviewPanel(new JPanel());
        foregroundColorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                parentLabel.setForeground(foregroundColorChooser.getColor());
            }
        });
        
        //sets up the background color chooser
        backgroundColorChooser.setColor(parentLabel.getBackground());
        backgroundColorChooser.setPreviewPanel(new JPanel());
        backgroundColorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                parentLabel.setBackground(backgroundColorChooser.getColor());
            }
        });
        
        //sets up the font listener
        fontCombo.setSelectedItem(parentLabel.getFont().getFontName());
        fontCombo.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                parentLabel.setFont(new Font(fontCombo.getSelectedItem().toString(),Font.BOLD,(Integer)fontSizeCombo.getSelectedItem()));
            }
        });
        
        
        //display the panel
        fontFrame.setSize(1000,500);
        fontFrame.setVisible(true);
        fontFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    
    
}
