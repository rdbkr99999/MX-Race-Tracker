/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabs;

import christest.Main;
import dataobjects.Event;
import dataobjects.EventClass;
import dataobjects.RaceEntry;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author assessor
 */
public class ResultsTab extends JPanel{
    Main parent = null;
    
    JTextArea mainArea = new JTextArea();
    JScrollPane mainScroll = new JScrollPane(mainArea);
    Event event = null;
    Vector<EventClass> classes = new Vector<EventClass>();
    Vector<RaceEntry> entries = new Vector<RaceEntry>();
    
    
    public ResultsTab(Main pnt){
        super();
        parent = pnt;
        setLayout(new BorderLayout());
        
        add(mainScroll,BorderLayout.CENTER);
        
    }
    
    public void updateResults(){
        mainArea.setText("");
        classes.clear();
        entries.clear();
        
        //get the event selected on the main page
        event = parent.getEvent();
        
        //get all the classes in the event
        classes.addAll(EventClass.getRaceClasses(event.getId(), parent.conn));
        
        //set the title
        mainArea.append("<h1>" + event.getName() + ", " + event.getDate() + "</h1>\n");
        
        //loop throough the classes and get the results
        for(int i=0; i<classes.size(); i++){
            mainArea.append("<table border=1 width=100%>\n\t<tr>\n\t\t<td colspan=6 align=center><b>" + classes.get(i).getName() + "</b></td>\n\t</tr>\n");
            mainArea.append("\t<tr>\n\t\t<td width=40%>Name</td>\n\t\t<td width=10%>Number</td>\n" +
                    "\t\t<td width=20%>Mfg</td>\n\t\t<td width=10%>Moto 1</td>\n\t\t<td width=10%>Moto 2</td>\n\t\t<td width=10%>Final</td>\n\t</tr>\n");
            entries.clear();
            entries.addAll(RaceEntry.getResults(classes.get(i).getId(), 2, parent.conn));
            for(int j=0; j<entries.size(); j++){
                mainArea.append("\t<tr>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getRacer().getLastName() + ", " + entries.get(j).getRacer().getFirstName() + "</td>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getNumber() + "</td>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getBikeMfg() + "</td>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getMoto1Place() + "</td>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getMoto2Place() + "</td>\n");
                mainArea.append("\t\t<td>" + entries.get(j).getFinalPlace() + "</td>\n");
                mainArea.append("\t</tr>\n");
            }
            mainArea.append("</table>\n<br/>\n");
        }
        
        mainArea.setCaretPosition(0);
        
        
    }

}
