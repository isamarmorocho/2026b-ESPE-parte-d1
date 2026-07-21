package es.upm.grise.order;

import java.util.ArrayList;

import es.upm.grise.order.exceptions.CannotAddItemsToPlacedOrderException;
import es.upm.grise.order.exceptions.IncorrectItemException;
import es.upm.grise.order.exceptions.NonExistingItemException;

public class Order {

    private ArrayList<Item> items;
    private Status status;
    private Invoice invoice;

    public Order() {
        items = new ArrayList<Item>();
        status = null;
    }

    public void addItem(Item item) throws CannotAddItemsToPlacedOrderException, IncorrectItemException {
        if (status == Status.PLACED) {
            throw new CannotAddItemsToPlacedOrderException();
        }

        if (item.getPrice() < 0) {
            throw new IncorrectItemException();
        }

        if (item.getQuantity() <= 0) {
            throw new IncorrectItemException();
        }

        for (Item i : items) {
            if (i.getProduct().getId() == item.getProduct().getId()) {
                i.setQuantity(i.getQuantity() + item.getQuantity());

                if (item.getPrice() > i.getPrice()) {
                    i.setPrice(item.getPrice());
                }

                return;
            }
        }

        items.add(item);

        if (items.size() == 1) {
            status = Status.UNCONFIRMED;
        }
    }

    public void removeItem(Item item) throws NonExistingItemException {
        Item removedItem = null;

        for (Item i : items) {
            if (i.getProduct().getId() == item.getProduct().getId()) {
                removedItem = i;
                break;
            }
        }

        if (removedItem == null) {
            throw new NonExistingItemException();
        }

        items.remove(removedItem);

        if (items.isEmpty()) {
            status = null;
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Status getStatus() {
        return status;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}