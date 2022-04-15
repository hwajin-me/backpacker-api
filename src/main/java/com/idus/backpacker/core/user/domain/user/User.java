package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.AggregateRoot;
import com.idus.backpacker.core.kernel.domain.share.BaseAggregateRoot;
import com.idus.backpacker.core.kernel.domain.share.Email;
import com.idus.backpacker.core.kernel.domain.share.PhoneNumber;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AggregateRoot
@Getter
@ToString
@Entity
@Table(name = "user_bcs_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at is null")
public class User extends BaseAggregateRoot<User, UserId> implements UserDetails {

    @NonNull
    @Column(nullable = false, length = 20)
    private UserName name;

    @NonNull
    @Column(nullable = false, length = 30)
    private UserNickName nickName;

    @NonNull
    @Column(nullable = false, length = 128)
    private String password;

    @NonNull
    @Column(nullable = false, length = 100)
    private Email email;

    @NonNull
    @Column(nullable = false, length = 20)
    private PhoneNumber phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "information_id")
    private UserInformation information;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private UserOrder order;

    User(
            @NonNull UserId id,
            @NonNull UserName name,
            @NonNull UserNickName nickName,
            @NonNull String password,
            @NonNull Email email,
            @NonNull PhoneNumber phoneNumber,
            UserInformation information) {
        super(id);
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.information = information;

        registerEvent(UserCreated.builder().userId(this.id).build());
    }

    /**
     * 사용자가 주문한 정보를 기록한다.
     *
     * @param order 사용자가 주문한 정보
     */
    public void recordOrder(@NonNull UserOrder order) {
        this.order = order;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.email.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
