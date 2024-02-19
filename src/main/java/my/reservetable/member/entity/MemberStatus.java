package my.reservetable.member.entity;

import lombok.Getter;

public enum MemberStatus {
    READY("대기중"),
    ACCEPT("수락");

    @Getter
    private final String desc;

    MemberStatus(String desc) {
        this.desc = desc;
    }

}
