package es.upm.grise.order;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.order.exceptions.CannotAddItemsToPlacedOrderException;
import es.upm.grise.order.exceptions.IncorrectItemException;
import es.upm.grise.order.exceptions.NonExistingItemException;

class OrderTest {

    private Order order;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        order = new Order();
        product1 = new Product();
        product1.id = 1;
        
        product2 = new Product();
        product2.id = 2;
    }

    @Test
    void testAddItemSuccessfully() throws Exception {
        Item item = new Item(product1, 2, 10.0);
        order.addItem(item);
        
        assertEquals(1, order.getItems().size());
        assertEquals(Status.UNCONFIRMED, order.getStatus());
    }

    @Test
    void testAddExistingItemIncrementsQuantity() throws Exception {
        Item item1 = new Item(product1, 2, 10.0);
        Item item2 = new Item(product1, 3, 12.0);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(1, order.getItems().size());
        assertEquals(5, order.getItems().get(0).getQuantity());
        assertEquals(12.0, order.getItems().get(0).getPrice());
    }

    @Test
    void testAddItemNegativePriceThrowsException() {
        Item item = new Item(product1, 2, -5.0);
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        });
    }

    @Test
    void testAddItemZeroOrNegativeQuantityThrowsException() {
        Item item = new Item(product1, 0, 10.0);
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        });
    }

    @Test
    void testRemoveExistingItemSuccessfully() throws Exception {
        Item item = new Item(product1, 2, 10.0);
        order.addItem(item);
        
        order.removeItem(item);
        assertTrue(order.getItems().isEmpty());
        assertNull(order.getStatus());
    }

    @Test
    void testRemoveNonExistingItemThrowsException() {
        Item item = new Item(product1, 2, 10.0);
        assertThrows(NonExistingItemException.class, () -> {
            order.removeItem(item);
        });
    }
}