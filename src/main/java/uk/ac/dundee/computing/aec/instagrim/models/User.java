/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import uk.ac.dundee.computing.aec.instagrim.stores.Profile;

//import com.datastax.driver.mapping.UDTMapper<T>;
//import com.datastax.driver.mapping.MappingManager;
/**
 *
 * @author Administrator
 */
public class User {

    Cluster cluster;

    public User() {

    }

    public boolean RegisterUser(String username, String Password, String firstName, String secondName, String email, String street, int postcode, String city) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        Set<String> EncodedMail = new HashSet<String>();
        //Map address = new HashMap();
        //Address userAddress = new Address();
        Map<String, UDTValue> addresses = new HashMap();
        //userAddress.setCity(city);
        //userAddress.setStreet(street);
        //userAddress.setZipCode(postcode);
        UserType addressType = cluster.getMetadata().getKeyspace("instagrim").getUserType("address");
        UDTValue addressToInsert = addressType.newValue()
                .setString("street", street)
                .setString("city", city)
                .setInt("zip", postcode);

        try {
            EncodedPassword = sha1handler.SHA1(Password);
            EncodedMail.add(email);
            addresses.put("addresses", addressToInsert);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email,addresses) Values(?,?,?,?,?,?)");

        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username, EncodedPassword, firstName, secondName, EncodedMail, addresses));
        //We are assuming this always works.  Also a transaction would be good here !

        return true;
    }

    public boolean IsValidUser(String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {

                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Profile getDetials(String user) {
        Profile profileToReturn = null;
        Session session = cluster.connect("instagrim");

        PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));

        if (rs.isExhausted()) {
            System.out.println("Profile Not Found!");
        } else {
            for (Row pullUser : rs) {
                Map<String, UDTValue> addressMap = pullUser.getMap("addresses", String.class, UDTValue.class);

                profileToReturn = new Profile(pullUser.getString("login"),
                        pullUser.getString("first_name"),
                        pullUser.getString("last_name"),
                        addressMap.get("addresses").getString("street"),
                        pullUser.getSet("email", String.class).toString(),
                        addressMap.get("addresses").getString("city"),
                        addressMap.get("addresses").getInt("zip")
                );

            }

            profileToReturn.printProfile();
        }

        return profileToReturn;
    }
}
