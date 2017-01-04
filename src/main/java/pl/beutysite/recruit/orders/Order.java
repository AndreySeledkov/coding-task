package pl.beutysite.recruit.orders;

import pl.beutysite.recruit.event.SeriousEnterpriseEventBus;
import pl.beutysite.recruit.event.SeriousEnterpriseEventBusLookup;
import pl.beutysite.recruit.util.TaxCalculationsHelper;

import java.math.BigDecimal;

public class Order {
    private final int itemId;
    private final int customerId;
    private final BigDecimal price;

    protected OrderFlag orderFlag;
    //for performance reasons lets pre-calculate it in constructor
    //private int preCalculatedHashCode = 0;

    //private static Random random = newRandom();

    protected SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;

    private static final BigDecimal TAX = new BigDecimal("23.5");

    public Order(int itemId, int customerId, BigDecimal price) {
        this.itemId = itemId;
        this.customerId = customerId;
        this.price = price;
        //preCalculatedHashCode = random.nextInt();
        orderFlag = OrderFlag.STANDARD;
    }

    public void process() {
        //SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");
        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }

    public BigDecimal getTotalAmount() {
        return price.add(getTax());
    }

    public int getItemId() {
        return itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTax() {
        //calculating standard tax - 23.5%
        return TaxCalculationsHelper.getPercentagePart(price, TAX);
    }

//    @Override
//    public int hashCode() {
//        return preCalculatedHashCode;
//    }


    public OrderFlag getOrderFlag() {
        return orderFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (customerId != order.customerId) return false;
        if (itemId != order.itemId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + customerId;
        return result;
    }
}
