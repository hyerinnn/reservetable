package my.reservetable.owner.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    /**
     * 사장 가입(등록)
     * */
    @Transactional
    public OwnerResponse signupOwner(OwnerSignupRequest request){

        //TODO : 회원가입 밸리데이션 (시큐리티적용)
        if (ownerRepository.findByOwnerId(request.getOwnerId()).isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        Owner newOwner = ownerRepository.save(request.toEntity());
        return OwnerResponse.toDto(newOwner);
    }

    /**
     * 기본키인 id로 사장 정보 조회
     * */
    public OwnerResponse findOwnerById(Long id){
        return ownerRepository.findById(id)
                .map(owner -> OwnerResponse.toDto(owner))
                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    /**
     * 가입한 ID로 사장 정보 조회
     * */
    public OwnerResponse findOwnerByOwnerId(String ownerId){
        return ownerRepository.findByOwnerId(ownerId)
                .map(owner -> OwnerResponse.toDto(owner))
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    /**
     * 사장 정보 수정
     * */
    public OwnerResponse updateOwner(OwnerUpdateRequest request){
        Owner owner = ownerRepository.findByOwnerId(request.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        owner.update(
                request.getOwnerId(),
                request.getOwnerName(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getEmail()
        );
        return OwnerResponse.toDto(owner);
    }
}
