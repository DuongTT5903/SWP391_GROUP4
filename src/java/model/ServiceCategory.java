/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class ServiceCategory {

    /**
     *
     * @param categoryID
     * @param categoryDetail
     * @param categoryName
     */
    public ServiceCategory(int categoryID, String categoryDetail, String categoryName) {
        this.categoryID = categoryID;
        this.categoryDetail = categoryDetail;
        this.categoryName = categoryName;
    }

    /**
     *
     */
    public ServiceCategory() {
    }

    /**
     *
     * @return
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     *
     * @param categoryID
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     *
     * @return
     */
    public String getCategoryDetail() {
        return categoryDetail;
    }

    /**
     *
     * @param categoryDetail
     */
    public void setCategoryDetail(String categoryDetail) {
        this.categoryDetail = categoryDetail;
    }

    /**
     *
     * @return
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private int categoryID;
    private String categoryDetail;
    private String categoryName;
}
