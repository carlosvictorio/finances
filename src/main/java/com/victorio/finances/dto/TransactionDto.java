package com.victorio.finances.dto;

import com.victorio.finances.enums.TypeEnum;

public record TransactionDto(Double value, TypeEnum type, String description) {}
