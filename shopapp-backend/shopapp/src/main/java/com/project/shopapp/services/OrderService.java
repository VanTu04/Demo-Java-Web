package com.project.shopapp.services;

import com.project.shopapp.DTO.OrderDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Order;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Order getOrder(Long id);
    void deleteOrder(Long id);
    List<Order> findByTableReservationId(Long id);
    List<Order> findAll();

    Order updateOrder(@Valid OrderDTO orderDTO, Long id);

    Order changeStatus(Long id) throws InvalidParamException;
}

