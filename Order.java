package salonpackage;

public interface Order {
    public void setDate(String date);

    public String getDate();

    public void setCustomerName(String customerName);

    public String getCustomerName();

    public double getTotalCost();

    public void setTotalCost(double totalCost);

}