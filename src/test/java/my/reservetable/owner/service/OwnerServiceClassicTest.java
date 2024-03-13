package my.reservetable.owner.service;

import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.repository.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
@Rollback(false)
class OwnerServiceClassicTest {

    @Autowired OwnerService ownerService;
    @Autowired OwnerRepository ownerRepository;

    @Test
    @DisplayName("사장님 회원가입 성공테스트")
    void signupOwnerSuccess(){
        //given
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId("test001")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        //when
        OwnerResponse newOwner = ownerService.signupOwner(request);

        //then
        assertThat(newOwner.getOwnerName()).isEqualTo(request.getOwnerName());
    }

    @Test
    @DisplayName("이미 가입된 id인 경우 사장님 회원가입 실패 테스트")
    void signupOwnerWhenAlreadyRegistered(){
        //given
        OwnerSignupRequest ownerRequest = OwnerSignupRequest.builder()
                .ownerId("test001")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        OwnerResponse owner = ownerService.signupOwner(ownerRequest);

        OwnerSignupRequest existsOwnerRequest = OwnerSignupRequest.builder()
                .ownerId("test001")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        //when & then
        assertThatThrownBy(() -> ownerService.signupOwner(existsOwnerRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 가입된 회원입니다.");    }


    @Test
    @DisplayName("ownerId로 사장님회원 조회")
    void findOwnerByOwnerId(){
        //given
        String id = "test001";
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId(id)
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        ownerService.signupOwner(request);

        //when & then
        OwnerResponse owner = ownerService.findOwnerByOwnerId(id);
        assertThat(owner.getOwnerId()).isEqualTo(id);
    }

    @Test
    @DisplayName("사장님 회원정보 수정 성공테스트")
    void updateOwner(){
        OwnerSignupRequest ownerRequest = OwnerSignupRequest.builder()
                .ownerId("test001")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        OwnerResponse owner = ownerService.signupOwner(ownerRequest);

        OwnerUpdateRequest updateOwner = OwnerUpdateRequest.builder()
                .ownerId("test001")
                .ownerName("jpa사장님")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        ownerService.updateOwner(updateOwner);

        assertThat(owner.getOwnerName()).isNotEqualTo(updateOwner.getOwnerName());
    }

}