/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.dataobjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author assessor
 */
public class Event {
    int id = -1;
    String name = "";
    String date = "";
    
    public static Vector<Event> getAllEvents(Connection conn){
        Statement stmt = null;
        ResultSet rs = null;
        
        Event evt = null;
        Vector<Event> eventVector = new Vector<Event>();
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from event order by date desc");
            while(rs.next()){
                evt = new Event();
                evt.setId(rs.getInt("id"));
                evt.setName(rs.getString("name"));
                evt.setDate(rs.getDate("date").toString());

                eventVector.add(evt);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return eventVector;
    }
    
    public static void deleteEvent(Event evt, Connection conn){
        Statement stmt = null;
        
        try{
            stmt = conn.createStatement();
            stmt.execute("delete from event where id = " + evt.getId());
            
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    public String getInsertSql(){
        String rtn = "insert into event (name, date) values ('" + 
                getName() + "', '" +
                getDate() + "')";
        
        return rtn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
    
    

}
