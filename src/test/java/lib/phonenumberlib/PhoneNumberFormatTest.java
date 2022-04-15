package lib.phonenumberlib;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Library > Phonenumberlib > Formatter 테스트")
public class PhoneNumberFormatTest {
    private PhoneNumberUtil lib;

    @BeforeEach
    void setUp() {
        lib = PhoneNumberUtil.getInstance();
    }

    @Test
    @DisplayName("국가 코드가 있는 한국 번호의 포멧팅이 되어야 한다")
    void should_be_able_to_format_code_from_kr_number_with_national() throws NumberParseException {
        var result =
                lib.format(lib.parse("+82 10-1234-5678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("국가 코드가 없는 한국 번호의 포멧팅이 되어야 한다")
    void should_be_able_to_format_code_from_kr_number() throws NumberParseException {
        var result =
                lib.format(lib.parse("010-1234-5678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("국가 코드가 있는 일본 번호의 포멧팅이 되어야 한다")
    void should_be_able_to_format_code_from_jp() throws NumberParseException {
        var result =
                lib.format(lib.parse("+81 80-1234-5678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+818012345678");
    }

    @Test
    @DisplayName("국가 코드가 없고 하이픈이 없는 한국 번호를 국가 코드가 포함되어 있는 번호로 포멧팅 할 수 있어야 한다")
    void should_be_able_to_format_code_no_hyphen_local_kr_number() throws NumberParseException {
        var result = lib.format(lib.parse("01012345678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("국가 코드가 없고 띄어쓰기로 되어 있는 한국 로컬 코드를 파싱할 수 있어야 한다")
    void should_be_able_to_format_code_no_hyphen_but_space() throws NumberParseException {
        var result =
                lib.format(lib.parse("010 1234 5678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("국가 코드가 있고 하이픈이 없는 한국 번호를 파싱할 수 있어야 한다")
    void should_be_able_to_parse_no_hyphen_kr_number() throws NumberParseException {
        var result =
                lib.format(lib.parse("+821012345678", "KR"), PhoneNumberUtil.PhoneNumberFormat.E164);

        assertThat(result).isEqualTo("+821012345678");
    }

    @Test
    @DisplayName("한국 로컬 번호의 하이픈이 있는 번호를 한국 로컬 번호의 하이픈이 있는 형태로 포멧팅 할 수 있어야 한다")
    void should_be_able_to_parse_hyphen_local_kr_number_to_hyphen_kr_number()
            throws NumberParseException {

        var result =
                lib.format(lib.parse("010-1234-5678", "KR"), PhoneNumberUtil.PhoneNumberFormat.NATIONAL);

        assertThat(result).isEqualTo("010-1234-5678");
    }
}
