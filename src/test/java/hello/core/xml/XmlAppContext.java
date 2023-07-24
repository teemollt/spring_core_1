package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

// 최근에 잘 사용 X
public class XmlAppContext {

    @Test
    void xmlAppContext() {
        // resources 밑에 xml파일을 읽어서 동작 xml이든 java코드든 상관없이 결국 BeanDefinition이라는 빈설정 메타정보를 만들고
        // (xml은 <bean> 하나당 메타 정보 생성, 자바 코드는 @Bean)
        // 스프링 컨테이너는 해당 메타정보를 읽어서 스프링 빈을 생성하는 것. => config도 역할과 구현을 개념적으로 나눈것이라고 보면됨.
        // 즉 스프링 컨테이너는 BeanDefinition이라는 인터페이스에만 의존함. (추상화에만 의존)
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

    }
}
