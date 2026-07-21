package es.upm.grise.order;

public class Invoice {
    
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
}