package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);

        System.out.println("memberRepository = " + memberRepository);
        // 위 3개 객체 모두 동일함!!!! 각각 new로 객체를 생성하는데 왜 동일한 객체일까?? AppConfig에서 직접 실험 ㄱㄱ

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    //@Configuration 관련..
    @Test
    void configrationDeep() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        // 이를 출력했을때 순수한 클래스라면 'class hello.core.AppConfig'와 같이 출력되어야함.
        // 하지만 출력해보면 bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$7f38b4f0로 출력됨.
        // 이는 CGLIB라는 바이트코드 조작 라이브러리를 사용해 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들어
        // 그 클래스를 스프링 빈으로 등록한것!!!!
        // -> 스프링 컨테이너에는 이름은 appConfig지만 실제 인스턴스는 CGLIB로 바이트코드 조작된 다른 클래스가 들어있는것
        /*
        CGLIB로 조작된 AppConfig 코드는 다음과 같은 로직이 포함되어 있을것임..
        @Bean
        public MemberRepository memberRepository() {

            if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
                return 스프링 컨테이너에서 찾아서 반환;
            } else { // 스프링 컨테이너에 없으면
                기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
                return 반환
                }
            }
           덕분에 Singleton 보장!!

           만약 AppConfig에서 @Configuration 어노테이션 빼고 테스트해보면 순수 appConfig 클래스가 출력되고
           맨 위 테스트에서 같은 memberRepository가 3번 호출되고 각각 다른 객체인걸 확인할 수 있음.
           memberService -> memberRepository = hello.core.member.MemoryMemberRepository@3a5ecce3
           orderService -> memberRepository = hello.core.member.MemoryMemberRepository@561868a0
           memberRepository = hello.core.member.MemoryMemberRepository@2ea6e30c
           -> 싱글톤 X
         */
    }
}
