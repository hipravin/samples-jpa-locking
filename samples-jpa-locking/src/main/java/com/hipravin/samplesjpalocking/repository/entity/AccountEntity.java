package com.hipravin.samplesjpalocking.repository.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
@NamedQueries({
        @NamedQuery(name = "AccountEntity.findByIdsIn",
                query="select a from AccountEntity a join fetch a.client where a.client.clientId in (:accountIds)")
})
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdSeq")
    @SequenceGenerator(sequenceName = "ACCOUNT_ACCOUNT_ID_SEQ", allocationSize = 100, name = "accountIdSeq")
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    @Basic
    @Column(name = "AVAILABLE_AMOUNT")
    private BigDecimal availableAmount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long clientId) {
        this.accountId = clientId;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }
}
