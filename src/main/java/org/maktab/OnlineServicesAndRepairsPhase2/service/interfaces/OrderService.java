package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    List<Order> findByCustomerId();
    List<Order> getByExpertSuggestion();
    List<Order> findById(Order order);
    List<Order> getOrdersById(Order order);
    Order saveOrder(Order order);
    Order save(Order order);
    Order getById(Long id);
    List<Order> getByCityAndService(Expert expert);
    Order chooseExpertForOrder(Order order);
    List<Order> takenOrdersAndDoneOrders();
    List<Order> BasedOnTimePeriodAndOrderStatusAndServiceName(Timestamp firstDate, Timestamp secondDate,
                                                              OrderStatus orderStatus, String specialtyName);
}
