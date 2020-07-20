package com.serviceorder.dto;

import com.serviceorder.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 11:03 AM
 * @created_by thuynt
 * @since 08/07/2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountTypeDTO extends AbstractDTO{
    private int typeId;
    @NotBlank(message = "{notnull.account.type.name}")
    private String typeName;
    private int status;
    //private List<AccountDTO> accountDTOList;

}
