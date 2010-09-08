/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.cellrenderers;

import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author assessor
 */
public class ClassListCellRenderer extends JLabel implements ListCellRenderer{

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof EventClass){
            EventClass raceClass = (EventClass)value;
            if(raceClass.getId() == -1){
                setText(" ");
            }else{
                setText(raceClass.getName() + " - " + raceClass.getLaps() + " laps");
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
