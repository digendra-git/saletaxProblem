package de.lastminute.saletax.receipt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import de.lastminute.saletax.product.Product;

public class CalculatorService {

    private double total;
    private double taxTotal;

    ArrayList<Product> productsList;

    public CalculatorService(ArrayList<Product> productsList){
        this.productsList = productsList;
        calculateTotals();
    }

    private void calculateTotals(){
        /*
         * This method runs through the receipt's list of products in order to calculate the sales tax and total
         * BigDecimals are used in order to avoid rounding errors
         *
         */
        int numOfItems = productsList.size();

        BigDecimal runningSum = new BigDecimal("0");
        BigDecimal runningTaxSum = new BigDecimal("0");

        for(int i = 0;i<numOfItems;i++){

            runningTaxSum = BigDecimal.valueOf(0);

            BigDecimal totalBeforeTax = new BigDecimal(String.valueOf(productsList.get(i).getPrice()));

            runningSum = runningSum.add(totalBeforeTax);

            if(productsList.get(i).isSalesTaxable()){
                //This item is sales taxable so charge 10% tax and round to the nearest 0.05

                BigDecimal salesTaxPercent = new BigDecimal(".10");
                BigDecimal salesTax = salesTaxPercent.multiply(totalBeforeTax);

                salesTax = round(salesTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
                runningTaxSum = runningTaxSum.add(salesTax);


            }

            if(productsList.get(i).isImportedTaxable()){
                //this item is import taxable so charge 5% tax and round to the nearest 0.05

                BigDecimal importTaxPercent = new BigDecimal(".05");
                BigDecimal importTax = importTaxPercent.multiply(totalBeforeTax);

                importTax = round(importTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
                runningTaxSum = runningTaxSum.add(importTax);

            }


            productsList.get(i).setPrice(runningTaxSum.floatValue() + productsList.get(i).getPrice());

            taxTotal += runningTaxSum.doubleValue();

            runningSum = runningSum.add(runningTaxSum);
        }
        //save out sales tax, and total
        //	taxTotal = roundTwoDecimals(taxTotal);
        total = runningSum.doubleValue();
    }

    public void setTotal(BigDecimal amount){
        total = amount.doubleValue();
    }

    public double getTotal(){
        return total;
    }
    public void setSalesTaxTotal(BigDecimal amount){
        taxTotal = amount.doubleValue();
    }

    public double getSalesTaxTotal(){
        return taxTotal;
    }

    public static BigDecimal round(BigDecimal value, BigDecimal increment,RoundingMode roundingMode) {
        /*
         * This method handles custom rounding to 0.05, and also sets the BigDecimal numbers to use 2 decimals
         *
         */
        if (increment.signum() == 0) {
            // 0 increment does not make much sense, but prevent division by 0
            return value;
        } else {
            BigDecimal divided = value.divide(increment, 0, roundingMode);
            BigDecimal result = divided.multiply(increment);
            result.setScale(2, RoundingMode.UNNECESSARY);
            return result;
        }
    }


    public void printReceipt(){
        /*
         * Print all the information about the Receipt
         *
         */
        int numOfItems = productsList.size();
        for(int i = 0;i<numOfItems;i++){
            System.out.println("1" + productsList.get(i).getName() + "at " + productsList.get(i).getPrice());
        }
        System.out.printf("Sales Tax: %.2f\n", taxTotal);
        System.out.println("Total: " + total);
    }
}
