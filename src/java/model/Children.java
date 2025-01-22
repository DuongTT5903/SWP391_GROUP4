package model;

/**
 * This class represents a child with an ID, name, gender, and parent ID.
 *
 * @author Admin
 */
public class Children {
    private int childId;
    private String childname;
    private int gender;
    private int parentId;

    public Children() {
    }

    public Children(int childId, String childname, int gender, int parentId) {
        this.childId = childId;
        this.childname = childname;
        this.gender = gender;
        this.parentId = parentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}