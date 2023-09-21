package com.auth.ganak.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fintech_name")
    private String fintechName;

    @Column(name = "agreement_date")
    private LocalDate agreementDate;

    @Column(name = "fldg_perecentage")
    private Double fldgPerecentage;

    @Column(name = "fldg_amount")
    private Double fldgAmount;

    @Column(name = "guarantee_type")
    private String guaranteeType;

    @Column(name = "guarantee_details")
    private String guaranteeDetails;

    @Column(name = "agreement_no")
    private String agreementNo;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "flag")
    private Boolean flag;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "updated_by_id")
    private Long updatedById;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "agreement")
    private Set<Loan> loans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFintechName() {
        return fintechName;
    }

    public Agreement fintechName(String fintechName) {
        this.fintechName = fintechName;
        return this;
    }

    public void setFintechName(String fintechName) {
        this.fintechName = fintechName;
    }

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public Agreement agreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
        return this;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    public Double getFldgPerecentage() {
        return fldgPerecentage;
    }

    public Agreement fldgPerecentage(Double fldgPerecentage) {
        this.fldgPerecentage = fldgPerecentage;
        return this;
    }

    public void setFldgPerecentage(Double fldgPerecentage) {
        this.fldgPerecentage = fldgPerecentage;
    }

    public Double getFldgAmount() {
        return fldgAmount;
    }

    public Agreement fldgAmount(Double fldgAmount) {
        this.fldgAmount = fldgAmount;
        return this;
    }

    public void setFldgAmount(Double fldgAmount) {
        this.fldgAmount = fldgAmount;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public Agreement guaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
        return this;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getGuaranteeDetails() {
        return guaranteeDetails;
    }

    public Agreement guaranteeDetails(String guaranteeDetails) {
        this.guaranteeDetails = guaranteeDetails;
        return this;
    }

    public void setGuaranteeDetails(String guaranteeDetails) {
        this.guaranteeDetails = guaranteeDetails;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public Agreement agreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
        return this;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Agreement dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public Agreement createdById(Long createdById) {
        this.createdById = createdById;
        return this;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Agreement dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public Agreement updatedById(Long updatedById) {
        this.updatedById = updatedById;
        return this;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public Agreement loans(Set<Loan> loans) {
        this.loans = loans;
        return this;
    }

    public Agreement addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setAgreement(this);
        return this;
    }

    public Agreement removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setAgreement(null);
        return this;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agreement)) {
            return false;
        }
        return id != null && id.equals(((Agreement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", fintechName='" + fintechName + '\'' +
                ", agreementDate=" + agreementDate +
                ", fldgPerecentage=" + fldgPerecentage +
                ", fldgAmount=" + fldgAmount +
                ", guaranteeType='" + guaranteeType + '\'' +
                ", guaranteeDetails='" + guaranteeDetails + '\'' +
                ", agreementNo='" + agreementNo + '\'' +
                ", dateCreated=" + dateCreated +
                ", flag=" + flag +
                ", createdById=" + createdById +
                ", dateUpdated=" + dateUpdated +
                ", updatedById=" + updatedById +
                '}';
    }
}
