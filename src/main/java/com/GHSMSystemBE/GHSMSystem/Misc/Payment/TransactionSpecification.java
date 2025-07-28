package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
public static Specification<Transaction> findWithUserName(String username)
{
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"),username);
}

    public static Specification<Transaction> findWithPaymentStatus(String paymentStatus)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"),paymentStatus);
    }

}
