package com.lg.fresher.lgcommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringResponse extends ResponseData{

    private String data;
}
