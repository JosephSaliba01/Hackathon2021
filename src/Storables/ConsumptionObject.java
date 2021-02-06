package Storables;

public class ConsumptionObject {

    private String username;
    private double price;
    private String date;

    public ConsumptionObject(String username, double price, String date){
        this.username = username;
        this.price = price;
        this.date =date;
    }

    public double getPrice() {
        return price;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return "ConsumptionObject{" +
                "username='" + username + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                '}';
    }
}
