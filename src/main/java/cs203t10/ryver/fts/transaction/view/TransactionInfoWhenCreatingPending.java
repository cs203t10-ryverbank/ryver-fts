package cs203t10.ryver.fts.transaction.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
public class TransactionInfoWhenCreatingPending {
  @JsonProperty("from")
  @NotNull(message = "Sender account id cannot be null")
  private Integer senderAccountId;

  @JsonProperty("amount")
  @NotNull(message = "Transfer amount cannot be null")
  @DecimalMin(value = "0.01", message = "Transfer amount must be larger than 0")
  private Double amount;

}