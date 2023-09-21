package com.auth.ganak.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Repayement.
 */
@Entity
@Table(name = "repayement")
public class Repayement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "repayement_amount")
    private Double repayementAmount;

    @Column(name = "repayement_date")
    private LocalDate repayementDate;

    @Column(name = "outstanding_balance")
    private Double outstandingBalance;

    @Column(name = "repayement_status")
    private String repayementStatus;

    @Column(name = "zkp_code")
    private String zkpCode;

    @Column(name = "flag")
    private Boolean flag;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "updated_by_id")
    private Long updatedById;

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties("repayements")
    private Loan repayement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRepayementAmount() {
        return repayementAmount;
    }

    public Repayement repayementAmount(Double repayementAmount) {
        this.repayementAmount = repayementAmount;
        return this;
    }

    public void setRepayementAmount(Double repayementAmount) {
        this.repayementAmount = repayementAmount;
    }

    public LocalDate getRepayementDate() {
        return repayementDate;
    }

    public Repayement repayementDate(LocalDate repayementDate) {
        this.repayementDate = repayementDate;
        return this;
    }

    public void setRepayementDate(LocalDate repayementDate) {
        this.repayementDate = repayementDate;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public Repayement outstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
        return this;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getRepayementStatus() {
        return repayementStatus;
    }

    public Repayement repayementStatus(String repayementStatus) {
        this.repayementStatus = repayementStatus;
        return this;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setRepayementStatus(String repayementStatus) {
        this.repayementStatus = repayementStatus;
    }

    public String getZkpCode() {
        return zkpCode;
    }

    public Repayement zkpCode(String zkpCode) {
        this.zkpCode = zkpCode;
        return this;
    }

    public void setZkpCode(String zkpCode) {
        this.zkpCode = zkpCode;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Repayement dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public Repayement createdById(Long createdById) {
        this.createdById = createdById;
        return this;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Repayement dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public Repayement updatedById(Long updatedById) {
        this.updatedById = updatedById;
        return this;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public Loan getRepayement() {
        return repayement;
    }

    public Repayement repayement(Loan loan) {
        this.repayement = loan;
        return this;
    }

    public void setRepayement(Loan loan) {
        this.repayement = loan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repayement)) {
            return false;
        }
        return id != null && id.equals(((Repayement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Repayement{" +
                "id=" + id +
                ", repayementAmount=" + repayementAmount +
                ", repayementDate=" + repayementDate +
                ", outstandingBalance=" + outstandingBalance +
                ", repayementStatus='" + repayementStatus + '\'' +
                ", zkpCode='" + zkpCode + '\'' +
                ", flag=" + flag +
                ", dateCreated=" + dateCreated +
                ", createdById=" + createdById +
                ", dateUpdated=" + dateUpdated +
                ", updatedById=" + updatedById +
                '}';
    }
}
