package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.Order;

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
