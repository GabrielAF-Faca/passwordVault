
package model;


/**
 *
 * @author Gabriel Azenha Fachim
 */

public class User {
    
    private int id;
    private final String site;
    private final String name;
    private final String email;
    private final String password;

    public User(int id, String site, String name, String email, String password) {
        this.id = id;
        this.site = site;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String site, String name, String email, String password) {
        this.site = site;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", site=" + site + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }
    
    
    
    

    
    
    
    
}
