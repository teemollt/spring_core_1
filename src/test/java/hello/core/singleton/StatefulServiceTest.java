package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
//   BadCase
//        //ThreadA : A사용자 10000원 주문
//        statefulService1.order("userA", 10000);
//        //ThreadB : B사용자 20000원 주문
//        statefulService2.order("userB", 20000);
//
//        //ThreadA : 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();
//        System.out.println("price = " + price);
//        // 기대: 10000, 실제: 20000 ThreadA의 order과 getPrice 사이에 ThreadB의 order이 끼어들었기때문.
//        // 싱글톤인 StatefulService의 price 필드는 공유필드인데, 특정 클라이언트가 값을 변경함. => 절대 안됨!!
//        // 따라서 무상태로 설계해야한다! 필드 대신 공유되지 않는 지역변수, 파라미터, ThreadLocal 사용!!
//
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        int userAPrice = statefulService1.order("userA", 10000);
        int userBPrice = statefulService1.order("userB", 20000);
        System.out.println("userAPrice = " + userAPrice + "  userB = " + userBPrice);
        Assertions.assertThat(userAPrice).isEqualTo(10000);

        /*
        공유필드 주의!!!
        스프링 빈은 절대 항상 무상태로 설계해야함!!!!
         */
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}