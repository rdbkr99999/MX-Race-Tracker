/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.cellrenderers;

import com.whiteknuckleranch.mxRaceTracker.dataobjects.RaceEntry;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author assessor
 */
public class RaceEntryMotoRenderer extends JLabel implements ListCellRenderer {
        private int moto = 0;
        private int position = 0;
        private int laps = 0;

        
        private ImageIcon greenFlag = null;
        private ImageIcon whiteFlag = null;
        private ImageIcon checkerFlag = null;
    
        public RaceEntryMotoRenderer(int moto){
            this.moto = moto;
            
            greenFlag = new ImageIcon(getClass().getResource("/green.PNG"));
            whiteFlag = new ImageIcon(getClass().getResource("/white.PNG"));
            checkerFlag = new ImageIcon(getClass().getResource("/checker.PNG"));
        }
    
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof RaceEntry){
            RaceEntry raceEntry = (RaceEntry)value;
            
            if(moto == 0){
                position = raceEntry.getMoto1Place();
                laps = raceEntry.getMoto1Laps();
                if(laps >= raceEntry.getEventClass().getLaps()){
                    setIcon(checkerFlag);
                }else if(laps == raceEntry.getEventClass().getLaps() - 1){
                    setIcon(whiteFlag);
                }else{
                    setIcon(greenFlag);
                }
                setText(position + " - " + laps + " " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getNumber());
            }else if(moto == 1){
                position = raceEntry.getMoto2Place();
                laps = raceEntry.getMoto2Laps();
                if(laps >= raceEntry.getEventClass().getLaps()){
                    setIcon(checkerFlag);
                }else if(laps == raceEntry.getEventClass().getLaps() - 1){
                    setIcon(whiteFlag);
                }else{
                    setIcon(greenFlag);
                }
                setText(position + " - " + laps + " " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getNumber());
            }else{
                position = raceEntry.getFinalPlace();
                setText(position + " - " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getNumber());
            }
            
            
            
        }
        
        setOpaque(true);
        
        if(isSelected){
            setBackground(Color.lightGray);
        }else{
            setBackground(Color.white);
        }
        
        return this;
    }

}
