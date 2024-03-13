package my.reservetable.owner.service;

import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


//@SpringBootTest
//@Transactional
//@Rollback(false)
@ExtendWith(MockitoExtension.class) //Mockito를 사용하여 테스트 클래스 초기화
class OwnerServiceTest {

    @InjectMocks OwnerService ownerService;  //@Mock으로 선언된 가짜 객체들을 의존한 service객체 생성
    @Mock OwnerRepository ownerRepository;   // 모의객체생성 (repository bean에 의존하지 않고 테스트 가능)

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

        // 이미 가입된 회원이 아닌 경우
        //given(ownerRepository.save(any(Owner.class))).willReturn(createOwner());
        given(ownerRepository.findByOwnerId("test001")).willReturn(Optional.empty());


        //when
        OwnerResponse newOwner = ownerService.signupOwner(request);

        //then
        assertThat(newOwner.getOwnerName()).isEqualTo(request.getOwnerName());
    }

    @Test
    @DisplayName("사장님 회원가입 실패 테스트 -> 이미 가입된 id인 경우")
    void signupOwnerWhenAlreadyRegistered(){
        //given
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId("alreadyOwner")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        // 이미 가입된 회원인 경우
        Owner existingOwner = mock(Owner.class);
        given(ownerRepository.findByOwnerId("alreadyOwner")).willReturn(Optional.of(existingOwner));

        //when
        
        //then
        assertThatThrownBy(() -> ownerService.signupOwner(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 가입된 회원입니다.");    }

    private Owner createOwner(){
        Owner owner = Owner.builder()
                .ownerId("test001")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        ReflectionTestUtils.setField(owner, "id", 3L);
        return owner;
    }

    @Test
    void findOwnerByOwnerId(){
        String id = "owner001";
        OwnerResponse owner = ownerService.findOwnerByOwnerId(id);
        assertThat(owner.getOwnerId()).isEqualTo(id);
    }

    @Test
    @DisplayName("사장님 회원정보 수정 성공테스트")
    void updateOwner(){
        Owner owner = Owner.builder()
                .ownerId("test001")
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