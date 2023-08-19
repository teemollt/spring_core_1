package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {


    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        System.out.println("beanA = " + beanA);

        Assertions.assertThat(beanA).isNotNull();

        // BeanB beanB = ac.getBean("beanB", BeanB.class);
        // 바로 윗줄 실행시 다음 에러 메시지 No bean named 'beanB' available
        // BeanB의 경우 @MyExcludeComponent 어노테이션을 적용받는데,
        // ComponentScan에서 excludeFilters를 통해 해당 어노테이션을 스캔대상에서 제외했으므로
        // 해당 빈은 없다는 에러가 뜨는것.
        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );

    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ANNOTATION,
                    classes = MyIncludeComponent.class
            ),
            excludeFilters = @ComponentScan.Filter(
                    type = FilterType.ANNOTATION,
                    classes = MyExcludeComponent.class
            )
    )
    static class ComponentFilterAppConfig {

    }
    /*
    FilterType 옵션 5가지
    - ANNOTATION : 기본값, 어노테이션을 인식해서 동작
    - ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
    - ASPECTJ : AspectJ 패턴 사용 ex) org.example..*Service+
    - REGEX : 정규표현식 ex) org\.example\.Default.*
    - CUSTOM : TypeFilter 라는 인터페이스를 구현해서 처리

    #참고 : 사실 @Component 면 충분해서 includeFilters를 사용할일이 거의 없음,
        excludeFilters는 종종 사용할 수도..
        최근 스프링부트는 컴포넌트 스캔을 기본으로 제공함. 이를 그대로 사용하는 것이 좋을듯..
     */
}
