package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

//AppConfig는
// 1. 어플리케이션 실제 동작에 필요한 구현객체를 생성함.
// 2. 생성한 객체 인스턴스의 참조를 생성자를 통해 주입(연결)함.
public class AppConfig {

    // MemberService를 사용하고자 할때 클라이언트에서 직접 구현체를 의존하지 않도록 하기 위해
    // 여기서 아래와 같이 구현체를 주입해줌.
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    // OrderService 클래스에서 생성자를 통해 구현체를 주입 받아야할 2개 필드에 아래와 같이 구현체 주입
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
