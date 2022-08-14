package de.lastminute.saletax.product;

public class Product {
    private String name;
    private float price;
    private ItemType type;

    public Product(String name, float price, ItemType itemType) {
        this.name = name;
        this.setPrice(price);

        this.type = itemType;

    }

    public String toString() {
        return this.name + this.getPrice();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSalesTaxable() {
        return !this.type.isExempted();
    }

    public boolean isImportedTaxable() {
        return this.type.isImported();
    }

}
