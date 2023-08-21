package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    /*
    # 옵션 처리
    주입할 스프링 빈이 없어도 동작해야 할 때가 있음.
    but, @Autowired만 사용하면 옵션 기본값이 true이기 떼문에 주입 대상이 없으면 오류 발생

    다음과 같이 자동 주입 대상을 옵션으로 처리
    - @Autowired(required-false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
    - org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력됨
    - Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력됨.
     */

    @Test
    void AutoWiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        //  - @Autowired(required-false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        // 자동 주입해줄 Member 클래스가 없기때문에 기본값으로는 오류가 발생하지만 아래와 같이 옵션질 해주면
        // 호출이 안되고 오류는 안남
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1); // 아예 호출이 안됨.
        }

        // org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력됨
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2); // noBean2 = null
        }

        // Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력됨.
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3); // noBean3 = Optional.empty
        }
    }


}
