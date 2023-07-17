package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());

        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        //실제 설정코드에서 반환하는 구체 타입을 반환하므로 인터페이스 타입이 아니라 구체 타입으로도 같은 결과
        // but 역할이 아닌 구현에 의존하면 안좋긴하지만 필요할 수도...
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회X") // 실패 케이스.
    void findBeanByNameX() {
        // ac.getBean("xxxxx", MemberServiceImpl.class);
        // MemberService xxxxx = ac.getBean("xxxxx", MemberServiceImpl.class);
        // 위 실행시 이 에러 발생 => org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxxxx' available

        //import static org.junit.jupiter.api.Assertions.* 위에 이걸로 static import
        // assertThrows () -> 뒤에 있는 구문 실행시 NoSuchBeanDefinitionException 해당 에러가 떠야 테스트 통과!
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberServiceImpl.class));
    }

}
