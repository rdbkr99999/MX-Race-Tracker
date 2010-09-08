/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.tabs;

import com.whiteknuckleranch.mxRaceTracker.cellrenderers.ClassListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.cellrenderers.EventListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.christest.Main;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.Event;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author assessor
 */
public class EventsTab extends JPanel{
    
    AbstractAction addEventAction = new AbstractAction("Add Event"){

        public void actionPerformed(ActionEvent e) {
            Event evt = new Event();
            
            try{
            
                String name = JOptionPane.showInputDialog("Please enter event name");
                String input = JOptionPane.showInputDialog("Please enter event date yyyy-mm-dd");

                //basic date check
                if(input.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
                    evt.setName(name);
                    evt.setDate(input);
                    
                    if(parent.establishConnection()){
                        stmt = parent.conn.createStatement();
                        stmt.execute(evt.getInsertSql());
                    }
                    
                    //repopulate the list
                    getEvents();
                }else{
                    JOptionPane.showMessageDialog(null, "Incorrect date format.");
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        
    };
    
    AbstractAction addClassAction = new AbstractAction("Add Class"){

        public void actionPerformed(ActionEvent e) {
            try{
                if(eventList.getSelectedIndex() >= 0){
                    String name = JOptionPane.showInputDialog("Enter event name");
                    if(name.length() > 0){
                        if(parent.establishConnection()){
                            int laps = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter number of laps"));
                            EventClass.insertRaceClass(((Event)eventList.getSelectedValue()).getId(), name, laps, parent.conn);
                            getClasses();
                            getClasses();
                            parent.setEvent((Event)eventList.getSelectedValue());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Please enter a class name");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please select an event before adding classes");
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        
    };
    
    AbstractAction removeEventAction = new AbstractAction("Remove Event"){

        public void actionPerformed(ActionEvent e) {
            try{
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete this event?", "Delete",JOptionPane.YES_NO_CANCEL_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    Event evt = (Event)eventList.getSelectedValue();
                    if(parent.establishConnection()){
                        Event.deleteEvent(evt, parent.conn);
                        getEvents();
                        classVector.clear();
                        classList.updateUI();
                    }
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        
    };
    
    AbstractAction removeClassAction = new AbstractAction("Remove Class"){

        public void actionPerformed(ActionEvent e) {
            try{
                if(classList.getSelectedIndex() >= 0){
                    if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this class?", 
                            "Delete", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                        if(parent.establishConnection()){
                            EventClass.deleteClass((EventClass)classList.getSelectedValue(), parent.conn);
                            getClasses();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please select a class to delete");
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        
    };
    
    
    Main parent = null;
    
    Statement stmt = null;
    ResultSet rs = null;
    
    Vector<Event> eventVector = new Vector<Event>();
    Vector<EventClass> classVector = new Vector<EventClass>();
    
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    
    JPanel eventPanel = new JPanel(new BorderLayout());
    JPanel classPanel = new JPanel(new BorderLayout());
    JPanel eventTitlePanel = new JPanel(new BorderLayout());
    JPanel classTitlePanel = new JPanel(new BorderLayout());
    JButton eventAddButton = new JButton(addEventAction);
    JButton classAddButton = new JButton(addClassAction);
    JButton eventRemoveButton = new JButton(removeEventAction);
    JButton classRemoveButton = new JButton(removeClassAction);
    JList eventList = new JList(eventVector);
    JList classList = new JList(classVector);
    
    
    public EventsTab(Main pnt){
        super();
        parent = pnt;
        setLayout(new BorderLayout());
        
        //event side
        //eventTitlePanel.add(eventLabel,BorderLayout.NORTH);
        //eventTitlePanel.add(eventField,BorderLayout.CENTER);
        eventTitlePanel.add(eventAddButton,BorderLayout.SOUTH);
        
        eventList.setCellRenderer(new EventListCellRenderer());
        eventList.addListSelectionListener(eventListListener);
        
        eventPanel.add(eventTitlePanel,BorderLayout.NORTH);
        eventPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        eventPanel.add(eventRemoveButton, BorderLayout.SOUTH);
        eventPanel.setBorder(new TitledBorder("Events"));
        
        //class side
        //classTitlePanel.add(classLabel,BorderLayout.NORTH);
        //classTitlePanel.add(classField,BorderLayout.CENTER);
        classTitlePanel.add(classAddButton,BorderLayout.SOUTH);
        
        classList.setCellRenderer(new ClassListCellRenderer());
        
        classPanel.add(classTitlePanel,BorderLayout.NORTH);
        classPanel.add(new JScrollPane(classList),BorderLayout.CENTER);
        classPanel.add(classRemoveButton,BorderLayout.SOUTH);
        classPanel.setBorder(new TitledBorder("Classes"));
        
        splitPane.add(eventPanel);
        splitPane.add(classPanel);
        
        add(splitPane,BorderLayout.CENTER);
        
        getEvents();
        
    }
    
    public void setSplit(double split){
        splitPane.setDividerLocation(split);
    }
    
    //gets all the events
    public void getEvents(){
        try{
            eventVector.clear();
            classVector.clear();
            if(parent.establishConnection()){
                eventVector.addAll(Event.getAllEvents(parent.conn));
            }
            eventList.updateUI();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //gets all the classes
    public void getClasses(){
        Event evt = null;
        classVector.clear();
        
        try{
            if(eventList.getSelectedIndex() >= 0){
                evt = (Event)eventList.getSelectedValue();
                if(parent.establishConnection()){
                    classVector.addAll(EventClass.getRaceClasses(evt.getId(), parent.conn));
                }
                classList.updateUI();
            }
            parent.setEvent((Event)eventList.getSelectedValue());
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ListSelectionListener eventListListener = new ListSelectionListener() {

        public void valueChanged(ListSelectionEvent e) {
            getClasses();
            parent.setEvent((Event)eventList.getSelectedValue());
        }
    };
    

}
