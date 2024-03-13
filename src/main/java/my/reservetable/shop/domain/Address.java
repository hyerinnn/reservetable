package my.reservetable.shop.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String zipcode;
    private String address1;
    private String address2;

    public Address(String zipcode, String address1, String address2) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public Address(String zipcode, String address1) {
        this.zipcode = zipcode;
        this.address1 = address1;
    }
}
