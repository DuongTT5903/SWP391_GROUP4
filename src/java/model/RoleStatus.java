package model;

/**
 * Represents the status of a role.
 */
public class RoleStatus {

    private int roleID;
    private Boolean status; // Changed to Boolean to handle nullable values

    /**
     * Constructor with parameters.
     *
     * @param roleID Unique role identifier.
     * @param status Role's active status (true = active, false = inactive).
     */
    public RoleStatus(int roleID, Boolean status) {
        this.roleID = roleID;
        this.status = status;
    }
public RoleStatus(Boolean status) {
    this.status = status;
}

    /**
     * Default constructor.
     */
    public RoleStatus() {
        this.status = false; // Default value
    }

    public int getRoleID() {
        return roleID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
