package com.auth.ganak.repository;

import com.auth.ganak.domain.Agreement;
import com.auth.ganak.domain.Loan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Loan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT a FROM Loan a WHERE a.flag = true")
    List<Loan> findAllFlaggedLoan();

}
