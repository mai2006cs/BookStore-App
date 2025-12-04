package bookstore.model;

public class Book {
    private String title;
    private String imageName;
    private double price;

    public Book(String title, String imageName, double price) {
        this.title = title;
        this.imageName = imageName;
        this.price = price;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getImageName() {  // <- this is what you need
        return imageName;
    }

    public double getPrice() {
        return price;
    }
}