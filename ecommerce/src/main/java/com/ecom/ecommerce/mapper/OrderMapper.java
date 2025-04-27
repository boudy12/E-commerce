package com.ecom.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecom.ecommerce.dto.OrderDTO;
import com.ecom.ecommerce.dto.OrderItemDTO;
import com.ecom.ecommerce.model.Order;
import com.ecom.ecommerce.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "items")
    OrderDTO toDTO(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "items", source = "orderItems")
    Order toEntity(OrderDTO orderDTO);

    
    List<OrderDTO> toDTOs(List<Order> orders);
    
    
    List<Order> toEntities(List<OrderDTO> orderDTOS);
    
    
    
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "ordertId", source = "order.id")
    OrderItemDTO toDTO(OrderItem orderItem);

    
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "order.id", source = "ordertId")
    OrderItem toEntity(OrderItemDTO orderItemDTO);
    
    
    List<OrderItemDTO> toOrderItemsDTO(List<OrderItem> ordersItems);
    
    
    List<OrderItem> toOrderItemEntity(List<OrderItemDTO> orderItemsDTO);
}
