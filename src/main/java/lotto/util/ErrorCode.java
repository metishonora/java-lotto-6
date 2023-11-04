package lotto.util;

public enum ErrorCode {
    PURCHASE_NOT_DIVISIBLE("구입 단위는 1000원이어야 합니다."),
    PURCHASE_NOT_POSITIVE("구입 금액은 양수여야 합니다."),
    PURCHASE_NOT_INTEGER("구입 금액은 숫자로 주어져야 합니다."),
    LOTTO_FORMAT("로또 번호가 형식에 맞지 않습니다."),
    LOTTO_ILLEGAL_NUMBER("로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    LOTTO_DUPLICATED("중복된 번호가 존재합니다."),
    LOTTO_ORDER("오름차순으로 입력해야 합니다."),
    BONUS_ILLEGAL_NUMBER("보너스 번호는 1부터 45 사이의 숫자여야 합니다."),
    BONUS_DUPLICATED("보너스 번호가 중복되었습니다.");

    private final String message;
    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}