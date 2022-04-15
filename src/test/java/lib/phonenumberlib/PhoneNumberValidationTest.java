package lib.phonenumberlib;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Library > Phonenumberlib > Validation 테스트")
public class PhoneNumberValidationTest {
    private PhoneNumberUtil lib;

    @BeforeEach
    void setUp() {
        lib = PhoneNumberUtil.getInstance();
    }

    @Test
    @DisplayName("한국 번호가 국가 코드를 포함하고 있다면 정상이어야 한다")
    void should_be_validated_with_kr_national_number() {
        assertThat(lib.isPossibleNumber("+82 10-1234-5678", "KR")).isTrue();
    }

    @Test
    @DisplayName("한국 번호가 국가 코드를 포함하고 있지 않다면 정상이어야 한다")
    void should_be_validated_without_kr_national_number() {
        assertThat(lib.isPossibleNumber("010-1234-5678", "KR")).isTrue();
    }

    @Test
    @DisplayName("일본 번호가 국가 코드를 포함하고 있지 않다면 정상이어야 한다")
    void should_be_validated_jp_national_number() {
        assertThat(lib.isPossibleNumber("+81 10-1234-5678", "KR")).isTrue();
    }

    @Test
    @DisplayName("국가 코드 사이에 하이픈이 있어도 정상이어야 한다")
    void should_be_validated_When_national_code_with_hyphen() {
        assertThat(lib.isPossibleNumber("+82-10-1234-5678", "KR")).isTrue();
    }

    @Test
    @DisplayName("하이픈 없이 국가 코드와 전화 번호가 있으면 정상이어야 한다")
    void should_be_validated_When_no_hyphen() {
        assertThat(lib.isPossibleNumber("+821012345678", "KR")).isTrue();
    }

    @Test
    @DisplayName("하이픈 없이 국가 코드가 없는 로컬 전화 번호는 정상이어야 한다")
    void should_be_validated_When_no_hyphen_and_local_number() {
        assertThat(lib.isPossibleNumber("01012345678", "KR")).isTrue();
    }
}
