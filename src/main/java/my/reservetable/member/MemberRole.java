package my.reservetable.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    OWNER("사장님"),
    USER("사용자");

    private final String value;
}
