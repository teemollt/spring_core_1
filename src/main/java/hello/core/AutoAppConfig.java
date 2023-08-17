package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // @Component붙은 클래스를 찾아서 다 스프링빈으로 자동 등록해줌
        //excludeFilters로 설정된건 빼고 스프링빈 등록
        //@Configuration이 붙은 설정정보도 자동으로 등록되므로 빼줘야 충돌 X(예제코드 남겨놓기위함)
        //( @Configuration도 소스코드 까보면 @Component 에노테이션 붙어있어서 자동등록됨 )
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}

    /*
    1. @ComponentScan
        - @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록
        - 이때 스프링 빈의 기본이름은 클래스명에서 첫글자 소문자로 바꾼 형태
            - 빈 이름 기본: MemberServiceImpl 클래스 -> memberServiceImpl
            - 빈 이름 직접 지정 : 필요에 따라 @Component("memberService2")와 같이 직접 이름 지정가능

     2. @Autowired 의존관계 자동 주입
        - 생성자에 @Autowired를 지정하면 스프링 컨테이너가 자동으로 해당 스프링빈을 찾아서 주입
        - 이때 기본 조회 전략은 "타입이 같은 빈"을 찾아서 주입
            - getBean(MemberRepository.class)와 동일하다고 이해
            - 같은 타입이 여러개면?? -> 일단은 충돌이나겠지? 추가설명 뒤에..
        - 생성자에 파라미터가 많아도 타입으로 다 찾아서 자동으로 주입해줌!
      */
