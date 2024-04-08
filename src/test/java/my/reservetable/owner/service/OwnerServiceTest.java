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
                .email("test@test.com")
                .nickName("사장님A")
                .password("1111")
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
        String email = "test001@test001.com";
        Owner owner = createOwner(email);
        ownerRepository.save(owner);

        OwnerSignupRequest existsOwnerRequest = OwnerSignupRequest.builder()
                .email("test001@test001.com")
                .nickName("사장님A")
                .password("1111")
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
        String email = "test001@test001.com";
        Owner owner = createOwner(email);
        Owner savedOwner = ownerRepository.save(owner);

        //when & then
        OwnerResponse ownerResponse = ownerService.findOwnerByOwnerId(savedOwner.getOwnerId());
        assertThat(owner.getEmail()).isEqualTo(ownerResponse.getEmail());
    }

    @Test
    @DisplayName("이메일로 사장님회원 조회")
    void findOwnerByEmail(){
        //given
        String email = "test001@test001.com";
        Owner owner = createOwner(email);
        ownerRepository.save(owner);

        //when & then
        OwnerResponse ownerResponse = ownerService.findOwnerByEmail(email);
        assertThat(ownerResponse.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("사장님 회원정보 수정 성공테스트")
    void updateOwner(){
        OwnerSignupRequest ownerRequest = OwnerSignupRequest.builder()
                .email("owner@owner.com")
                .nickName("사장님A")
                .password("1111")
                .phoneNumber("01027374848")
                .build();
        OwnerResponse owner = ownerService.signupOwner(ownerRequest);

        OwnerUpdateRequest updateOwner = OwnerUpdateRequest.builder()
                .ownerId(owner.getOwnerId())
                .nickName("jpa사장님")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();
        ownerService.updateOwner(updateOwner);

        assertThat(owner.getNickName()).isNotEqualTo(updateOwner.getNickName());
    }

    private Owner createOwner(String email){
        return Owner.builder()
                .nickName("사장님A")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .build();
    }
}