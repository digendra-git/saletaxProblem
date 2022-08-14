package de.lastminute.saletax.receipt.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

import de.lastminute.saletax.product.ItemType;
import de.lastminute.saletax.product.Product;

public class Receipt {

	private ArrayList<Product> productsList = new ArrayList<Product>();
	String[] exemptedItems =  new String[]{"book","chocolate","pills"};

	private double total;
	private double taxTotal;
	
	@SuppressWarnings("resource")
	public Receipt(String inputFileName){
		/* This method accepts a directory to an input text file which it traverses
		 * Each line is read and converted into the proper Product type
		 *
		 */
		try {

            Scanner input = new Scanner(System.in);

            File file = new File(inputFileName);

            input = new Scanner(file);
            
            while (input.hasNextLine()) {
            	
            	String line = input.nextLine(); //take the line
            	 
            	String[] words = line.split(" "); //divide the line into tokens
            	
            	int qty = Integer.parseInt(words[0]); //first token is the quantity
            	
            	boolean isImported = line.contains("imported"); //check if the item is imported
            	
            	 //check if the item in the exempted list
            	
            	int exemptedItemIndex = containsItemFromArray(line,exemptedItems); //Find which type of exemption
            	
            	String exemptedType = null;
            	
            	if(exemptedItemIndex != -1){
            		//the item is tax exempted
            		
            		//the exempted word is contained at exempted item index
                	exemptedType = exemptedItems[exemptedItemIndex];
        			
            	}

            	int splitIndex = line.lastIndexOf("at");
            	
            	if(splitIndex == -1){
            		
            		System.out.println("Bad Formatting");
            		
            	} else {
            		
                	float price = Float.parseFloat((line.substring(splitIndex + 2))); //the price is the token after the substring "at"
                    
                	String name = line.substring(1, splitIndex); //the name is everything between the qty and at
                	
                    for(int i = 0;i<qty;i++){
                    	//loop for the total quantity of the item to make that many in the list
                    	
                    	Product newProduct = null;
                    	
                    	if(isImported){
                    		//the product is imported
                        	if(exemptedType != null){
                        		//the product is not imported and is exempt of sales tax
                        		
                        		if(exemptedType == "book"){
                        			newProduct = new Product(name,price, ItemType.IMPORTED_BOOK);
                        		} else if(exemptedType == "pills"){
                        			newProduct = new Product(name,price,ItemType.IMPORTED_MEDICAL);
                        		} else if(exemptedType == "chocolate"){
                        			newProduct = new Product(name,price,ItemType.IMPORTED_FOOD);
                        		}

                        	} else {
                        		//the product is imported and sales taxed
                        		newProduct = new Product(name,price,ItemType.IMPORTED_OTHERS);
                        	}
                        	
                    	} else {
                    		//the product is domestic
                        	if(exemptedType != null){
                        		//the product is domestic and is exempt of sales tax
                        		
                        		if(exemptedType == "book"){
                        			newProduct = new Product(name,price,ItemType.BOOK);
                        		} else if(exemptedType == "pills"){
                        			newProduct = new Product(name,price,ItemType.MEDICAL);
                        		} else if(exemptedType == "chocolate"){
                        			newProduct = new Product(name,price,ItemType.FOOD);
                        		}

                        	} else {
                        		//the product is domestic and is sales taxed
                        		newProduct = new Product(name,price,ItemType.OTHERS);
                        	}
                    	}
                    	
                        productsList.add(newProduct); //add the product to our receipt's list
                    }
            	}
            	
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

  public static int containsItemFromArray(String inputString, String[] items) {
		/*
		 * This method returns the index of which String in items was found in the input String
		 *  -1 is returned in none of the Strings in items are found in the inputString
		 */
		int index = -1;

		for(int i = 0;i<items.length;i++){

			index = inputString.indexOf(items[i]);

			if(index != -1)
				return i;

		}
		return -1;

	}


}
