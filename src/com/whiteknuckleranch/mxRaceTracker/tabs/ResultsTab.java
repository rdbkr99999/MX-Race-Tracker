/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.tabs;

import com.whiteknuckleranch.mxRaceTracker.christest.Main;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.Event;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.EventClass;
import com.whiteknuckleranch.mxRaceTracker.dataobjects.RaceEntry;
import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
        try{
            mainArea.setText("");
            classes.clear();
            entries.clear();

            //set up writing to a file
            FileWriter writer = new FileWriter("c:\\temp\\all.html");
            BufferedWriter out = new BufferedWriter(writer);
            
            FileWriter resultsWriter = new FileWriter("c:\\temp\\results.html");
            BufferedWriter resultsOut = new BufferedWriter(resultsWriter);
            
            FileWriter specWriter = null;
            BufferedWriter specOut = null;


            //get the event selected on the main page
            event = parent.getEvent();

            //get all the classes in the event
            classes.addAll(EventClass.getRaceClasses(event.getId(), parent.conn));

            //set the title
            mainArea.append("<h1>" + event.getName() + ", " + event.getDate() + "</h1>\n");
            out.write("<html><head><title>" + event.getName() + "</title></head><body><img src=\"./WKR_Logo_Long.png\"><h1>" + 
                    event.getName() + ", " + event.getDate() + "</h1>");
            
            resultsOut.write("<html><head><title>" + event.getName() + "</title></head><body><img src=\"./WKR_Logo_Long.png\"><h1>" + 
                    event.getName() + ", " + event.getDate() + "</h1>");
            resultsOut.write("<a href=\"file:///c:/temp/all.html\">All</br>");
        	

            //loop throough the classes and get the results
            for(int i=0; i<classes.size(); i++){
            	resultsOut.write("<a href=\"file:///c:/temp/"+ classes.get(i).getName().replace("/", "-") + ".html\">" + classes.get(i).getName() + "</br>");
            	specWriter = new FileWriter("c:\\temp\\" + classes.get(i).getName().replace("/", "-") + ".html");
            	specOut = new BufferedWriter(specWriter);
            	
            	 specOut.write("<html><head><title>" + event.getName() + "</title></head><body><img src=\"./WKR_Logo_Long.png\"><h1>" + 
                         event.getName() + ", " + event.getDate() + "</h1>");
            	
                mainArea.append("<table border=1 width=100%>\n\t<tr>\n\t\t<td colspan=5 align=center><b>" + classes.get(i).getName() + "</b></td>\n\t</tr>\n");
                mainArea.append("\t<tr>\n\t\t<td width=40%>Name</td>\n\t\t<td width=10%>Number</td>\n" +
                        "\t\t<td width=10%>Moto 1</td>\n\t\t<td width=10%>Moto 2</td>\n\t\t<td width=10%>Final</td>\n\t</tr>\n");

                out.write("<table border=1 width=100%>\n\t<tr>\n\t\t<td colspan=5 align=center><b>" + classes.get(i).getName() + "</b></td>\n\t</tr>\n");
                out.write("\t<tr>\n\t\t<td width=40%>Name</td>\n\t\t<td width=10%>Number</td>\n" +
                        "\t\t<td width=10%>Moto 1</td>\n\t\t<td width=10%>Moto 2</td>\n\t\t<td width=10%>Final</td>\n\t</tr>\n");
                
                specOut.write("<table border=1 width=100%>\n\t<tr>\n\t\t<td colspan=5 align=center><b>" + classes.get(i).getName() + "</b></td>\n\t</tr>\n");
                specOut.write("\t<tr>\n\t\t<td width=40%>Name</td>\n\t\t<td width=10%>Number</td>\n" +
                        "\t\t<td width=10%>Moto 1</td>\n\t\t<td width=10%>Moto 2</td>\n\t\t<td width=10%>Final</td>\n\t</tr>\n");
                
                entries.clear();
                entries.addAll(RaceEntry.getResults(classes.get(i).getId(), 2, parent.conn));
                for(int j=0; j<entries.size(); j++){
                    mainArea.append("\t<tr>\n");
                    mainArea.append("\t\t<td>" + entries.get(j).getRacer().getLastName() + ", " + entries.get(j).getRacer().getFirstName() + "</td>\n");
                    mainArea.append("\t\t<td>" + entries.get(j).getNumber() + "</td>\n");
                    mainArea.append("\t\t<td>" + entries.get(j).getMoto1Place() + "</td>\n");
                    mainArea.append("\t\t<td>" + entries.get(j).getMoto2Place() + "</td>\n");
                    mainArea.append("\t\t<td>" + entries.get(j).getFinalPlace() + "</td>\n");
                    mainArea.append("\t</tr>\n");

                    out.write("\t<tr>\n");
                    out.write("\t\t<td>" + entries.get(j).getRacer().getLastName() + ", " + entries.get(j).getRacer().getFirstName() + "</td>\n");
                    out.write("\t\t<td>" + entries.get(j).getNumber() + "</td>\n");
                    out.write("\t\t<td>" + entries.get(j).getMoto1Place() + "</td>\n");
                    out.write("\t\t<td>" + entries.get(j).getMoto2Place() + "</td>\n");
                    out.write("\t\t<td>" + entries.get(j).getFinalPlace() + "</td>\n");
                    out.write("\t</tr>\n");
                    
                    specOut.write("\t<tr>\n");
                    specOut.write("\t\t<td>" + entries.get(j).getRacer().getLastName() + ", " + entries.get(j).getRacer().getFirstName() + "</td>\n");
                    specOut.write("\t\t<td>" + entries.get(j).getNumber() + "</td>\n");
                    specOut.write("\t\t<td>" + entries.get(j).getMoto1Place() + "</td>\n");
                    specOut.write("\t\t<td>" + entries.get(j).getMoto2Place() + "</td>\n");
                    specOut.write("\t\t<td>" + entries.get(j).getFinalPlace() + "</td>\n");
                    specOut.write("\t</tr>\n");
                }
                mainArea.append("</table>\n<br/>\n");
                out.write("</table>\n<br/>\n");
                
                specOut.write("</table>\n<br/>\n");
                specOut.write("</html>");
                
                specOut.close();
                specWriter.close();
            }

            mainArea.setCaretPosition(0);
            out.write("</html>");
            
            resultsOut.write("</html>");

            //close the file
            out.close();
            writer.close();
            
            resultsOut.close();
            resultsWriter.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
