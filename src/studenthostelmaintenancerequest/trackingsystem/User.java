/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author chaic
 */
public abstract class User {
 
    
    private String userId;
    private String name;
    private String email;
 
    
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name   = name;
        this.email  = email;
    }
 
    // Abstract Methods 
 
    
    public void login() {
        System.out.println(name + " (ID: " + userId + ") has logged in.");
    }

    public void logout() {
        System.out.println(name + " (ID: " + userId + ") has logged out.");
    }

 
    
    public String getName() {
        return name;
    }
 
    
    public String getEmail() {
        return email;
    }
 
    
    public void setName(String name) {
        this.name = name;
    }
 
    
    public void setEmail(String email) {
        this.email = email;
    }
}
