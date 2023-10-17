package com.chubanova;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
public class StartGameResponse {

    @JsonProperty("battleId")
    private UUID battleId;
    public StartGameResponse(UUID battleId) {
        this.battleId = battleId;

    }
}
