package my.reservetable.waiting.repository;

import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    // TODO :  mysql인 경우 cast대신 date로 작성 ( CAST(w.registeredDateTime AS date) -> DATE(w.registeredDateTime) )
    List<Waiting> findByUserId(Long userId);

    @Query("SELECT w FROM Waiting w WHERE w.userId = :userId AND w.shop.shopId = :shopId AND w.waitingStatus = :status")
    Optional<Waiting> findByUserIdAndShopIdAndWaitingStatus(Long userId, Long shopId, WaitingStatus status);

    @Query("SELECT w FROM Waiting w WHERE CAST(w.registeredDateTime AS date) = CURRENT_DATE AND w.shop.shopId = :shopId AND w.waitingStatus = :status")
    List<Waiting> getNowWaitingsByShopId(Long shopId, WaitingStatus status);

    @Query("SELECT count (*) FROM Waiting w WHERE CAST(w.registeredDateTime AS date) = CURRENT_DATE AND w.shop.shopId = :shopId AND w.waitingStatus = :status")
    int getCountNowWaitingsByShopId(Long shopId, WaitingStatus status);

    @Query("SELECT w FROM Waiting w WHERE w.shop.shopId = :shopId")
    List<Waiting> getListAllWaitingByShopId(Long shopId);

    // [웨이팅번호] 오늘기준, 상태와 상관없이 특정시간(웨이팅 생성시간)보다 먼저 생성된 웨이팅 수 조회
    @Query("SELECT count (*) FROM Waiting w WHERE w.registeredDateTime < :registeredDateTime AND CAST(w.registeredDateTime AS date) = :today")
    int getRegisteredDateTimeBefore(@Param("registeredDateTime") LocalDateTime registeredDateTime, @Param("today") LocalDate today);

    // [나의 웨이팅보다 앞선 웨이팅 수] 오늘 기준, 상태가 READY인 데이터 중, 특정 웨이팅생성 시간보다 먼저 생성된 웨이팅 수 조회
    @Query("SELECT COUNT(w) FROM Waiting w WHERE w.waitingStatus = :status AND w.registeredDateTime < :registeredDateTime " +
            "AND CAST(w.registeredDateTime AS date) = :today")
    int countByStatusAndToday(@Param("status") WaitingStatus status,
                               @Param("registeredDateTime") LocalDateTime registeredDateTime,
                               @Param("today") LocalDate today);


}
