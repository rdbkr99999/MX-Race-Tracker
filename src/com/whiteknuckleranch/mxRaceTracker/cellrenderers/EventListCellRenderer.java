/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.cellrenderers;

import com.whiteknuckleranch.mxRaceTracker.dataobjects.Event;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author assessor
 */
public class EventListCellRenderer extends JLabel implements ListCellRenderer{

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof Event){
            Event evt = (Event)value;
            setText(evt.getName() + ", " + evt.getDate());
            
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
