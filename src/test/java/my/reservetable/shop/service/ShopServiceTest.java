package my.reservetable.shop.service;

import my.reservetable.member.domain.Owner;
import my.reservetable.member.repository.OwnerRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.dto.ShopRegisterRequest;
import my.reservetable.shop.repository.ShopRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class ShopServiceTest {

    @Autowired ShopService shopService;
    @Autowired ShopRepository shopRepository;
    @Autowired OwnerRepository ownerRepository;

    @Test
    void testRegisterShop(){

        Owner owner = Owner.builder()
                .ownerName("테스트사장님")
                .build();

        ownerRepository.save(owner);

        Owner findowner = ownerRepository.getReferenceById(owner.getId());



        ShopRegisterRequest requestShop = ShopRegisterRequest.builder()
                .shopName("뉴샵")
                .build();


        //String newshop = shopService.registerShop(requestShop);
        Shop newshop = shopRepository.save(requestShop.toEntity(findowner));

        Assertions.assertThat(newshop.getOwner().getId()).isEqualTo(2);
        

    }

}