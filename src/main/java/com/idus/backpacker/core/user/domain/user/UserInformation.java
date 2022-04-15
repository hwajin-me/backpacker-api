package com.idus.backpacker.core.user.domain.user;

import com.idus.backpacker.core.kernel.domain.share.BaseAggregateRootSubDomain;
import javax.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "user_bcs_user_information")
public class UserInformation extends BaseAggregateRootSubDomain<User> {

    /** 생물학적 성별 */
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 8)
    private UserSex sex;

    public static UserInformation of(@NonNull UserSex sex) {
        return new UserInformation(sex);
    }

    private UserInformation(UserSex sex) {
        this.sex = sex;
    }
}
