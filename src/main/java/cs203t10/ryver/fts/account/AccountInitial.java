package cs203t10.ryver.fts.account;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import cs203t10.ryver.fts.transaction.Transaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Hidden
public class AccountInitial {

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonProperty("customer_id")
	@NotNull(message = "Customer ID cannot be null")
	private Integer customerId;

	@JsonProperty("balance")
	@NotNull(message = "Balance cannot be null")
	private Double balance;

	@JsonIgnore
	@OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
	private List<Transaction> sentTransactions;

	@JsonIgnore
	@OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransactions;
    
    public Account toAccount() {
        Account account = Account.builder()
                            .customerId(customerId)
                            .balance(balance)
                            .availableBalance(balance)
                            .build();
        return account;
    }

}
