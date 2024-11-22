package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.OrderDTO;
import com.project.shopapp.DTO.OrderItemDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.enums.PAYMENT_STATUS;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderItem;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.TableReservation;
import com.project.shopapp.repositories.OrderItemRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.TableReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements com.project.shopapp.services.OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TableReservationRepository tableReservationRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        TableReservation tableReservation = tableReservationRepository.findById(orderDTO.getTableReservationId())
                .orElseThrow(() -> new DataNotFoundException("TableReservation not found"));
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now().toString());
        order.setReservation(tableReservation);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product food = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);

            // Tính tổng giá OrderItem rồi cộng vào totalPrice
            totalPrice = totalPrice.add(food.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setPaymentStatus(PAYMENT_STATUS.UNPAID);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> findByTableReservationId(Long id) {
        TableReservation tableReservation = tableReservationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("TableReservation not found"));
        List<Order> orders = orderRepository.findByReservation(tableReservation);
        return orders;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrder(OrderDTO orderDTO, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
        order.setOrderTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toString());

        orderItemRepository.deleteByOrder(order);

        List<OrderItem> updatedOrderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product food = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            // Tạo `OrderItem` mới
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);

            // Tính tổng giá cho `OrderItem` mới
            totalPrice = totalPrice.add(food.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            updatedOrderItems.add(orderItem);
        }
        orderItemRepository.saveAll(updatedOrderItems);

        order.setOrderItems(updatedOrderItems);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public Order changeStatus(Long id) throws InvalidParamException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Not found order"));
        if(existingOrder.getPaymentStatus().equals(PAYMENT_STATUS.UNPAID)) {
            existingOrder.setPaymentStatus(PAYMENT_STATUS.PAID);
        }else {
            throw new InvalidParamException("Order has been changed");
        }
        return orderRepository.save(existingOrder);
    }
}
