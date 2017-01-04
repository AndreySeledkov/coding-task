package pl.beutysite.recruit.orders;

import pl.beutysite.recruit.util.TaxCalculationsHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 04.01.2017.
 */
public class UnifiedOrder extends Order {

    private List<Order> orderList = new ArrayList<>();

    public UnifiedOrder(int itemId, int customerId, BigDecimal itemPrice, OrderFlag... orderFlags) {
        super(itemId, customerId, itemPrice);

        for (OrderFlag flag : orderFlags) {
            Order newOrder;
            switch (flag) {

                case PRIORITY:
                    newOrder = new PriorityOrder(itemId, customerId, itemPrice);
                    break;
                case INTERNATIONAL:
                    newOrder = new InternationalOrder(itemId, customerId, itemPrice);
                    break;
                case DISCOUNTED:
                    newOrder = new DiscountedOrder(itemId, customerId, itemPrice);
                    break;

                case STANDARD:
                default:
                    newOrder = new Order(itemId, customerId, itemPrice);
                    break;
            }
            orderList.add(newOrder);
        }

        orderFlag = OrderFlag.UNIFIED;
    }

    @Override
    public BigDecimal getPrice() {

        BigDecimal bigDecimal = new BigDecimal("0.0");
        for (Order order : orderList) {
            bigDecimal.add(order.getPrice());
        }
        return bigDecimal;
    }

    @Override
    public BigDecimal getTax() {

        BigDecimal bigDecimal = new BigDecimal("0.0");
        for (Order order : orderList) {
            bigDecimal.add(order.getTax());
        }
        return bigDecimal;
    }


    @Override
    public BigDecimal getTotalAmount() {

        BigDecimal bigDecimal = new BigDecimal("0.0");
        for (Order order : orderList) {
            bigDecimal.add(order.getTotalAmount());
        }

        return bigDecimal;
    }
}
