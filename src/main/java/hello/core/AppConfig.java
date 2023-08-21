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
// 스프링은 빈을 생성하고, 의존관계를 주입하는 단계가 나누어져있음. 아래와 같이 등록하면 이게 한번이 수행되는거처럼 보임..
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

    /* @Configuration 관련 내용
        @Bean memberService -> memberRepository() -> new MemoryMemberRepository
        @Bean orderService -> memberRepository() -> new MemoryMemberRepository
        서로 다른 서비스에서 MemoryMemberRepository 객체가 두번 생성되는데?
        싱글톤 패턴이 깨진것 아닌지?? 스프링은 이를 어떻게 해결하는지?? Test 만들어서 확인.

        직접 ConfigurationSingletonTest 클래스에서 테스트를 해보면 모두 같은 객체를 가리키고 있음.
        이를 다시 AppConfig에서 print해서 확인해보면
        자바 코드상 분명(순서보장x)
        call AppConfig.memberService
        call AppConfig.memberRepository
        call AppConfig.memberRepository
        call AppConfig.orderService
        call AppConfig.memberRepository
        와 같이 될것 같은데
        실제로 출력은

        call AppConfig.memberService
        call AppConfig.memberRepository
        call AppConfig.orderService
        이렇게만 됨.

        비밀은 @Configuration에 있음!! -> ConfigurationSingletonTest에서 추가로 알아보자..

        정리하자면
        @Bean만 사용해도 스프링 빈으로 등록되지만 싱글톤 보장X
        -> 스프링 설정 정보는 항상 @Configuration을 사용하자! -> 싱글톤 보장O
     */

    // Bean 이름은 절대 중복되면 안됨!!
    @Bean //  "스프링 컨테이너"에 등록!
    public MemberService memberService() {

        // AppConfig에서 new를 각각 써도 왜 모두 같은 객체인지 실험
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {

        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {

        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    // 할인 정책을 바꾸려면 이거만 수정하면됨
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
