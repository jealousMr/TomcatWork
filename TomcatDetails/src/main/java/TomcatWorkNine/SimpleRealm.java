package TomcatWorkNine;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleRealm implements Realm {
    private Container container;
    private ArrayList users=new ArrayList();
    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public String getInfo() {
        return "A simple Realm implementation";
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public Principal authenticate(String s, String s1) {
        System.out.println("SimpleRealm.authenticate()");
        if (s==null || s1==null)
            return null;
        User user=getUser(s,s1);
        if(user==null)
            return null;
        return new GenericPrincipal(this, user.username, user.password, user.getRoles());
    }

    @Override
    public Principal authenticate(String s, byte[] bytes) {
        return null;
    }

    @Override
    public Principal authenticate(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        return null;
    }

    @Override
    public Principal authenticate(X509Certificate[] x509Certificates) {
        return null;
    }

    @Override
    public boolean hasRole(Principal principal, String role) {
        if ((principal == null) || (role == null) ||
                !(principal instanceof GenericPrincipal))
            return (false);
        GenericPrincipal gp = (GenericPrincipal) principal;
        if (!(gp.getRealm() == this))
            return (false);
        boolean result = gp.hasRole(role);
        return result;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    private void createUserDatabase() {
        User user1 = new User("ken", "blackcomb");
        user1.addRole("manager");
        user1.addRole("programmer");
        User user2 = new User("cindy", "bamboo");
        user2.addRole("programmer");

        users.add(user1);
        users.add(user2);
    }
    private User getUser(String username, String password) {
        Iterator iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            if (user.username.equals(username) && user.password.equals(password))
                return user;
        }
        return null;
    }
    class User {

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String username;
        public ArrayList roles = new ArrayList();
        public String password;

        public void addRole(String role) {
            roles.add(role);
        }
        public ArrayList getRoles() {
            return roles;
        }
    }
}
