package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.NoRegisterWaitingException;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingByUserService {

    private final WaitingRepository waitingRepository;
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public WaitingResponse registerWaiting(WaitingRegisterRequest request) {
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new NotFoundEntityException("매장을 찾을 수 없습니다."));
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

        validationWaitingEnable(shop, request);
        int waitingNumber = createWaitingNumber(request.getRegisteredDateTime());
        Waiting waiting = waitingRepository.save(request.toEntity(shop, member, waitingNumber));
        return WaitingResponse.toDto(waiting, getMyWaitingOrder(request.getRegisteredDateTime()));
    }

    public WaitingResponse getMyNowWaiting(Long memberId, Long shopId){
        return waitingRepository.findByUserIdAndShopIdAndWaitingStatus(memberId, shopId, WaitingStatus.READY)
                .map(myWaiting -> WaitingResponse.toDto(myWaiting, getMyWaitingOrder(myWaiting.getRegisteredDateTime())))
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
    }

    public List<WaitingResponse> getAllMyWaitings(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));
        return waitingRepository.findByMember(member)
                .stream()
                .map(WaitingResponse::toDto)
                .collect(Collectors.toList());
    }

    private void validationWaitingEnable(Shop shop, WaitingRegisterRequest request) {
        // 1. 가게가 웨이팅을 열어놨는지 확인 (waitingYn = Y)
        if ("N".equals(shop.getWaitingYn())) {
            throw new NoRegisterWaitingException("웨이팅을 할 수 없습니다.");
        }
        //2. 가게가 운영중인 상태인지 확인 (status = open)
        if (ShopStatus.OPEN != shop.getStatus()) {
            throw new NoRegisterWaitingException("가게가 운영중이지 않아서 웨이팅을 할 수 없습니다.");
        }
        //3. 이미 웨이팅중인 유저인지 확인
        Optional<Waiting> findWaitingByUserId = waitingRepository.findByUserIdAndShopIdAndWaitingStatus(request.getMemberId(), request.getShopId(), WaitingStatus.READY);
        if (findWaitingByUserId.isPresent()) {
            throw new NoRegisterWaitingException("이미 웨이팅한 회원입니다.");
        }
        //4. 마지막 주문가능 시간(lastOrderTime)보다 이전 인지 확인
        if (((request.getRegisteredDateTime()).toLocalTime()).isAfter(shop.getLastOrderTime())) {
            throw new NoRegisterWaitingException("주문시간이 마감되어 웨이팅을 할 수 없습니다.");
        }
    }

    private int createWaitingNumber(LocalDateTime registeredDateTime) {
        LocalDate today = LocalDate.now();
        int waitingNumber = waitingRepository.getRegisteredDateTimeBefore(registeredDateTime, today);
        return waitingNumber + 1;
    }

    private int getMyWaitingOrder(LocalDateTime registeredDateTime) {
        LocalDate today = LocalDate.now();
        int myOrderBefore = waitingRepository.countByStatusAndToday(WaitingStatus.READY, registeredDateTime, today);
        return myOrderBefore + 1;
    }
}
