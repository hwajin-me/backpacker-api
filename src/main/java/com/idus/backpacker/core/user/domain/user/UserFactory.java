package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.AggregateRootFactory;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import lombok.*;

@RequiredArgsConstructor
public class UserFactory implements AggregateRootFactory<User> {
    private final Data data;

    @Override
    public User create() {
        return new User(
                this.data.id,
                this.data.name,
                this.data.nickName,
                this.data.password,
                this.data.email,
                this.data.phoneNumber,
                this.data.information);
    }

    @Builder
    public static class Data {
        @NonNull private final UserId id;

        @NonNull private final UserName name;

        @NonNull private final UserNickName nickName;

        @NonNull private final String password;

        @NonNull private final Email email;

        @NonNull private final PhoneNumber phoneNumber;

        private final UserInformation information;
    }
}
