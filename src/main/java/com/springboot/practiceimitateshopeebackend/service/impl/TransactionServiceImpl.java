package com.springboot.practiceimitateshopeebackend.service.impl;

import com.springboot.practiceimitateshopeebackend.entity.Inventory;
import com.springboot.practiceimitateshopeebackend.entity.Order;
import com.springboot.practiceimitateshopeebackend.entity.Transaction;
import com.springboot.practiceimitateshopeebackend.repository.TransactionRepository;
import com.springboot.practiceimitateshopeebackend.security.JwtAuthenticationFilter;
import com.springboot.practiceimitateshopeebackend.service.TransactionService;
import com.springboot.practiceimitateshopeebackend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllCancelledOrders() {
        String username = JwtAuthenticationFilter.CURRENT_USER;
        return transactionRepository.findAll().stream()
                .filter(filter ->
                        filter.getCreatedBy().equals(username) &&
                                filter.getOrderStatus().equals(StringUtils.ORDER_CANCELLED))
                .toList();
    }

    @Override
    public List<Transaction> getAllCompletedOrders() {
        String username = JwtAuthenticationFilter.CURRENT_USER;
        return transactionRepository.findAll().stream()
                .filter(filter ->
                        filter.getCreatedBy().equals(username) &&
                                filter.getOrderStatus().equals(StringUtils.ORDER_DELIVERED))
                .toList();
    }

    public void saveCancelledOrder(Order order){

        Transaction transaction = new Transaction();
        transaction.setProductName(order.getProductName());
        transaction.setShopName(order.getShopName());
        transaction.setPrice(order.getPrice());
        transaction.setTotalAmount(order.getTotalAmount());
        transaction.setQuantity(order.getQuantity());
        transaction.setInventoryId(order.getInventoryId());
        transaction.setSize(order.getSize());
        transaction.setColor(order.getColor());
        transaction.setUser(order.getUser());
        transaction.setOrderStatus(StringUtils.ORDER_CANCELLED);

        transactionRepository.save(transaction);
    }

    public void saveCompletedOrder(Order order){

        Transaction transaction = new Transaction();
        transaction.setProductName(order.getProductName());
        transaction.setShopName(order.getShopName());
        transaction.setPrice(order.getPrice());
        transaction.setTotalAmount(order.getTotalAmount());
        transaction.setQuantity(order.getQuantity());
        transaction.setInventoryId(order.getInventoryId());
        transaction.setSize(order.getSize());
        transaction.setColor(order.getColor());
        transaction.setUser(order.getUser());
        transaction.setOrderStatus(StringUtils.ORDER_DELIVERED);
        transaction.setPaymentMethod(StringUtils.CASH_ON_DELIVERY);

        transactionRepository.save(transaction);

    }

}
