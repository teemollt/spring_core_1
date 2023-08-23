package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy") // Qualifier 매칭
// -> but Qualifier에서 지정한 이름이 문자열이므로 오타를 내도 컴파일러가 못잡아냄 -> 런타임에서 문제
// 따라서 annotation.MainDiscountPolicy에서 만든거처럼 직접 어노테이션을 만들어서
// 잘못 입력시 컴파일 오류가 날 수 있게끔 해주는 것도 좋음. 동작은 동일..
//@Primary 사용시 DiscountPolicy 타입의 여러 빈이 존재할 시 해당 빈을 autowired할때 최우선적으로 매칭
@MainDiscountPolicy // 직접 만든 어노테이션, 이걸 사용하는 OrderServiceImpl에도 적용
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
