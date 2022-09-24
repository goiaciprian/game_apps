package com.gameapi.DTOs;

import com.gameapi.Models.WSResponseTypes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSResponse <T>{
    private WSResponseTypes type;
    private T content;
}
