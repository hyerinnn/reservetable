package my.reservetable.member.service;

import my.reservetable.member.dto.response.OwnerResponse;
import my.reservetable.member.dto.request.OwnerRequest;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    void signupOwner(){
        OwnerRequest request = OwnerRequest.builder()
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
        //System.out.println("owner.getOwnerName() = " + owner.getOwnerName());
        assertThat(owner.getOwnerId()).isEqualTo(id);
    }

    @Test
    void updateOwner(){

        Long id = 1L;
        OwnerResponse findOwner = ownerService.findOwnerById(id);

        OwnerRequest request = OwnerRequest.builder()
                .ownerId("test002")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();


        OwnerRequest updateOwner = OwnerRequest.builder()
                .ownerId("수정아이디")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        OwnerResponse owner = ownerService.updateOwner(updateOwner);
        assertThat(owner.getOwnerId()).isEqualTo("수정아이디");

    }

}