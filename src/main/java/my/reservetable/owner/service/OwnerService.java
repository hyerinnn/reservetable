package my.reservetable.owner.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.DuplicateMemberException;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerResponse signupOwner(OwnerSignupRequest request) {
        validateDuplicateOwner(request.getOwnerId());
        Owner newOwner = ownerRepository.save(request.toEntity());
        return OwnerResponse.toDto(newOwner);
    }

    private void validateDuplicateOwner(String ownerId) {
        Optional<Owner> findOwners = ownerRepository.findById(ownerId);
        if (findOwners.isPresent()) {
            throw new DuplicateMemberException("이미 가입된 회원입니다.");
        }
    }

    public OwnerResponse findOwnerByOwnerId(String ownerId) {
        return ownerRepository.findById(ownerId)
                .map(owner -> OwnerResponse.toDto(owner))
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public OwnerResponse updateOwner(OwnerUpdateRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NotExistMemberException("회원정보를 찾을 수 없습니다."));

        owner.update(
                request.getOwnerId(),
                request.getNickName(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getEmail()
        );
        return OwnerResponse.toDto(owner);
    }
}
