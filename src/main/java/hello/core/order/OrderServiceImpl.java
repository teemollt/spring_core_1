package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문 생성 요청이 오면 회원 정보 우선 조회 후
        Member member = memberRepository.findById(memberId);
        // member의 grade를 할인정책에서 알아야 하므로 member를 itemPrice를 알아야하므로 itemPrice를
        // 할인정책으로 넘겨서 결과값만 받아옴.
        // 단일책임원칙이 잘 지켜진 설계임. 주문서비스에서는 할인 정책을 알지 못하고 결과만 받음
        // 할인정책의 변경이 있을 경우에는 discountPolicy쪽 구현체 변경을 하면됨
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 마지막으로 최종 생성된 주문을 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
