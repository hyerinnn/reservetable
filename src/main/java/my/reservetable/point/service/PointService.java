package my.reservetable.point.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.point.domain.Point;
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

    private static final int signUpPoint = 50;
    private static final int generalPoint = 10;


    @Transactional
    public int addPoint(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));

        Optional<Point> myPoint = pointRepository.findByMember(member);

        //TODO: isPresent map, orElse 등으로 작업해보기

        // 포인트 정보가 없으면, 회원가입 포인트 지급
        Point pointEntity = myPoint.orElseGet(() ->{
                Point point = pointRepository.save(
                        Point.builder()
                                .member(member)
                                .point(signUpPoint)
                                .build()
                );
                return point;
            }
        );
        // 포인트 정보가 있는 경우
        if(myPoint.isPresent()){
            pointEntity.addPoint(generalPoint);
        }
        return pointEntity.getPoint();
    }

    @Transactional
    public int subtractPoint(Long memberId, int point){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));
        Point myPoint = pointRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("포인트정보가 없습니다."));

        if(!myPoint.checkPointAmount(point)){
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        myPoint.subtractPoint(point);
        return myPoint.getPoint();
    }
}

