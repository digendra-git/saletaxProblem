package de.lastminute.saletax.receipt.service;

import java.util.ArrayList;

import de.lastminute.saletax.product.Product;

public interface IReceiptParser {

    String[] exemptedItems =  new String[]{"book","chocolate","pills"};

    ArrayList<Product> parse(String input);

}
