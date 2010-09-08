/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whiteknuckleranch.mxRaceTracker.dataobjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author assessor
 */
public class Racer {
    int id = -1;
    String firstName = "";
    String lastName = "";
    String birthDay = "";
    
    //makes a new racer
    public static void makeNewRacer(Connection conn){
        Statement stmt = null;
        ResultSet rset = null;
        try{
                String lastName = JOptionPane.showInputDialog(null,"Enter last name");
                if(lastName == null || lastName.length() == 0){
                    JOptionPane.showMessageDialog(null, "All entries are required");
                    return;
                }
                String firstName = JOptionPane.showInputDialog(null,"Enter first name");
                if(firstName == null || firstName.length() == 0){
                    JOptionPane.showMessageDialog(null, "All entries are required");
                    return;
                }
                String birthday = JOptionPane.showInputDialog(null, "Enter birthday (dd-mm-yyyy)");
                if(birthday == null || birthday.length() == 0){
                    JOptionPane.showMessageDialog(null, "All entries are required");
                    return;
                }
                
                //make sure they aren't identical to someone else
                stmt = conn.createStatement();
                rset = stmt.executeQuery("select count(*) from racers where " + 
                        "last_name = '" + lastName + "' and " +
                        "first_name = '" + firstName + "' and " +
                        "birthday = '" + birthday + "'");
                rset.next();
                if(rset.getInt("count(*)") > 0){
                    JOptionPane.showMessageDialog(null, "An identical racer already exists in the database");
                }
                
                stmt = conn.createStatement();
                stmt.execute("insert into racers (last_name, first_name, birthday) values ('" + 
                            lastName + "','" + firstName + "','" + birthday + "')");
                
            }catch(Exception exc){
                exc.printStackTrace();
            }
    }
    
    //deletes a racer
    public static void deleteRacer(Racer r, Connection conn){
        try{
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + r.getLastName() + ", " + r.getFirstName() + "?",
                        "Delete", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                Statement stmt = conn.createStatement();
                stmt.execute("delete from racers where id = " + r.getId());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //gets a specific racer
    public static Racer getRacerById(int id, Connection conn){
        Racer tempRacer = new Racer();
        Statement stmt = null;
        ResultSet rset = null;
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from racers where id = " + id);
            rset.next();
            tempRacer.setId(id);
            tempRacer.setLastName(rset.getString("last_name"));
            tempRacer.setFirstName(rset.getString("first_name"));
            tempRacer.setBirthDay(rset.getString("birthday"));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempRacer;
    }
    
    //gets all racers in the database
    public static Vector<Racer> getAllRacers(Connection conn){
        Vector<Racer> racers = new Vector<Racer>();
        Statement stmt = null;
        ResultSet rset = null;
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select id from racers order by last_name, first_name");
            while(rset.next()){
                racers.add(getRacerById(rset.getInt("id"), conn));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return racers;
        
    }
    
    //gets all the racers for a class
    public static Vector<Racer> getAllClassRacers(EventClass rc, Connection conn){
        Vector<Racer> racers = new Vector<Racer>();
        Statement stmt = null;
        ResultSet rset = null;
        
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select racer_id from race_entry where event_class_id = " +
                    rc.getId());
            while(rset.next()){
                racers.add(getRacerById(rset.getInt("racer_id"), conn));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return racers;
    }

    //gets the racers race age, the age as of Jan 1 this year
    public int getRaceAge(){
        int age = 0;
        
        int birthYear = Integer.parseInt(birthDay.split("-")[2]);
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        age = curYear - birthYear - 1;
        
        return age;
    }
    
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    

}
