package de.lastminute.saletax.receipt.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.lastminute.saletax.product.Product;

public class ReceiptFileParserTest {

    IReceiptParser receiptParser = new ReceiptFileParser();

    @Test
    public void parseTest1(){
        List<String> items =  Arrays.asList(" book ", " music CD ", " chocolate bar ");
        ArrayList<Product> products = receiptParser.parse(ReceiptFileParserTest.class.getResource("/test1.txt").getFile());
        assertNotNull(products);
        assertEquals(3 ,products.size());
        assertTrue(isItemExist(items, products));
    }

    @Test
    public void parseTest2(){
        List<String> items =  Arrays.asList(" imported box of chocolates ", " imported bottle of perfume ");
        ArrayList<Product> products = receiptParser.parse(ReceiptFileParserTest.class.getResource("/test2.txt").getFile());
        assertNotNull(products);
        assertEquals(2 ,products.size());
        assertTrue(isItemExist(items, products));
    }


    @Test
    public void parseTest3(){
        List<String> items =  Arrays.asList(" imported bottle of perfume ", " bottle of perfume ", " packet of headache pills ", " imported box of chocolates ");
        ArrayList<Product> products = receiptParser.parse(ReceiptFileParserTest.class.getResource("/test3.txt").getFile());
        assertNotNull(products);
        assertEquals(4 ,products.size());
        assertTrue(isItemExist(items, products));
    }



    boolean isItemExist( List<String> items , ArrayList<Product> products){
        for (Product product: products) {
            String name = product.getName();
            if(!items.contains(name)){
                return false;
            }
        }
        return true;

    }
}
