/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataobjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author assessor
 */
public class EventClass {
    int id = -1;
    String name = "";
    int laps = 0;
    int raceOrder = 0;
    
    public EventClass(){
        
    }
    
    public EventClass(int i, String nm, int l){
        setId(i);
        setName(nm);
        setLaps(l);
    }
    
    //adds a new raceClass for the event
    public static void insertRaceClass(int eventId, String name, int laps, Connection conn){
        Statement stmt = null;
        ResultSet rset = null;
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select count(*) from event_classes where event_id = " + 
                    eventId);
            rset.next();
            
            stmt.execute("insert into event_classes (event_id, name, laps, race_order) values (" +
                    eventId + ", '" + name + "'," + laps + 
                    ", " + rset.getInt("count(*)") + ")");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //deletes the class from the event
    //this also deletes the raceEntries and laps associated with this event
    public static void deleteClass(EventClass raceClass, Connection conn){
        Statement stmt = null;
        
        try{
            stmt = conn.createStatement();
            stmt.execute("delete from event_classes where id = " + raceClass.getId());
            RaceEntry.deleteRaceClass(raceClass.getId(),conn);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public static Vector<EventClass> getRaceClasses(int eventId, Connection conn){
        Statement stmt = null;
        ResultSet rset = null;
        
        EventClass tempClass = null;
        Vector<EventClass> classes = new Vector<EventClass>();
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from event_classes where event_id = " + eventId);
            while(rset.next()){
                tempClass = new EventClass(rset.getInt("id"), rset.getString("name"), rset.getInt("laps"));
                tempClass.setOrder(rset.getInt("race_order"));
                classes.add(tempClass);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return classes;
    }
    
    public static EventClass getRaceClass(int id, Connection conn){
        EventClass tempClass = new EventClass();
        Statement stmt = null;
        ResultSet rset = null;
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from event_classes where id = " + id);
            tempClass.setId(id);
            rset.next();
            tempClass.setName(rset.getString("name"));
            tempClass.setLaps(rset.getInt("laps"));
            tempClass.setOrder(rset.getInt("race_order"));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return tempClass;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return raceOrder;
    }

    public void setOrder(int order) {
        this.raceOrder = order;
    }
    
    

}
