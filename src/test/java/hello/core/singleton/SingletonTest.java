package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        System.out.println("memberService1 == memberService2 ? " + (memberService1 == memberService2));

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

        /*
        문제점 : 스프링 없는 순수한 DI컨테이너인 AppConfig는
                클라이언트의 호출이 있을때마다 메모리에 객체를 새로 생성함 -> 메모리 낭비 심함!!!
        해결책 : 한개의 객체만 생성하고 해당 객체를 공유하면 됨!! -> 싱글톤 패턴!!

        싱글톤 패턴??
        - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
        - 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 함
            - private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막음
         */
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
//        SingletonService singletonService = new SingletonService(); -> SingletonService() has private access
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        System.out.println("singletonService1 == singletonService2 ? " + (singletonService1 == singletonService2));

        // memberService1 != memberService2
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        // isSameAs  ==> == 연산자 느낌
        // isEqualTo ==> String.equals() 느낌
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        // 직접 만들었던 appConfig 대신 스프링컨테이너 사용
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회 : 스프링빈으로 등록된 객체를 조회
//        MemberService memberService1 = appConfig.memberService();
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회 : 스프링빈으로 등록된 객체를 조회
//        MemberService memberService2 = appConfig.memberService();
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값이 같은 것을 확인 => 스프링컨테이너 사용시 자동으로 싱글톤으로 생성됨.
        // ==> 클라이언트 요청이 있을때마다 객체 생성하지 않고 이미 만들어진 객체를 공유해 효율적으로 재사용!!
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        System.out.println("memberService1 == memberService2 ? " + (memberService1 == memberService2));

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isSameAs(memberService2);

        /* 참고
        스프링의 기본 빈 등록 방식은 싱글톤, but 요청할때마다 새로운 객체를 생성해 반환하는 기능도 제공함! (아주 예외적인 경우에 사용..)
         */
    }
}
