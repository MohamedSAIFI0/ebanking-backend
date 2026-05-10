package net.saifi.ebankingbackend.dtos;

import net.saifi.ebankingbackend.enums.OperationType;
import lombok.Data;

import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;

    private OperationType type;

}
