package com.idus.backpacker.core.user.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UserCreated {
    @NonNull private final UserId userId;
}
