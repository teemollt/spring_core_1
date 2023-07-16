package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//AppConfig는
// 1. 어플리케이션 실제 동작에 필요한 구현객체를 생성함.
// 2. 생성한 객체 인스턴스의 참조를 생성자를 통해 주입(연결)함.

//@Configuration , @Bean 어노테이션 붙이기 전/후 차이
// 어플리케이션 구성,설정 정보를 담당하는 클래스에 사용하는 어노테이션 해당 클래스의 모든 메서드에 @Bean 달아줘
// 그러면 메서드들이 "스프링 컨테이너"에 등록됨!
@Configuration  // 어플리케이션 구성,설정 정보를 담당하는 클래스에 사용하는 어노테이션 해당 클래스의 모든 메서드에 @Bean 달아줘
public class AppConfig {
/*
1. 아래 주석처리한 메서드 2개와 같이 작성하는거 보다 그 아래쪽 처럼 따로 빼는 방식으로 리팩터링 해주는게 좋음
 */
//    // MemberService를 사용하고자 할때 클라이언트에서 직접 구현체를 의존하지 않도록 하기 위해
//    // 여기서 아래와 같이 구현체를 주입해줌.
//    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository());
//    }
//
//    // OrderService 클래스에서 생성자를 통해 구현체를 주입 받아야할 2개 필드에 아래와 같이 구현체 주입
//    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//    }

    @Bean //  "스프링 컨테이너"에 등록!
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 할인 정책을 바꾸려면 이거만 수정하면됨
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
