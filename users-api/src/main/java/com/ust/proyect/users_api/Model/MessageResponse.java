package com.ust.proyect.users_api.Model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MessageResponse implements Serializable {
    private String message;
    private Object object;

}
