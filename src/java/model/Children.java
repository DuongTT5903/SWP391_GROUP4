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

    /**
     *
     */
    public Children() {
    }

    /**
     *
     * @param childId
     * @param childname
     * @param gender
     * @param parentId
     */
    public Children(int childId, String childname, int gender, int parentId) {
        this.childId = childId;
        this.childname = childname;
        this.gender = gender;
        this.parentId = parentId;
    }

    /**
     *
     * @return
     */
    public int getChildId() {
        return childId;
    }

    /**
     *
     * @param childId
     */
    public void setChildId(int childId) {
        this.childId = childId;
    }

    /**
     *
     * @return
     */
    public String getChildname() {
        return childname;
    }

    /**
     *
     * @param childname
     */
    public void setChildname(String childname) {
        this.childname = childname;
    }

    /**
     *
     * @return
     */
    public int getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public int getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}