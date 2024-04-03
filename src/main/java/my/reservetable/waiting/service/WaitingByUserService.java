package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.NoRegisterWaitingException;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.request.MyWaitingRequest;
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.request.WaitingStatusUpdateRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingByUserService {

    private final WaitingRepository waitingRepository;
    private final ShopRepository shopRepository;
    LocalDate today = LocalDate.now();

    public WaitingResponse registerWaiting(WaitingRegisterRequest request) {
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new NotFoundEntityException("매장을 찾을 수 없습니다."));

        validationWaitingEnable(shop, request);
        Waiting waiting = waitingRepository.save(request.toEntity(shop));
        return WaitingResponse.toDto(waiting, getMyWaitingNumber(request.getRegisteredDateTime()), getMyWaitingOrder(request.getRegisteredDateTime()));
    }

    public WaitingResponse getMyNowWaiting(MyWaitingRequest request){
        Waiting waiting = waitingRepository.findByUserIdAndShopId(request.getUserId(), request.getShopId(), WaitingStatus.READY)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));

        return waitingRepository.findByUserIdAndShopId(request.getUserId(), request.getShopId(), WaitingStatus.READY)
                .map(myWaiting -> WaitingResponse.toDto(myWaiting, getMyWaitingNumber(waiting.getRegisteredDateTime()), getMyWaitingOrder(waiting.getRegisteredDateTime())))
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
    }

    public List<WaitingResponse> getAllMyWaitings(Long userId){
        return waitingRepository.findByUserId(userId)
                .stream()
                .map(WaitingResponse::toDto)
                .collect(Collectors.toList());
    }

    public WaitingResponse changeWaitingStatus(WaitingStatusUpdateRequest request) {
        Waiting waiting = waitingRepository.findById(request.getWaitingId())
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
        waiting.changeWaitingStatus(request.getWaitingStatus());
        return WaitingResponse.toDto(waiting);
    }

    private void validationWaitingEnable(Shop shop, WaitingRegisterRequest request) {
        // 1. 가게가 웨이팅을 열어놨는지 확인 (waitingYn = Y)
        if ("N".equals(shop.getWaitingYn())) {
            throw new NoRegisterWaitingException("웨이팅을 할 수 없습니다.");
        }
        //2. 가게가 운영중인 상태인지 확인 (status = open)
        if (ShopStatus.OPEN != shop.getStatus()) {
            throw new NoRegisterWaitingException("웨이팅을 할 수 없습니다.");
        }
        //3. 이미 웨이팅한 유저인지 확인
        Optional<Waiting> findWaitingByUserId = waitingRepository.findByUserIdAndShopId(request.getUserId(), request.getShopId(), WaitingStatus.READY);
        if (findWaitingByUserId.isPresent()) {
            throw new NoRegisterWaitingException("이미 웨이팅한 회원입니다.");
        }
        //4. 마지막 주문가능 시간(lastOrderTime)보다 이전 인지 확인
        if (((request.getRegisteredDateTime()).toLocalTime()).isAfter(shop.getLastOrderTime())) {
            throw new NoRegisterWaitingException("웨이팅을 할 수 없습니다.");
        }
    }

    private int getMyWaitingNumber(LocalDateTime registeredDateTime) {
        int waitingNumber = waitingRepository.getRegisteredDateTimeBefore(registeredDateTime, today);
        return waitingNumber + 1;
    }

    private int getMyWaitingOrder(LocalDateTime registeredDateTime) {
        int myOrderBefore = waitingRepository.countByStatusAndToday(WaitingStatus.READY, registeredDateTime, today);
        return myOrderBefore + 1;
    }
}
