/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class Room{
    //data variable
    private String roomNumber;
    private String block;

    //constructor
    public Room(String roomNumber, String block){
        this.roomNumber = roomNumber;
        this.block = block;
    }

    //getRoomDetails method
    public String getRoomDetails(){
        return "Room Number: " + roomNumber + ", Block: " + block;
    }

    //Getters and Setters
    public String getRoomNumber(){
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }

    public String getBlock(){
        return block;
    }

    public void setBlock(String block){
        this.block = block;
    }
}
