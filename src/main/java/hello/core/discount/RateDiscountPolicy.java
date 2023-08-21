package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Qualifier("mainDiscountPolicy") // Qualifier 매칭
//@Primary 사용시 DiscountPolicy 타입의 여러 빈이 존재할 시 해당 빈을 autowired할때 최우선적으로 매칭
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;
    private Member member = new Member(1L, "memberA", Grade.VIP);
//    private Order order = new Order(member.getId())
//    Long memberId, String itemName, int itemPrice, int discountPrice

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
