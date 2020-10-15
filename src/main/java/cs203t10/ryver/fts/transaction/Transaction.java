package cs203t10.ryver.fts.transaction;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cs203t10.ryver.fts.account.Account;

import lombok.*;

@Entity
@Getter @Setter @Builder(toBuilder = true)
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Transaction {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(targetEntity = Account.class)
  @JoinColumn(name = "sender_account", nullable = false)
  private Account senderAccount;

  @ManyToOne(targetEntity = Account.class)
  @JoinColumn(name = "receiver_account")
  private Account receiverAccount;

  @NotNull(message = "Transfer amount cannot be null")
  @DecimalMin(value = "0.01", message = "Transfer amount must be larger than 0")
  private Double amount;

  @JsonIgnore
  private String status;

}
