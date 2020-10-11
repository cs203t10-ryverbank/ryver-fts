package cs203t10.ryver.fts.transaction.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
public class TransactionInfoWhenAcceptingPending {

  @JsonProperty("to")
  @NotNull(message = "Receiver account id cannot be null")
  private Integer receiverAccountId;


}