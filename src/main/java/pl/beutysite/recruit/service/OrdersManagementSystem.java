package pl.beutysite.recruit.service;

import pl.beutysite.recruit.orders.Order;
import pl.beutysite.recruit.orders.OrderFlag;

public interface OrdersManagementSystem {

    public void createOrder(int itemId, int customerId,OrderFlag... flags);
    public Order fetchNextOrder();
}
