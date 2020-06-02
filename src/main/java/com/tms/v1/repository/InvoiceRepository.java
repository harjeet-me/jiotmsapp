package com.tms.v1.repository;

import com.tms.v1.domain.Invoice;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByTrip_Id(Long tripId);

    public List<Invoice> findByCustomer_IdAndInvoiceDateBetween(Long customerId, LocalDate invoiceDateStart, LocalDate invoiceDateEnd);

    @Query("SELECT coalesce(max(inv.id), 0) FROM Invoice inv")
    Optional<Long> getMaxId();
}
