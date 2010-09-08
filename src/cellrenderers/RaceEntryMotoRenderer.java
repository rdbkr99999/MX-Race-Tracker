/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cellrenderers;

import dataobjects.RaceEntry;
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
        int moto = 0;
        int position = 0;
        int laps = 0;
        
        ImageIcon greenFlag = new ImageIcon("C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures\\green.PNG");
        ImageIcon whiteFlag = new ImageIcon("C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures\\white.PNG");
        ImageIcon checkerFlag = new ImageIcon("C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures\\checker.PNG");
    
        public RaceEntryMotoRenderer(int moto){
            this.moto = moto;
        }
    
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof RaceEntry){
            RaceEntry raceEntry = (RaceEntry)value;
            
            if(moto == 0){
                position = raceEntry.getMoto1Place();
                laps = raceEntry.getMoto1Laps();
                if(laps >= raceEntry.getEventClass().getLaps() - 1){
                    setIcon(checkerFlag);
                }else if(laps == raceEntry.getEventClass().getLaps() - 2){
                    setIcon(whiteFlag);
                }else{
                    setIcon(greenFlag);
                }
                setText(position + " - " + laps + " " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getBikeMfg() + " " +
                    raceEntry.getNumber());
            }else if(moto == 1){
                position = raceEntry.getMoto2Place();
                laps = raceEntry.getMoto2Laps();
                if(laps >= raceEntry.getEventClass().getLaps() - 1){
                    setIcon(checkerFlag);
                }else if(laps == raceEntry.getEventClass().getLaps() - 2){
                    setIcon(whiteFlag);
                }else{
                    setIcon(greenFlag);
                }
                setText(position + " - " + laps + " " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getBikeMfg() + " " +
                    raceEntry.getNumber());
            }else{
                position = raceEntry.getFinalPlace();
                setText(position + " - " + raceEntry.getRacer().getLastName() + ", " +
                    raceEntry.getRacer().getFirstName() + " - " +
                    raceEntry.getBikeMfg() + " " +
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
