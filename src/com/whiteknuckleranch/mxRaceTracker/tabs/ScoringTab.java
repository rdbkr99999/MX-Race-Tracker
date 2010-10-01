/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.tabs;

import com.whiteknuckleranch.mxRaceTracker.cellrenderers.ClassListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.cellrenderers.RaceEntryListCellRenderer;
import com.whiteknuckleranch.mxRaceTracker.cellrenderers.RaceEntryMotoRenderer;
import com.whiteknuckleranch.mxRaceTracker.christest.Main;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.RaceEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author assessor
 */
public class ScoringTab extends JPanel{
    Main parent = null;
    
    int raceClassId = -1;
    
    JPanel classPanel = new JPanel(new BorderLayout());
    JPanel moto1Panel = new JPanel(new BorderLayout());
    JPanel moto2Panel = new JPanel(new BorderLayout());
    JPanel moto1ButtonPanel = new JPanel(new BorderLayout());
    JPanel moto2ButtonPanel = new JPanel(new BorderLayout());
    JPanel moto1InputPanel = new JPanel(new BorderLayout());
    JPanel moto2InputPanel = new JPanel(new BorderLayout());
    JPanel finalPanel = new JPanel(new BorderLayout());
    
    Vector<EventClass> classVector = new Vector<EventClass>();
    Vector<RaceEntry> racerVector = new Vector<RaceEntry>();
    Vector<RaceEntry> moto1Vector = new Vector<RaceEntry>();
    Vector<RaceEntry> moto2Vector = new Vector<RaceEntry>();
    Vector<RaceEntry> finalVector = new Vector<RaceEntry>();
    
    Vector[] scoringArray = {moto1Vector,moto2Vector,finalVector};
    
    
    JComboBox classCombo = new JComboBox(classVector);
    JList racerList = new JList(racerVector);
    //JLabel moto1Label = new JLabel("Moto1");
    //JLabel moto2Label = new JLabel("Moto2");
    //JLabel finalLabel = new JLabel("Final");
    JList moto1List = new JList(moto1Vector);
    JList moto2List = new JList(moto2Vector);
    JList finalList = new JList(finalVector);
    
    JList[] listArray = {moto1List,moto2List,finalList};
    
    JTextField moto1Field = new JTextField();
    JTextField moto2Field = new JTextField();
    
    HashMap<String,RaceEntry> racerMap = new HashMap<String,RaceEntry>();
    
    
    public ScoringTab(Main pnt){
        super();
        parent = pnt;
        setLayout(new GridLayout(1,4));
        
        classCombo.setRenderer(new ClassListCellRenderer());
        classCombo.addActionListener(comboListener);
        classPanel.add(classCombo,BorderLayout.NORTH);
        racerList.setCellRenderer(new RaceEntryListCellRenderer());
        classPanel.add(new JScrollPane(racerList),BorderLayout.CENTER);
        classPanel.setBorder(new TitledBorder("Class"));
        
        //moto1Panel.add(moto1Label,BorderLayout.NORTH);
        moto1List.setCellRenderer(new RaceEntryMotoRenderer(0));
        moto1Panel.add(new JScrollPane(moto1List),BorderLayout.CENTER);
        moto1InputPanel.add(moto1Field,BorderLayout.CENTER);
        moto1Panel.add(moto1InputPanel,BorderLayout.SOUTH);
        moto1Panel.setBorder(new TitledBorder("Moto1"));
        moto1InputPanel.setBorder(new TitledBorder("Score Moto 1"));
        moto1Field.addKeyListener(moto1Listener);
        moto1Field.setBackground(Color.lightGray);
        moto1Field.setOpaque(true);
        
        //moto2Panel.add(moto2Label,BorderLayout.NORTH);
        
        moto2List.setCellRenderer(new RaceEntryMotoRenderer(1));
        moto2Panel.add(new JScrollPane(moto2List),BorderLayout.CENTER);
        moto2InputPanel.add(moto2Field,BorderLayout.SOUTH);
        moto2Panel.add(moto2InputPanel,BorderLayout.SOUTH);
        moto2Panel.setBorder(new TitledBorder("Moto2"));
        moto2InputPanel.setBorder(new TitledBorder("Score Moto 2"));
        moto2Field.addKeyListener(moto2Listener);
        moto2Field.setBackground(Color.lightGray);
        moto2Field.setOpaque(true);
        
        finalList.setCellRenderer(new RaceEntryMotoRenderer(2));
        //finalPanel.add(finalLabel,BorderLayout.NORTH);
        finalPanel.add(new JScrollPane(finalList),BorderLayout.CENTER);
        finalPanel.setBorder(new TitledBorder("Final"));
        
        add(classPanel);
        add(moto1Panel);
        add(moto2Panel);
        add(finalPanel);
    }
    
    public void updateMotoResults(int moto){
        scoringArray[moto].clear();
        scoringArray[moto].addAll(RaceEntry.getResults(raceClassId, moto,parent.conn));
        listArray[moto].updateUI();
        parent.resultsTab.updateResults();
    }
    
    KeyListener moto1Listener = new KeyListener() {

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            try{
                String[] fieldParts = new String[2];
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(moto1Field.getText().contains("-")){
                        fieldParts = moto1Field.getText().split("-");
                    }else{
                        fieldParts[0] = moto1Field.getText();
                    }
                    RaceEntry re = (RaceEntry)racerMap.get(fieldParts[0]);
                    //System.out.println(re.getRacer().getLastName());
                    if(fieldParts[1] != null){
                        re.subtractLap(0,parent.conn);
                    }else{
                        re.addLap(0,parent.conn);
                    }
                    moto1Field.setText("");

                    updateMotoResults(0);
                    updateMotoResults(2);
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        public void keyReleased(KeyEvent e) {}
    };
    
    KeyListener moto2Listener = new KeyListener() {

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            try{
                String[] fieldParts = new String[2];
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(moto2Field.getText().contains("-")){
                        fieldParts = moto2Field.getText().split("-");
                    }else{
                        fieldParts[0] = moto2Field.getText();
                    }
                    RaceEntry re = (RaceEntry)racerMap.get(fieldParts[0]);
                    //System.out.println(re.getRacer().getLastName());
                    if(fieldParts[1] != null){
                        re.subtractLap(1,parent.conn);
                    }else{
                        re.addLap(1,parent.conn);
                    }
                    moto2Field.setText("");

                    updateMotoResults(1);
                    updateMotoResults(2);
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        public void keyReleased(KeyEvent e) { }
    };
    
    ActionListener comboListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            raceClassId = ((EventClass)classCombo.getSelectedItem()).getId();
            loadRacers();
        }
    };
    
    public void loadAllClasses(){
        classVector.clear();
        classVector.add(new EventClass(-1, " ",0));
        classVector.addAll(EventClass.getRaceClasses(parent.getEvent().getId(), parent.conn));
        classCombo.setSelectedIndex(0);
        classCombo.updateUI();
    }
    
    public void loadRacers(){
        racerMap.clear();
        racerVector.clear();
        racerVector.addAll(RaceEntry.getAllEntries(((EventClass)classCombo.getSelectedItem()).getId(),parent.conn));
        racerList.updateUI();
        
        for(int i=0; i<racerVector.size(); i++){
            racerMap.put(racerVector.get(i).getNumber(), racerVector.get(i));
        }
        
        if(classCombo.getSelectedIndex() >= 0){
            updateMotoResults(0);
            updateMotoResults(1);
            updateMotoResults(2);
        }
    }
}
