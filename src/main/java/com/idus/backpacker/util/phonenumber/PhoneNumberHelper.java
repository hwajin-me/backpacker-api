package com.idus.backpacker.util.phonenumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhoneNumberHelper {
    private static final String NUMBER_DEFAULT_REGION = "KR";
    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    public static String format(String value) {
        return util.format(PhoneNumberHelper.parse(value), PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static boolean isValid(String number) {
        return PhoneNumberUtil.ValidationResult.IS_POSSIBLE.equals(
                PhoneNumberHelper.isValidReason(number));
    }

    public static PhoneNumberUtil.ValidationResult isValidReason(String number) {
        var result = util.isPossibleNumberWithReason(PhoneNumberHelper.parse(number));

        if (PhoneNumberUtil.ValidationResult.IS_POSSIBLE.equals(result)
                || PhoneNumberUtil.ValidationResult.IS_POSSIBLE_LOCAL_ONLY.equals(result)) {
            return PhoneNumberUtil.ValidationResult.IS_POSSIBLE;
        }

        return result;
    }

    private static Phonenumber.PhoneNumber parse(String value) {
        try {
            return util.parse(value, NUMBER_DEFAULT_REGION);

        } catch (NumberParseException e) {
            throw new PhoneNumberHelperException(
                    String.format("%s 번호 파싱에 실패하였습니다. [Reason=%s]", value, e.getErrorType()), e);
        }
    }
}
