package com.chubanova;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCommandRequest {
    private Long operationId;
    private Object[] args;

    public Command toCommand() {
        return () -> {};
    }
}
