package cs203t10.ryver.fts.account;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import cs203t10.ryver.fts.transaction.Transaction;


import lombok.*;

@Entity
@Getter @Setter @Builder(toBuilder = true)
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Account {

    @JsonProperty("id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    @JsonProperty("customer_id")
    @NotNull(message = "Customer ID cannot be null")
    private Integer customerId;

    @JsonProperty("balance")
    @NotNull(message = "Balance cannot be null")
    private Double balance;

    @JsonProperty("available_balance")
    @NotNull(message = "Available balance cannot be null")
    private Double availableBalance;

    @JsonIgnore
    @OneToMany(mappedBy = "senderAccountId", cascade = CascadeType.ALL)
    private List<Transaction> sentTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverAccountId", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransactions;
}

