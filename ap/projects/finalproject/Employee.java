package ap.projects.finalproject;

public class Employee extends User {

    private int addBookCount = 0;
    private int loanApproveCount = 0;
    private int loanReturnedCount = 0;

    public Employee(String name, String employeeId, String username, String password,
                    int addBookCount, int loanApproveCount, int loanReturnedCount) {
        super(name, employeeId, username, password);
        this.addBookCount = addBookCount;
        this.loanApproveCount = loanApproveCount;
        this.loanReturnedCount = loanReturnedCount;
    }

    public Employee(String name, String employeeId, String username, String password) {
       super(name, employeeId, username, password);
    }

    public String getId() {
        return super.getId();
    }

    public String getEmployeeId() {
        return super.getId();
    }

    public void incrementBooksRegistered() {
        addBookCount++;
    }

    public void incrementLoansApproved() {
        loanApproveCount++;
    }

    public void incrementLoansReturned() {
        loanReturnedCount++;
    }

    public int getAddBookCount() {
        return addBookCount;
    }

    public int getLoanApproveCount() {
        return loanApproveCount;
    }

    public int getLoanReturnedCount() {
        return loanReturnedCount;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String toString() {
        return getUserInfo();
    }
}
