package ap.projects.finalproject;

public abstract class User implements UserManagement{

    protected String name;
    protected String id;
    protected String username;
    protected String password;

    public User(String name, String id, String username, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getUserInfo();
    }

    @Override
    public String getUserInfo() {
        return "Name: " + name + " , ID: " + id + " , Username: " + username;
    }
}
