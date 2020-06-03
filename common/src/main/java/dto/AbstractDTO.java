package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date createdAt;
    private Date updatedAt;
    private int status;
}
