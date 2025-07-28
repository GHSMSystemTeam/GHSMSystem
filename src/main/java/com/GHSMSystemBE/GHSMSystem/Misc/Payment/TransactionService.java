package com.GHSMSystemBE.GHSMSystem.Misc.Payment;


import java.util.List;


public interface TransactionService{
    List<Transaction> getAll();
    List<Transaction> getByCustomerName(String customerId);
    List<Transaction> getByPaymentStatus(String status);
Transaction getByOrderId(String orderId);

}
