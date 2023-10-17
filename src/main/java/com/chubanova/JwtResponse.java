package com.chubanova;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    @JsonIgnore
    private static final long serialVersionUID = -8091879091924046844L;

    @JsonIgnore
    private final String jwttoken;

    private String token;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
        this.token = jwttoken;
    }

    public String getToken() {
        return this.token;
    }
}
