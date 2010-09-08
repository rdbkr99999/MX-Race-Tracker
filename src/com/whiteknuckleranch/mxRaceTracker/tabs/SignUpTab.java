/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.tabs;

import com.whiteknuckleranch.mxRaceTracker.cellrenderers.ClassListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.cellrenderers.RaceEntryListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.cellrenderers.RacerListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.christest.Main;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.RaceEntry;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.Racer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author assessor
 */
public class SignUpTab extends JPanel{
    Main parent = null;
    
    Vector<Racer> racersVector = new Vector<Racer>();
    Vector<RaceEntry> selectedRacers = new Vector<RaceEntry>();
    Vector<EventClass> classVector = new Vector<EventClass>();
    
    JPanel racerPanel = new JPanel(new BorderLayout());
    JPanel assignedPanel = new JPanel(new BorderLayout());
    JPanel centerPanel = new JPanel();
    
    JPanel racerTopPanel = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());
    
    JSplitPane innerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane outerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    
    JList racerList = new JList(racersVector);
    JList assignedList = new JList(selectedRacers);
    JComboBox classCombo = new JComboBox(classVector);
    
    Statement stmt = null;
    ResultSet rset = null;
    
    
    //makes a new racer
    JButton addRacerButton = new JButton(new AbstractAction("New Racer") {
        public void actionPerformed(ActionEvent e) {
            Racer.makeNewRacer(parent.conn);
            loadAvailableRacers();
        }
    });
    
    //deletes a racer
    JButton deleteRacer = new JButton(new AbstractAction("Delete Racer") {

        public void actionPerformed(ActionEvent e) {
            if(racerList.getSelectedIndex() < 0){
                JOptionPane.showMessageDialog(null, "Please select a racer to delete");
                return;
            }

            Racer.deleteRacer((Racer)racerList.getSelectedValue(), parent.conn);
            loadAvailableRacers();
        }
    });

    //creates a new race entry into the selected class
    JButton addButton = new JButton(new AbstractAction(">") {
        public void actionPerformed(ActionEvent e) {
            if(classCombo.getSelectedIndex() <= 0 ){
                JOptionPane.showMessageDialog(null, "Please select a class to add the racer to");
                return;
            }
            
            if(racerList.getSelectedIndex() < 0 ){
                JOptionPane.showMessageDialog(null, "Please select a racer to add to the class");
                return;
            }
            
            RaceEntry.createRaceEntry((Racer)racerList.getSelectedValue(), 
                    (EventClass)classCombo.getSelectedItem(), parent.conn);
            loadSelectedRacers();
        }
    });
    
    JButton removeButton = new JButton(new AbstractAction("<") {

        public void actionPerformed(ActionEvent e) {
            RaceEntry.deleteRaceEntry((RaceEntry)assignedList.getSelectedValue(), parent.conn);
            loadSelectedRacers();
        }
    });
    
    AbstractAction classComboAction = new AbstractAction() {

        public void actionPerformed(ActionEvent e) {
            loadSelectedRacers();
        }
    };
    
    public SignUpTab(Main pnt){
        super();
        parent = pnt;
        setLayout(new BorderLayout());
        
        //top panel
        classCombo.setRenderer(new ClassListCellRenderer());
        classCombo.addActionListener(classComboAction);
        topPanel.add(classCombo,BorderLayout.CENTER);
        topPanel.setBorder(new TitledBorder("Classes"));
        
        //racer panel
        racerTopPanel.add(addRacerButton,BorderLayout.CENTER);
        
        racerList.setCellRenderer(new RacerListCellRenderer());
        racerPanel.add(racerTopPanel,BorderLayout.NORTH);
        racerPanel.add(new JScrollPane(racerList),BorderLayout.CENTER);
        racerPanel.add(deleteRacer,BorderLayout.SOUTH);
        racerPanel.setBorder(new TitledBorder("Racers"));
        
        //assigned panel
        assignedList.setCellRenderer(new RaceEntryListCellRenderer());
        assignedPanel.add(new JScrollPane(assignedList));
        assignedPanel.setBorder(new TitledBorder("Assigned Racers"));
        
        //center panel
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        addButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        removeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        centerPanel.add(new Box.Filler(new Dimension(0,0), new Dimension(10,150), new Dimension(10,800)));
        centerPanel.add(addButton);
        centerPanel.add(removeButton);
        centerPanel.add(new Box.Filler(new Dimension(0,0), new Dimension(10,150), new Dimension(10,800)));
        
        innerSplitPane.add(racerPanel);
        innerSplitPane.add(centerPanel);
        
        outerSplitPane.add(innerSplitPane);
        outerSplitPane.add(assignedPanel);
        
        //all together
        add(topPanel,BorderLayout.NORTH);
        add(outerSplitPane,BorderLayout.CENTER);
        //add(racerPanel,BorderLayout.WEST);
        //add(centerPanel,BorderLayout.CENTER);
        //add(assignedPanel,BorderLayout.EAST);
        
        loadClasses();
        loadAvailableRacers();
        loadSelectedRacers();
    }
    
    public void loadClasses(){
        EventClass tempClass = new EventClass(-1, " ", 0);
        try{
            selectedRacers.clear();
            classVector.clear();
            
            classVector.add(tempClass);

            if(parent.getEvent() == null){
                classVector.add(new EventClass(-1,"Please select an event", 0));

            }else{
                if(parent.establishConnection()){
                    classVector.addAll(EventClass.getRaceClasses(parent.getEvent().getId(), parent.conn));
                }
            }

            classCombo.updateUI();
            classCombo.setSelectedIndex(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loadAvailableRacers(){
        racersVector.clear();
        try{
            if(parent.establishConnection()){
                racersVector.addAll(Racer.getAllRacers(parent.conn));
            }
            racerList.updateUI();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loadSelectedRacers(){
        selectedRacers.clear();
        try{
            if(classCombo.getSelectedIndex() >= 0){
                selectedRacers.addAll(RaceEntry.getAllEntries(((EventClass)classCombo.getSelectedItem()).getId(), parent.conn));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        assignedList.updateUI();
    }
    
    public void setDividerLocation(){
        try{
            outerSplitPane.setDividerLocation(.55);
            updateUI();
            repaint();
            Thread.sleep(300);
            innerSplitPane.setDividerLocation(.84);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
