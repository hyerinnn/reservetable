package my.reservetable.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.like.domain.Likes;
import my.reservetable.like.dto.LikeRequest;
import my.reservetable.like.dto.LikeResponse;
import my.reservetable.like.dto.LikesWithCountResponse;
import my.reservetable.like.repository.LikesRepository;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikesRepository likeRepository;
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createLike(LikeRequest request) {
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

        Optional<Likes> like = likeRepository.findByMemberAndShop(member, shop);
        if(like.isPresent()){
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        likeRepository.save(request.toEntity(member, shop));
    }

    @Transactional
    public void cancelLike(LikeRequest request) {
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

        Likes like = likeRepository.findByMemberAndShop(member, shop)
                .orElseThrow(() -> new NotFoundEntityException("좋아요 정보가 없습니다."));

        likeRepository.delete(like);
    }


    public LikesWithCountResponse getLikesByMember(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));

        int count = getCountLikesByMember(memberId);
        List<LikeResponse> likeResponses = likeRepository.findAllByMember(member)
                .stream().map(LikeResponse::toDto).collect(Collectors.toList());

        return LikesWithCountResponse.builder()
                .likes(likeResponses)
                .count(count)
                .build();
    }

    private int getCountLikesByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));
        return likeRepository.countByMember(member);
    }

    public int getCountLikesByShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));
        return likeRepository.countByShop(shop);
    }



    // ======================== 공부용(기록용) 좋아요/좋아요 취소 합친 API =====================
    @Transactional
    public void backUpLikeShop(LikeRequest request) {
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

        likeRepository.findByMemberAndShop(member, shop)
                .ifPresentOrElse(
                        //'좋아요'를 누른적이 있다면 '좋아요' 취소
                        likes -> {
                            likeRepository.deleteByMemberAndShop(member, shop);
                        },
                        //'좋아요'를 누른적이 없다면 '좋아요'
                        () -> {
                            likeRepository.save(
                                    Likes.builder()
                                            .member(member)
                                            .shop(shop)
                                            .build()
                            );
                        }
                );
    }

}
