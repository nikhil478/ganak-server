package com.auth.ganak.repository;

import com.auth.ganak.domain.Loan;
import com.auth.ganak.domain.Repayement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Repayement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepayementRepository extends JpaRepository<Repayement, Long> {

    @Query("SELECT a FROM Repayement a WHERE a.flag = true")
    List<Repayement> findAllFlaggedRepayement();

}
