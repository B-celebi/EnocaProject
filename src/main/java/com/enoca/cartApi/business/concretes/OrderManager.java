package com.enoca.cartApi.business.concretes;

import com.enoca.cartApi.business.abstracts.OrderBusiness;
import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.common.utilities.abstracts.ModelMapperBusiness;
import com.enoca.cartApi.dao.OrderDao;
import com.enoca.cartApi.dto.responses.GetAllOrdersForCustomerId;
import com.enoca.cartApi.dto.responses.GetOrderForCode;
import com.enoca.cartApi.dto.responses.GetProductResponseForOrder;
import com.enoca.cartApi.entities.concretes.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderManager implements OrderBusiness {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
    @Override
    public String generateOrderCode(String productName,Long quantity,String customerName){
        // Şu anki tarih ve saat bilgisini al
        LocalDateTime now = LocalDateTime.now();

        String code = "ENOCA" + "-" + productName+ "-" +quantity+ "-" + customerName+"-"+now.getYear()+"-"+now.getSecond();

        // Kodu oluştur ve döndür
        return code;
    }

    @Override
    public ResponseEntity<Object> getOrderForCode(String id) {
        Order order = this.orderDao.findOrderByCode(id);
        if(order==null){
            throw new BusinessException("No orders found with this code.");
        }
        GetOrderForCode getOrderForCode = this.modelMapperBusiness.forRequest().map(order, GetOrderForCode.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getOrderForCode);
    }

    @Override
    public ResponseEntity<Object> GetAllOrdersForCustomer(String id) {
        List<Order> orders = this.orderDao.findAllByCustomerId(id);
        if(orders.isEmpty()){
            throw new BusinessException("No orders found for customer");
        }
        GetAllOrdersForCustomerId getAllOrdersForCustomerIds = new GetAllOrdersForCustomerId();
        List<GetProductResponseForOrder> getProductResponses = new ArrayList<>();
        Double realTotalPrice = 0.0;
        int i = 0;
        for(Order o : orders) {
            getProductResponses.add(this.modelMapperBusiness.forResponse().map(o.getProduct(), GetProductResponseForOrder.class));
            getProductResponses.get(i).setQuantity(o.getQuantity());
            realTotalPrice+= o.getTotalPrice();
            i++;
        }
        getAllOrdersForCustomerIds.setCustomerId(id);
        getAllOrdersForCustomerIds.setProducts(getProductResponses);
        getAllOrdersForCustomerIds.setTotalPrice(realTotalPrice);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getAllOrdersForCustomerIds);
    }
}
