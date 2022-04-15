package com.idus.backpacker.api.user.presentation.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.idus.backpacker.api.BackpackerApiApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User > Presentation > Http > UserQueryController 테스트")
class UserQueryControllerTest extends BackpackerApiApplicationTest {

    @Test
    @DisplayName("회원 목록을 조회할 수 있어야 한다")
    void should_be_able_to_get_all_users() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 목록을 조회할 때 이름 필터링을 적용할 수 있어야 한다")
    void should_be_able_to_get_all_users_with_name_filter() throws Exception {
        params.add("name", "가나다");

        mockMvc.perform(get("/users").params(params)).andExpect(status().isOk());
    }
}
