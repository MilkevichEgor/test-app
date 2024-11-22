package com.fusion.dto.request;

import com.fusion.constant.UserRole;
import jakarta.validation.constraints.NotNull;

public record ChangeRole(@NotNull UserRole role) {
}
