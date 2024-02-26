package my.reservetable.member.service;

import my.reservetable.member.dto.request.OwnerUpdateRequest;
import my.reservetable.member.dto.response.OwnerResponse;
import my.reservetable.member.dto.request.OwnerSignupRequest;
import my.reservetable.member.entity.Owner;
import my.reservetable.member.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class OwnerServiceTest {

    @Autowired OwnerService ownerService;
    @Autowired OwnerRepository ownerRepository;

    @Test
    void signupOwner(){
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId("test002")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        OwnerResponse newOwner = ownerService.signupOwner(request);

        assertThat(newOwner.getOwnerName()).isEqualTo(request.getOwnerName());
    }

    @Test
    void findOwnerByOwnerId(){
        String id = "owner001";
        OwnerResponse owner = ownerService.findOwnerByOwnerId(id);
        assertThat(owner.getOwnerId()).isEqualTo(id);
    }

    @Test
    void updateOwner(){
        Owner owner = Owner.builder()
                .ownerId("test002")
                .ownerName("사장님B")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        ownerRepository.save(owner);

        OwnerUpdateRequest updateOwner = OwnerUpdateRequest.builder()
                .ownerId("jpa")
                .ownerName("jpa사장님")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        ownerService.updateOwner(owner.getId(), updateOwner);

        assertThat(owner.getOwnerId()).isEqualTo(updateOwner.getOwnerId());
        assertThat(owner.getOwnerName()).isEqualTo(updateOwner.getOwnerName());
    }

}