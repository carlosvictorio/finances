package com.victorio.finances.dto;

import java.time.LocalDateTime;

import com.victorio.finances.enums.TypeEnum;

public record TransactionDto(Double value, TypeEnum type, String description, LocalDateTime date) {}
