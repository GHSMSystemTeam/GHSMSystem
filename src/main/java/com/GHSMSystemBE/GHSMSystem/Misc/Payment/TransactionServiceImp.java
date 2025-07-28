package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepo repo;

    @Autowired
    public TransactionServiceImp(TransactionRepo repo) {
        this.repo = repo;
    }

    //get a list of all transaction
    @Override
    public List<Transaction> getAll() {
        return repo.findAll();
    }
    //get a list of transaction made by a customer
    @Override
    public List<Transaction> getByCustomerName(String customerName) {
        return repo.findAll(TransactionSpecification.findWithUserName(customerName));
    }
    //get a list of transaction according to payment status
    @Override
    public List<Transaction> getByPaymentStatus(String status) {
        return repo.findAll(TransactionSpecification.findWithPaymentStatus(status));
    }
    //get individual transaction
    @Override
    public Transaction getByOrderId(String orderId) {
        return repo.findById(orderId).orElse(null);
    }
}