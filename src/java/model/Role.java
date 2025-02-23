/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Role {
   private int roleID;
    private String roleName;

    /**
     *
     */
    public Role() {
       
    }

    /**
     *
     * @param roleid
     * @param rolename
     */
    public Role(int roleid, String rolename) {
        this.roleID = roleid;
        this.roleName = rolename;
    }

    /**
     *
     * @return
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     *
     * @param roleID
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     *
     * @return
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     *
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
 
}
