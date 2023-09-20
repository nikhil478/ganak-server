package com.auth.ganak.repository;

import com.auth.ganak.domain.Repayement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Repayement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepayementRepository extends JpaRepository<Repayement, Long> {

}
