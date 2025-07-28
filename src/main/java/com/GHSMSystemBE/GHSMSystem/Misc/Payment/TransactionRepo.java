package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,String> {
    List<Transaction> findAll(Specification specification);
}
