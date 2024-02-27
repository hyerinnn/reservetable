package my.reservetable.member.service;

import my.reservetable.member.dto.request.OwnerUpdateRequest;
import my.reservetable.member.dto.response.OwnerResponse;
import my.reservetable.member.dto.request.OwnerSignupRequest;
import my.reservetable.member.domain.Owner;
import my.reservetable.member.repository.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


//@SpringBootTest
//@Transactional
//@Rollback(false)
@ExtendWith(MockitoExtension.class) //Mockito를 사용하여 테스트 클래스 초기화
class OwnerServiceTest {

    @InjectMocks OwnerService ownerService;  //@Mock으로 선언된 가짜 객체들을 의존한 service객체 생성
    @Mock OwnerRepository ownerRepository;   // 모의객체생성 (repository bean에 의존하지 않고 테스트 가능)

    @Test
    @DisplayName("사장님 회원가입")
    void signupOwner(){
        //given
        OwnerSignupRequest request = OwnerSignupRequest.builder()
                .ownerId("test002")
                .ownerName("사장님A")
                .password("1111")
                .email("abc@abc.com")
                .phoneNumber("01027374848")
                .build();

        //가입안된 id인지 확인  -> 이런 부분도 여기서 테스트 하는지?
        //when(ownerRepository.findByOwnerId(any(String.class))).thenReturn(Optional.empty());
        //given(ownerRepository.findByOwnerId(anyString())).willReturn(Optional.ofNullable(null));

        given(ownerRepository.save(any(Owner.class))).willReturn(createOwner());

        //when
        OwnerResponse newOwner = ownerService.signupOwner(request);

        //then
        assertThat(newOwner.getOwnerName()).isEqualTo(request.getOwnerName());
    }

    private Owner createOwner(){
        Owner owner = Owner.builder()
                .ownerId("test002")
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