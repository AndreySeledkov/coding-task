package pl.beutysite.recruit.orders;

import pl.beutysite.recruit.util.TaxCalculationsHelper;

import java.math.BigDecimal;

public class PriorityOrder extends Order {

    private static final BigDecimal FEE = new BigDecimal("1.5");

    public PriorityOrder(int itemId, int customerId, BigDecimal price) {
        super(itemId, customerId, price);
        orderFlag = OrderFlag.PRIORITY;
    }

    public void process() {
        //SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");

        seeb.sendEvent("*** This is priority order, hurry up! ***");

        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }

    @Override
    public BigDecimal getPrice() {
        //adding priority order fee - 1.5%
        return TaxCalculationsHelper.addPercentage(super.getPrice(), FEE);
    }
}
