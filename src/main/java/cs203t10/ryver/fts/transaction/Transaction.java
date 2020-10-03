package cs203t10.ryver.fts.transaction;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import cs203t10.ryver.fts.account.Account;

import lombok.*;

@Entity
@Getter @Setter @Builder(toBuilder = true)
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Transaction {

  @JsonProperty("id")
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @JsonProperty("from")
  @ManyToOne(targetEntity = Account.class)
  @JoinColumn(name = "sender_account_id", nullable = false)
  private Account senderAccountId;

  @JsonProperty("to")
  @ManyToOne(targetEntity = Account.class)
  @JoinColumn(name = "receiver_account_id", nullable = false)
  private Account receiverAccountId;

  @JsonProperty("amount")
  @NotNull(message = "Transfer amount cannot be null")
  @DecimalMin(value = "0.01", message = "Transfer amount must be larger than 0")
  private Double amount;

}
