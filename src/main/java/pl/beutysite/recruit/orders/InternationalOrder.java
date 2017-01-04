package pl.beutysite.recruit.orders;

import pl.beutysite.recruit.util.TaxCalculationsHelper;

import java.math.BigDecimal;

//TODO will have more taxes calculated
public class InternationalOrder extends Order {

    private static final BigDecimal TAX = new BigDecimal("15.0");

    public InternationalOrder(int itemId, int customerId, BigDecimal price) {
        super(itemId, customerId, price);
        orderFlag = OrderFlag.INTERNATIONAL;
    }

    public BigDecimal getTax() {
        //calculating international tax - 15.0%
        return TaxCalculationsHelper.getPercentagePart(getPrice(), TAX);
    }

    public void process() {
//        SeriousEnterpriseEventBus seeb = SeriousEnterpriseEventBusLookup.seeb;
        seeb.sendEvent("Order processing started");

        seeb.sendEvent("Dispatch translated order confirmation email");

        seeb.sendEvent("Initiate shipment");
        seeb.sendEvent("Order processing finished");
    }
}
