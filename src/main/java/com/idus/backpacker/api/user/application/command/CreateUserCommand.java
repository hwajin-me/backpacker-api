package com.idus.backpacker.api.user.application.command;

import com.idus.backpacker.api.kernel.application.command.Command;
import com.idus.backpacker.api.kernel.application.command.CommandModel;
import com.idus.backpacker.api.user.application.exception.UserAlreadyExistsException;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import com.idus.backpacker.core.user.domain.user.*;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserCommand.Model> {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void invoke(@NonNull Model model) {
        if (this.repository.existsUserByEmail(Email.of(model.getEmail()))) {
            throw new UserAlreadyExistsException("이미 존재하는 회원입니다.");
        }

        final var data =
                UserFactory.Data.builder()
                        .id(UserId.of(model.getId()))
                        .name(UserName.of(model.getName()))
                        .nickName(UserNickName.of(model.getNickName()))
                        .password(this.passwordEncoder.encode(model.getPassword()))
                        .email(Email.of(model.getEmail()))
                        .phoneNumber(PhoneNumber.of(model.getPhoneNumber()));

        if (model.getSex() != null) {
            data.information(UserInformation.of(UserSex.fromString(model.getSex())));
        }

        final var user = new UserFactory(data.build()).create();

        this.repository.save(user);
    }

    @Builder
    @Getter
    public static class Model implements CommandModel {
        @NonNull private final UUID id;
        @NonNull private final String name;
        @NonNull private final String nickName;
        @NonNull private final String password;
        @NonNull private final String email;
        @NonNull private final String phoneNumber;
        private final String sex;
    }
}
