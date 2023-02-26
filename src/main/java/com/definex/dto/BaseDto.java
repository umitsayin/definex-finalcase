package com.definex.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseDto {
    private UUID id;
    private LocalDateTime createdAt;
}
