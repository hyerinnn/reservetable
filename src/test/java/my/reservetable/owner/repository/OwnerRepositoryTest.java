package my.reservetable.owner.repository;

import my.reservetable.owner.domain.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@Transactional
//@Rollback(false)
class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @DisplayName("사장회원등록")
    @Test
    void ownerSaveOk() {
        // given
        Owner owner = Owner.builder()
                .email("owner@owner.com")
                .nickName("새로운사장님")
                .build();
        // when
        Owner newOwner = ownerRepository.save(owner);

        System.out.println("생성시간: " + newOwner.getCreatedDate());
        System.out.println("생성자: " + newOwner.getCreatedBy());

        // then
        assertThat(newOwner.getOwnerId()).isEqualTo(owner.getOwnerId());
    }
}