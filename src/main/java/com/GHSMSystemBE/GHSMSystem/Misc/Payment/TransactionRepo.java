package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,String> {
}
