package com.arquiproject.svc_artisans.repository;

import com.arquiproject.svc_artisans.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
  Payment findByUserEmail(String userEmail);
}
