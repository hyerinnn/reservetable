package my.reservetable.shop.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShopStatus {

    READY("준비중"),
    OPEN("오픈"),
    BREAK_TIME("브레이크타임"),
    CLOSE("마감"),
    DAY_OFF("휴무");

    private final String text;
}
