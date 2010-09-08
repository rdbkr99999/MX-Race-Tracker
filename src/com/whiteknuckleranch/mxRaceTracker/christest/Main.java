/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.christest;

import com.whiteknuckleranch.mxRaceTracker.dataobjects.Event;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import com.whiteknuckleranch.mxRaceTracker.tabs.EventsTab;
import com.whiteknuckleranch.mxRaceTracker.tabs.ResultsTab;
import com.whiteknuckleranch.mxRaceTracker.tabs.ScoringTab;
import com.whiteknuckleranch.mxRaceTracker.tabs.SignUpTab;

/**
 *
 * @author assessor
 */
public class Main {
    
    String schemaName = "chris_test";
    String dbHost = "localhost";
    String dbUser = "root";
    String dbPassword = "Tiw0S,wsIdw?";
    //String dbPassword = "";
    
    Event setEvent = null;
    EventClass setClass = null;

    JFrame mainFrame = null;
    JPanel mainPanel = null;
    JPanel topPanel = null;
    JTabbedPane mainTabbedPane = null;
    JLabel titleLabel = null;//new JLabel("White Knuckle Ranch");
    JLabel infoLabel = new JLabel("Welcome to the race manager");
    //JLabel seperatorLabel = new JLabel(new ImageIcon("C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures\\tirePattern.PNG"));
    JLabel seperatorLabel = new JLabel(" ");
    
    MainMenu mainMenu = new MainMenu(this);
    
    public Connection conn = null;
    
    Utils utils = null;
    
    EventsTab eventsTab;// = new EventsTab(this);
    SignUpTab signUpTab;// = new SignUpTab(this);
    ScoringTab scoringTab;// = new ScoringTab(this);
    public ResultsTab resultsTab;// = new ResultsTab(this);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.showGui();
    }
    
    public void showGui(){
        //gather credentials
//        dbHost = JOptionPane.showInputDialog("Database Hostname");
//        dbUser = JOptionPane.showInputDialog("Enter db user");
//        dbPassword = JOptionPane.showInputDialog("Enter db password");
        
        establishConnection();
        utils = new Utils(conn);
        eventsTab = new EventsTab(this);
        signUpTab = new SignUpTab(this);
        scoringTab = new ScoringTab(this);
        resultsTab = new ResultsTab(this);
        
        mainFrame = new JFrame("Race Monitor");
        mainPanel = new JPanel(new BorderLayout());
        mainTabbedPane = new JTabbedPane();
        
        mainTabbedPane.add(eventsTab,"Events");
        mainTabbedPane.add(signUpTab, "Sign Up");
        mainTabbedPane.add(scoringTab, "Scoring");
        mainTabbedPane.add(resultsTab, "Results");
        
        topPanel = new JPanel(new BorderLayout());
        
        //titleLabel.setFont(new Font("chiller",Font.BOLD,80));
        titleLabel = new JLabel(utils.getConfigValue("title"));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(Integer.parseInt(utils.getConfigValue("title_background"))));
        titleLabel.setFont(new Font(utils.getConfigValue("title_font"),Font.BOLD,Integer.parseInt(utils.getConfigValue("title_size"))));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        setTitleColor(Integer.parseInt(utils.getConfigValue("title_color")));
        
        //seperatorLabel.set
        
        infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.BOLD,14));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.white);
        infoLabel.setBorder(new LineBorder(Color.black));
        
        seperatorLabel.setOpaque(true);
        seperatorLabel.setBackground(Color.DARK_GRAY);
        topPanel.add(titleLabel,BorderLayout.NORTH);
        topPanel.add(infoLabel,BorderLayout.CENTER);
        topPanel.add(seperatorLabel, BorderLayout.SOUTH);
        
        //final stuff
        mainPanel.add(mainTabbedPane,BorderLayout.CENTER);
        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        
        mainFrame.setJMenuBar(mainMenu);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000,600);
        mainFrame.setVisible(true);
        
        eventsTab.setSplit(.5);
        signUpTab.setDividerLocation();
        
    }
    
    public void setTitle(String str, Font font){
        titleLabel.setText(str);
        titleLabel.setFont(font);
        utils.setConfigValue("title", str);
        utils.setConfigValue("title_size", String.valueOf(font.getSize()));
        utils.setConfigValue("title_font", font.getName());
        titleLabel.updateUI();
    }
    
    public void setTitleColor(int color){
        Color newColor = new Color(color);
        titleLabel.setForeground(newColor);
        utils.setConfigValue("title_color", String.valueOf(color));
        titleLabel.updateUI();
    }
    
    public void setTitleBackgroundColor(int color){
        Color newColor = new Color(color);
        titleLabel.setBackground(newColor);
        utils.setConfigValue("title_background", String.valueOf(color));
        titleLabel.updateUI();
    }
    
    public Font getTitleFont(){
        return titleLabel.getFont();
    }
    
    public String getTitle(){
        return titleLabel.getText();
    }
    
    public JLabel getTitleLabel(){
        return titleLabel;
    }
    
    //makes sure the database is connected, this is why i share this class everywhere
    public boolean establishConnection(){
        if(conn == null){
            try{
//                String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//                String dbName = StaticUtils.dbName;
//                String connectionURL = "jdbc:derby:" + dbName;
                Class.forName("org.gjt.mm.mysql.Driver");
                System.out.println("jdbc:mysql://" + dbHost + "/" + schemaName + dbUser + dbPassword);
                conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + schemaName,dbUser,dbPassword);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unable to connect to the database");
                e.printStackTrace();
                return false;
            }
        }
        //good return
        return true;
    }

    public EventClass getRaceClass() {
        return setClass;
    }

    public void setRaceClass(EventClass setClass) {
        this.setClass = setClass;
    }

    public Event getEvent() {
        return setEvent;
    }

    public void setEvent(Event setEvent) {
        this.setEvent = setEvent;
        infoLabel.setText(setEvent.getName() + ", " + setEvent.getDate());
        signUpTab.loadClasses();
        scoringTab.loadAllClasses();
        resultsTab.updateResults();
    }

    public void exportHtml(){
        setEvent.getName();
        Vector<EventClass> classes = new Vector<EventClass>();
        classes.addAll(EventClass.getRaceClasses(setEvent.getId(),conn));
        for(int i=0; i<classes.size(); i++){
            
        }
    }
    
            

}
