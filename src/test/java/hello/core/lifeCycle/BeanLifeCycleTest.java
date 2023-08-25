package hello.core.lifeCycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        // 기본 ApplicationContext에서는 컨텍스트 close 기능 없어서 하위 객체 사용 Configurable~ 이게 Anno~보단 상위
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig {

        // 빈 등록시 initMethod, destroyMethod 설정해주면 설정한 이름으로 초기화, 소멸 동작하는 메서드 사용가능
//      @Bean(initMethod = "init", destroyMethod = "close")
        @Bean //  빈에서 직접 메서드에 어노테이션 달아주는 방법으로 사용하면되기때문에 위처럼 따로 설정 안해줘도됨.
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
/*
NetworkClient의 생성자 부분을 보면 url 정보 없이 connect가 호출됨.
-> 객체를 생성하는 단계에는 url이 없고, 객체를 생성한 다음 외부에서 setUrl()이 호출되어야 url이 존재하게됨.
==>>
스프링빈은 다음과 같은 라이프 사이클을 가짐(생성자 주입 말고..)
객체생성 -> 의존관계 주입

즉, 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료.
따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 함!!!
그런데 개발자가 이 시점을 어떻게 알 수 있을까????????
"스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공!!!"
또한 "스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 줌!!!!"
따라서 안전하게 종료 작업을 진행할 수 있음

"스프링 빈의 이벤트 라이프사이클"
스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜맥 -> 스프링 종료

- 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
- 소멸전 콜백 : 빈이 소멸되기 직전에 호출

참고 : 객체의 생성과 초기화를 분리하자.
- 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가짐.
반면 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행.
따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화 하는 부분을
명확하게 나누는 것이 유지보수 관점에서 좋음.
물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단순한 경우엔 생성자에서 한번에 다 처리하는게 나을수도..

스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원!
- 인터페이스(InitializingBean, DisposableBean)
- 설정 정보에 초기화 메서드, 종료 메서드 지정
- @PostConstruct, @PreDestroy 어노테이션 지원


1. 인터페이스(InitializingBean, DisposableBean) 사용 -> NetworkClient 참고
    - 단점
        a. 이 인터페이스는 스프링 전용 인터페이스임. 해당 코드가 스프링 전용 인터페이스에 의존
        b. 초기화, 소멸 메서드의 이름을 변경할 수 없음.
        c. 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없음.
    => 스프링 초창기에 나온 방법! 요즘은 거의 사용하지 않음!!!!!!!!!!!!!
 */
