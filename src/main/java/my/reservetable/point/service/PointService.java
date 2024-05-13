package my.reservetable.point.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.point.domain.Point;
import my.reservetable.point.domain.PointType;
import my.reservetable.point.repository.PointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public int addPoint(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));

        Optional<Point> optionalPoint = pointRepository.findByMember(member);
        // 포인트 지급내역이 아예 없으면, 회원가입 포인트 지급
        Point pointEntity = optionalPoint.orElseGet(() ->
                pointRepository.save(
                        Point.builder()
                                .member(member)
                                .point(PointType.SIGNUP.getPoint())
                                .build()
                )
        );
        // 포인트 지급내역이 있는 경우, 일반 포인트 지급
        if(optionalPoint.isPresent()){
            pointEntity.addPoint(PointType.GENERAL.getPoint());
        }
        return pointEntity.getPoint();
    }

    @Transactional
    public int subtractPoint(Long memberId, int point){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));
        Point myPoint = pointRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("포인트정보가 없습니다."));

        myPoint.subtractPoint(point);
        return myPoint.getPoint();
    }
}

