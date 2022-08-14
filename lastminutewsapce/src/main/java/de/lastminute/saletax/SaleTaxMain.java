package de.lastminute.saletax;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import de.lastminute.saletax.product.Product;
import de.lastminute.saletax.receipt.service.CalculatorService;
import de.lastminute.saletax.receipt.service.IReceiptParser;
import de.lastminute.saletax.receipt.service.ReceiptFileParser;

/*
 * Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is
 * an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.
 * When I purchase items, I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total
 * cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price
 * of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.
 * 
 * This class servers as a driver for the supporting classes
 */
public class SaleTaxMain {


	public static void main(String[] args) throws FileNotFoundException {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter receipt file name ");

		String fileName = scan.next();

		processReceipt(fileName);

		scan.close();

	}


	static void processReceipt(String fileName) throws FileNotFoundException {
		if(fileName != null){
			File inputFile = new File(fileName);
			if(inputFile.exists()){
				IReceiptParser parser = new ReceiptFileParser();
				ArrayList<Product> products = parser.parse(fileName);

				CalculatorService calculatorService = new CalculatorService(products);
				calculatorService.printReceipt();
			} else {
				System.out.println(fileName + " file not found ");
				throw new FileNotFoundException("File not found , please specify correct input file");
			}

		}

	}

}
