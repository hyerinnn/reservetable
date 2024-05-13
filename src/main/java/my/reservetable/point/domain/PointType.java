package my.reservetable.point.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

    SIGNUP("회원가입", 50),
    GENERAL("일반", 10);

    private final String text;
    private final int point;
}
