/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataobjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author assessor
 */
public class RaceEntry {
    int id = 0;
    EventClass eventClass = null;
    Racer racer = null;
    String bikeMfg = "";
    String size = "";
    String number = "";
    int moto1Place = 0;
    int moto2Place = 0;
    int moto1Laps = 0;
    int moto2Laps = 0;
    int finalPlace = 0;
    
    //adds a lap to the database for the given moto
    public void addLap(int moto, Connection conn){
        Statement stmt = null;
        String sql = null;
        
        try{
            stmt = conn.createStatement();
            sql = "insert into laps (race_entry_id, moto, race_class_id) values ( " +
                    getId() + ", " + moto + ", " + eventClass.getId() + ")";
            stmt.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //removes a lap from a racer
    public void subtractLap(int moto, Connection conn){
        Statement stmt = null;
        String sql = null;
        
        try{
            stmt = conn.createStatement();
            sql = "delete from laps where  race_entry_id = " + getId() + 
                    " and moto = " + moto + " order by id desc limit 1";
            System.out.println(sql);
            stmt.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //this gets the final score based on the two motos
    //this will continue to change as laps are added
    public static Vector<RaceEntry> getFinalScore(int eventClassId, Connection conn){
        Vector<RaceEntry> entries = new Vector<RaceEntry>();
        Statement stmt = null;
        ResultSet rset = null;
        RaceEntry tempEntry = null;
        String sql = null;
        int position = 1;
        
        try{
            //this orders the racers by points, then moto2 position
            sql = "select race_entry_id, moto1_place, moto2_place, moto1_place + moto2_place as points from score" +
                    " where race_class_id = " + eventClassId + " " +
                    "order by points asc, moto2_place asc";
            //System.out.println(sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while(rset.next()){
                tempEntry = getEntry(rset.getInt("race_entry_id"), conn);
                tempEntry.setFinalPlace(position);
                tempEntry.setMoto1Place(rset.getInt("moto1_place"));
                tempEntry.setMoto2Place(rset.getInt("moto2_place"));
                entries.add(tempEntry);
                position++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return entries;
    }
    
    //this returns a vector of raceEntries with the results for the given moto
    public static Vector<RaceEntry> getResults(int eventClassId, int moto, Connection conn){
        //if we are requesting the final score, that gets calculated differently
        if(moto == 2){
            return getFinalScore(eventClassId,conn);
        }
        Vector<RaceEntry> entries = new Vector<RaceEntry>();
        Statement stmt = null;
        ResultSet rset = null;
        RaceEntry tempEntry = null;
        String sql = null;
        int position = 1;
        
        
        try{
            //this gets the results for a given moto in the order they run
            sql = "select race_entry_id, count(*) as laps from laps where race_class_id = " + eventClassId + 
                    " and moto = " + moto + " group by race_entry_id " +
                    "order by laps desc, max(lap_timestamp) asc";
            //System.out.println(sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while(rset.next()){
                //if(!rset.isLast()){
                    tempEntry = getEntry(rset.getInt("race_entry_id"),conn);
                    tempEntry.setLaps(moto,rset.getInt("laps"));
                    tempEntry.setPosition(moto,position);
                    entries.add(tempEntry);
                    RaceEntry.setScore(tempEntry,moto,position,conn);
                    position++;
                //}
            }
                
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return entries;
    }
    
    //sets the score into the score table
    public static void setScore(RaceEntry entry, int moto, int pos, Connection conn){
        Statement stmt = null;
        String sql = null;
        String motoStr = null;
        
        try{
            if(moto == 0){
                motoStr = "moto1_place";
            }else{
                motoStr = "moto2_place";
            }
            sql = "insert into score (race_entry_id, race_class_id," + motoStr+ ") values (" +
                    entry.getId() + ", " + entry.getEventClass().getId() + ", " + pos + ")" +
                    "on duplicate key update " + motoStr + "=" + pos;
            stmt = conn.createStatement();
            stmt.execute(sql);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //ets the laps for the moto
    public void setLaps(int m, int l){
        if(m == 0){
            setMoto1Laps(l);
        }else{
            setMoto2Laps(l);
        }
    }
    
    //sets the positino for the moto
    public void setPosition(int m, int p){
        if(m == 0){
            setMoto1Place(p);
        }else{
            setMoto2Place(p);
        }
    }
    
    //returns a specific raceEntry
    public static RaceEntry getEntry(int raceEntryId, Connection conn){
        RaceEntry tempEntry = null;
        Statement stmt = null;
        ResultSet rset = null;
        String sql = null;
        
        try{
            sql = "select * from race_entry where id = " + raceEntryId;
            //System.out.println(sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            rset.next();
             
            tempEntry = new RaceEntry();
            tempEntry.setId(raceEntryId);
            tempEntry.setEventClass(EventClass.getRaceClass(rset.getInt("event_class_id"),conn));
            tempEntry.setRacer(Racer.getRacerById(rset.getInt("racer_id"), conn));
            tempEntry.setBikeMfg(rset.getString("bike_mfg"));
            tempEntry.setSize(rset.getString("size"));
            tempEntry.setNumber(rset.getString("number"));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempEntry;
    }
    
    //gets all the entries for an eventClass
    public static Vector<RaceEntry> getAllEntries(int eventClassId, Connection conn){
        Vector<RaceEntry> entries = new Vector<RaceEntry>();
        Statement stmt = null;
        ResultSet rset = null;
        String sql = null;
        
        try{
            stmt = conn.createStatement();
            sql = "select * from race_entry where event_class_id = " + eventClassId;
            rset = stmt.executeQuery(sql);
            while(rset.next()){
                entries.add(RaceEntry.getEntry(rset.getInt("id"),conn));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return entries;
    }
    
    //creates a new race entry
    public static void createRaceEntry(Racer racer, EventClass eventClass, Connection conn){
        Statement stmt = null;
        ResultSet rset = null;
        try{
            //make sure the racer isn't already in the class
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select count(*) from race_entry where event_class_id = " +
                    eventClass.getId() + " and racer_id = " + racer.getId());
            rset.next();
            if(rset.getInt("count(*)") > 0){
                JOptionPane.showMessageDialog(null, "This racer has already been entered in this race");
                return;
            }
            
            //get the inputs for the race
            String bikeMfg = JOptionPane.showInputDialog(null,"Enter bike manufacturer");
            if(bikeMfg == null || bikeMfg.length() == 0){
                JOptionPane.showMessageDialog(null, "All fields are required");
                return;
            }
            
            String bikeSize = JOptionPane.showInputDialog(null,"Enter bike size (ie 250 2t, 450, 250f");
            if(bikeSize == null || bikeSize.length() == 0){
                JOptionPane.showMessageDialog(null, "All fields are required");
                return;
            }
            
            String number = JOptionPane.showInputDialog(null, "Enter number (make sure it doesn't conflict, add an R if necessary)");
            if(bikeMfg == null || bikeMfg.length() == 0){
                JOptionPane.showMessageDialog(null, "All fields are required");
                return;
            }
            
            //make sure the number isn't duplicated
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select count(*) from race_entry where event_class_id = " +
                    eventClass.getId() + " and number = '" + number + "'");
            rset.next();
            if(rset.getInt("count(*)") > 0){
                number = JOptionPane.showInputDialog("This number is already in use for this class, please enter a new number or discriminator (eg 199r)");
            }
            
            //insert the entry
            stmt = conn.createStatement();
            String sql = "insert into race_entry (event_class_id, racer_id, bike_mfg, size, number) values (" +
                    eventClass.getId() + ", " +
                    racer.getId() + ", '" +
                    bikeMfg + "', '" + 
                    bikeSize + "', '" +
                    number + "')";
            stmt.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //delete a specific race entry
    public static void deleteRaceEntry(RaceEntry re, Connection conn){
        Statement stmt = null;
        
        try{
            stmt = conn.createStatement();
            stmt.execute("delete from race_entry where id = " + re.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //deletes all associated events when deleting a raceClass
    public static void deleteRaceClass(int id, Connection conn){
        Statement stmt = null;
        
        try{
            stmt = conn.createStatement();
            stmt.execute("delete from race_entry where event_class_id = " + id);
            stmt.execute("delete from laps where race_class_id = " + id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBikeMfg() {
        return bikeMfg;
    }

    public void setBikeMfg(String bikeMfg) {
        this.bikeMfg = bikeMfg;
    }

    public EventClass getEventClass() {
        return eventClass;
    }

    public void setEventClass(EventClass eventClass) {
        this.eventClass = eventClass;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Racer getRacer() {
        return racer;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getMoto1Laps() {
        return moto1Laps;
    }

    public void setMoto1Laps(int moto1Laps) {
        this.moto1Laps = moto1Laps;
    }

    public int getMoto1Place() {
        return moto1Place;
    }

    public void setMoto1Place(int moto1Place) {
        this.moto1Place = moto1Place;
    }

    public int getMoto2Laps() {
        return moto2Laps;
    }

    public void setMoto2Laps(int moto2Laps) {
        this.moto2Laps = moto2Laps;
    }

    public int getMoto2Place() {
        return moto2Place;
    }

    public void setMoto2Place(int moto2Place) {
        this.moto2Place = moto2Place;
    }

    public int getFinalPlace() {
        return finalPlace;
    }

    public void setFinalPlace(int finalPlace) {
        this.finalPlace = finalPlace;
    }
    
    

}
