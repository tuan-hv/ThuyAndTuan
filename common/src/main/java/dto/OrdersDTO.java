package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends AbstractDTO {
    private int ordersId;
    private double totalPrice;

    private List<OrderdetailDTO> orderDetailEntities;
}
