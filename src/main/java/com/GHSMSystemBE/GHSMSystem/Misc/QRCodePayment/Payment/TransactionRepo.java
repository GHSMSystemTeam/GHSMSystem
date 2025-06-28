package com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,String> {
}
