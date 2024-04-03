package my.reservetable.owner.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.exception.DuplicateMemberException;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.repository.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


//@SpringBootTest
//@Transactional
class OwnerServiceTest extends IntegrationTestSupport {

    @Autowired OwnerService ownerService;
    @Autowired OwnerRepository ownerRepository;

    //TODO: OwnerSignupRequest 빌더부분을 따로 메소드로 빼기

    @Test
    @DisplayName("사장님 회원가입 성공테스트")
    void signupOwnerSuccess(){
        //given
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId("test001")
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        //when
        OwnerResponse newOwner = ownerService.signupOwner(request);

        //then
        assertThat(newOwner.getNickName()).isEqualTo(request.getNickName());
    }

    @Test
    @DisplayName("중복 id인 경우 회원가입 실패 테스트")
    void signupOwnerWhenAlreadyRegistered(){
        //given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);

        OwnerSignupRequest existsOwnerRequest = OwnerSignupRequest.builder()
                .ownerId("test001")
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        //when & then
        assertThatThrownBy(() -> ownerService.signupOwner(existsOwnerRequest))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage("이미 가입된 회원입니다.");
    }

    @Test
    @DisplayName("ownerId로 사장님회원 조회")
    void findOwnerByOwnerId(){
        //given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);

        //when & then
        OwnerResponse ownerResponse = ownerService.findOwnerByOwnerId(ownerId);
        assertThat(ownerResponse.getOwnerId()).isEqualTo(ownerId);
    }

    @Test
    @DisplayName("사장님 회원정보 수정 성공테스트")
    void updateOwner(){
        OwnerSignupRequest ownerRequest = OwnerSignupRequest.builder()
                .ownerId("test001")
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        OwnerResponse owner = ownerService.signupOwner(ownerRequest);

        OwnerUpdateRequest updateOwner = OwnerUpdateRequest.builder()
                .ownerId("test001")
                .nickName("jpa사장님")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        ownerService.updateOwner(updateOwner);

        assertThat(owner.getNickName()).isNotEqualTo(updateOwner.getNickName());
    }

    private Owner createOwner(String ownerId){
        return Owner.builder()
                .ownerId(ownerId)
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
    }
}