/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author trung
 */
public class Cart {
    private int id;
   private boolean checkService;
   private int amount;
   private int getNumberOfPerson;

    public void setGetNumberOfPerson(int getNumberOfPerson) {
        this.getNumberOfPerson = getNumberOfPerson;
    }

    public int getGetNumberOfPerson() {
        return getNumberOfPerson;
    }
   private Service service;
   private User user;
    public Cart() {
    }
    public Cart(int id, boolean checkService, int amount, Service service, User user) {
        this.id = id;
        this.checkService = checkService;
        this.amount = amount;
        this.service = service;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheckService() {
        return checkService;
    }

    public void setCheckService(boolean checkService) {
        this.checkService = checkService;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}