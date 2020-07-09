package com.serviceorder.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 2:37 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private int status;
    private T data;

    public static APIResponse<Object> setDataForAPIResponse(Object data, String message, int status){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(data);
        apiResponse.setMessage(message);
        apiResponse.setStatus(status);
        return apiResponse;
    }

}
