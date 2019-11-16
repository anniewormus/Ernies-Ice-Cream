import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.JOptionPane;

public class OrderInfo {
	private String firstName = "Annie";
	private String lastName = "Wormus";
	private String icecream = "vanilla";
	private String toppings = "sprinkles";
	private String cuporcone = "cone";
	private double price = 20.33;
	private String date ="";
	
	//constructor
	public OrderInfo(String firstName, String lastName, String icecream, String toppings, String cuporcone, double price){
		this.firstName = firstName;
		this.lastName = lastName;
		this.icecream = icecream;
		this.toppings = toppings;
		this.cuporcone = cuporcone;
		this.price = price;
		this.date = setCurrentDate();
	
	}
	//no arg constructor
    public OrderInfo(){
		
	}
    
    //getters and setters
    public String getFirstName(){
    	return this.firstName;
    }
    public void setFirstName(String name){
    	this.firstName = firstName;
    }
  
    public String getLastName(){
    	return this.lastName;
    }
    public void setLastName(String lastName){
    	this.lastName = lastName;
    }
    
    public String getIcecream(){
    	return this.icecream;
    }
    public void setIcecream(String icecream){
    	this.icecream = icecream;
    }
    
    public String getToppings(){
    	return this.toppings;
    }
    public void setToppings(String toppings){
    	this.toppings = toppings;
    }
    
    public String getCupOrCone(){
    	return this.cuporcone;
    }
    public void setCupOrCone(String cuporcone){
    	this.cuporcone = cuporcone;
    }
    
    public double getPrice(){
    	return this.price;
    }
    public void setPrice(double price){
    	this.price = price;
    }
    
    public String getDate(){
    	return date;
    }
    private  String setCurrentDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yy");
		Date date = new Date();
		return df.format(date);
	}

    public String toString(){
    	return "Name: " + getFirstName() + " " + getLastName() + "\nIce Cream: " + getIcecream()
    	+ "\nToppings: " + getToppings() + "\nCup or Cone: " + getCupOrCone()
    	+ "\nPrice: " + getPrice();
    }
}

