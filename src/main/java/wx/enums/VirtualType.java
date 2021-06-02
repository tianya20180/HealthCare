package wx.enums;

public enum VirtualType {

    User("User"),  Admin("Admin"), Doctor("Doctor");

    private String type;

    private VirtualType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
