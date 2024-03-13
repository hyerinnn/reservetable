package my.reservetable.config;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)  //Spring Data JPA에서 제공하는 이벤트 리스너로 엔티티의 영속, 수정 이벤트를 감지하는 역할
public class AuditingEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;     //생성일

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;   //수정일


    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy;            //생성자


    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;          //수정자

}
