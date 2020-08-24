/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.cellrenderers;

import com.whiteknuckleranch.mxRaceTracker.dataobjects.RaceEntry;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author assessor
 */
public class RaceEntryListCellRenderer extends JLabel implements ListCellRenderer {
    
        /**
	 * 
	 */
	private static final long serialVersionUID = 7404496034401342370L;

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof RaceEntry){
            RaceEntry raceEntry = (RaceEntry)value;
            setText(raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getNumber());
            
            
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
