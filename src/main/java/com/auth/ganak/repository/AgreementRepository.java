package com.auth.ganak.repository;

import com.auth.ganak.domain.Agreement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Agreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    @Query("SELECT a FROM Agreement a WHERE a.flag = true")
    List<Agreement> findAllFlaggedAgreement();

}
