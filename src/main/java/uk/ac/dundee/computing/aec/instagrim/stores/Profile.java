/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

/**
 *
 * @author Think
 */
public class Profile {

    private String userName;
    private String firstName;
    private String secondName;
    private String street;
    private String email;
    private String city;
    private int postCode;

    public Profile(String userName, String firstName, String secondName, String street, String email, String city, int postCode) {
        this.userName = userName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.city = city;
        this.email = email;
        this.street = street;
        this.postCode = postCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getSecondName()
    {
        return secondName;
    }
    
    public String getStreet() {
        return street;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getCity() {
        return city;
    }
    
    public int getPostCode() {
        return postCode;
    }
    
    public void printProfile(){
        
        System.out.println(userName + ' ' + firstName + ' ' + secondName + ' ' + street + ' ' + email + ' ' + city + ' ' + postCode);
    }
}
