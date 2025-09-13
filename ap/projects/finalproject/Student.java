package ap.projects.finalproject;

public class Student extends User {

    private boolean active = true;

    public Student(String name, String studentId, String username, String password) {
        super(name, studentId, username, password);
    }

    public String getId() {
        return super.getId();
    }

    public String getStudentId() {
        return super.getId();
    }

    //public boolean isActive() {
    //    return active;
    //}

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return getUserInfo() + " , Status: " + (active ? "Active" : "Inactive");
    }
}
