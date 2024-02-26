package my.reservetable.member.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.response.OwnerResponse;
import my.reservetable.member.dto.request.OwnerRequest;
import my.reservetable.member.entity.Owner;
import my.reservetable.member.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    /**
     * 사장 가입(등록)
     * */
    @Transactional
    public OwnerResponse signupOwner(OwnerRequest request){

        //TODO : 회원가입 밸리데이션 (시큐리티적용)
        Owner newOwner = ownerRepository.save(request.toEntity());
        return OwnerResponse.toDto(newOwner);
    }

    /**
     * 사장 정보 조회
     * */
    public OwnerResponse findOwnerById(Long id){
        return ownerRepository.findById(id)
                .map(owner -> OwnerResponse.toDto(owner))
                .orElseThrow(()-> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }


    /**
     * 가입한 ID로 사장 정보 조회
     * */
    public OwnerResponse findOwnerByOwnerId(String ownerId){
        return ownerRepository.findByOwnerId(ownerId)
                .map(owner -> OwnerResponse.toDto(owner))
                .orElseThrow(()-> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }

    /**
     * 사장 정보 수정
     * */
/*    public OwnerResponse updateOwner(Long id, OwnerRequest request){
        Owner owner = ownerRepository.getReferenceById(id);


        return ownerRepository.getReferenceById(id)
                .map(OwnerResponse::toDto)
                .orElseThrow(()-> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }
    */

}
