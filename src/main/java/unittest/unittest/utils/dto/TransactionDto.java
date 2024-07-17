package unittest.unittest.utils.dto;

import lombok.*;
import unittest.unittest.model.Customer;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Integer customerId;
    private String productName;
    private Integer quantity;
    private Integer price;
}
