package com.auth.ganak.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Loan.
 */
@Entity
@Table(name = "loan")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "loanee_name")
    private String loaneeName;

    @Column(name = "dob")
    private Long dob;

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private String contact;

    @Column(name = "disbursement_date")
    private LocalDate disbursementDate;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "loan_tenure")
    private Double loanTenure;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "zkp_code")
    private String zkpCode;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "updated_by_id")
    private Long updatedById;

    @OneToMany(mappedBy = "repayement")
    private Set<Repayement> repayements = new HashSet<>();

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Agreement agreement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoaneeName() {
        return loaneeName;
    }

    public Loan loaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
        return this;
    }

    public void setLoaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
    }

    public Long getDob() {
        return dob;
    }

    public Loan dob(Long dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public Loan address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public Loan contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public Loan disbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
        return this;
    }

    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public Loan loanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getLoanTenure() {
        return loanTenure;
    }

    public Loan loanTenure(Double loanTenure) {
        this.loanTenure = loanTenure;
        return this;
    }

    public void setLoanTenure(Double loanTenure) {
        this.loanTenure = loanTenure;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Loan interestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getZkpCode() {
        return zkpCode;
    }

    public Loan zkpCode(String zkpCode) {
        this.zkpCode = zkpCode;
        return this;
    }

    public void setZkpCode(String zkpCode) {
        this.zkpCode = zkpCode;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Loan dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public Loan createdById(Long createdById) {
        this.createdById = createdById;
        return this;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Loan dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public Loan updatedById(Long updatedById) {
        this.updatedById = updatedById;
        return this;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public Set<Repayement> getRepayements() {
        return repayements;
    }

    public Loan repayements(Set<Repayement> repayements) {
        this.repayements = repayements;
        return this;
    }

    public Loan addRepayement(Repayement repayement) {
        this.repayements.add(repayement);
        repayement.setRepayement(this);
        return this;
    }

    public Loan removeRepayement(Repayement repayement) {
        this.repayements.remove(repayement);
        repayement.setRepayement(null);
        return this;
    }

    public void setRepayements(Set<Repayement> repayements) {
        this.repayements = repayements;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public Loan agreement(Agreement agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loan)) {
            return false;
        }
        return id != null && id.equals(((Loan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Loan{" +
            "id=" + getId() +
            ", loaneeName='" + getLoaneeName() + "'" +
            ", dob=" + getDob() +
            ", address='" + getAddress() + "'" +
            ", contact='" + getContact() + "'" +
            ", disbursementDate='" + getDisbursementDate() + "'" +
            ", loanAmount=" + getLoanAmount() +
            ", loanTenure=" + getLoanTenure() +
            ", interestRate=" + getInterestRate() +
            ", zkpCode='" + getZkpCode() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdById=" + getCreatedById() +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", updatedById=" + getUpdatedById() +
            "}";
    }
}
