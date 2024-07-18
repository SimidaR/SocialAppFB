package facebook.app.entities;

public class Profile {
    private int id;
    private String name;
    private int age;
    private String location;
    private String email;
    public Profile(int id, String name, String email, int age, String location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
        this.email = email;
    }
    public Profile() {}

    public void setId(int id) {    this.id = id;}
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) {
        this.age = age;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getId() { return id; }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public String getLocation() {
        return location;
    }
}
