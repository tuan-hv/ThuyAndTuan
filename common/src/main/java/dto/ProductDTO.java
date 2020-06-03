package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO extends AbstractDTO {
    private int productId;
    private String productName;
    private String image;
    private String description;
    private double price;

}
