package pl.beutysite.recruit.service;

import com.sun.istack.internal.Nullable;
import pl.beutysite.recruit.orders.*;

import java.math.BigDecimal;
import java.util.*;

public class OrdersManagementSystemImpl implements OrdersManagementSystem {

    //external systems
    private final TaxOfficeAdapter taxOfficeAdapter;
    private final ItemsRepository itemsRepository;


    private SortedSet<Order> ordersQueue = new TreeSet<>(new OrderComparator());

//    private Order newOrder=null;

    public OrdersManagementSystemImpl(TaxOfficeAdapter taxOfficeAdapter, ItemsRepository itemsRepository) {
        this.taxOfficeAdapter = taxOfficeAdapter;
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void createOrder(int itemId, int customerId, OrderFlag... flags) {

        //fetch price and calculate discount and taxes
        BigDecimal itemPrice = itemsRepository.fetchItemPrice(itemId);


        UnifiedOrder unifiedOrder = new UnifiedOrder(itemId, customerId, itemPrice, flags);

//        //create and queue order
//        OrderFlag flag = flags[0];
//        Order newOrder;
//        switch (flag) {
//
//            case PRIORITY:
//                newOrder = new PriorityOrder(itemId, customerId, itemPrice);
//                break;
//            case INTERNATIONAL:
//                newOrder = new InternationalOrder(itemId, customerId, itemPrice);
//                break;
//            case DISCOUNTED:
//                newOrder = new DiscountedOrder(itemId, customerId, itemPrice);
//                break;
//
//            case STANDARD:
//            default:
//                newOrder = new Order(itemId, customerId, itemPrice);
//                break;
//
//        }
        ordersQueue.add(unifiedOrder);

        //ordersQueue.add(newOrder);

//        //JIRA-18883 Fix priority orders not always being fetched first
//        if (OrderFlag.PRIORITY == flag) {
//            while (fetchNextOrder() != newOrder) {
//                ordersQueue.remove(newOrder);
//                newOrder = new PriorityOrder(itemId, customerId, itemPrice);
//                ordersQueue.add(newOrder);
//            }
//            ordersQueue.add(newOrder);
//        }

        //send tax due amount
        taxOfficeAdapter.registerTax(unifiedOrder.getTax());
    }

    @Nullable
    @Override
    public Order fetchNextOrder() {
        Iterator iterator = ordersQueue.iterator();
        return iterator.hasNext() ? ordersQueue.iterator().next() : null;
    }

    class OrderComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            if (o1.getOrderFlag() == OrderFlag.PRIORITY) {
                return -1;
            }
            return 1;
        }
    }

}
