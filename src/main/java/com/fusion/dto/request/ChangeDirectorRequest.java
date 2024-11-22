package com.fusion.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeDirectorRequest(@NotNull Long userId) {}
