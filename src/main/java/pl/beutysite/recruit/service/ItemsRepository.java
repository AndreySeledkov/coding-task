package pl.beutysite.recruit.service;

import java.math.BigDecimal;

public interface ItemsRepository {

    public BigDecimal fetchItemPrice(int itemId);
}
