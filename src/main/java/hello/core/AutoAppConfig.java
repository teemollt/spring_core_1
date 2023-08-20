package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // @Component붙은 클래스를 찾아서 다 스프링빈으로 자동 등록해줌
        // 탐색할 패키지의 시작위치(root)를 지정, 이 패키지 포함해서 하위 패키지를 모두 탐색
        // {"hello.core", "hello.service"}와 같이 여러 위치를 지정할 수 있음.
        // basePackageClasses 사용하면 지정한 클래스의 패키지를 탐색 시작위치로 지정
        // 시작위치 미지정시 @ComponentScan이 있는 설정정보 클래스의 패키지가 시작위치!!!
        // 권장 : 패키지 위치 지정하지 않고 설정 정보 클래스의 위치를 프로젝트 최상단에 두는것. 스프링부트 기본방식
        // -> 스프링부트 사용시 스프링부트 대표 시작 정보 @SpringBootApplication 을 이 프로젝트 시작 루트 위치에 두는 것이 관례
        // 그리고 이 안에 @ComponentScan이 들어있다.
//        basePackages = "hello.core.member",
        //excludeFilters로 설정된건 빼고 스프링빈 등록
        //@Configuration이 붙은 설정정보도 자동으로 등록되므로 빼줘야 충돌 X(예제코드 남겨놓기위함)
        //( @Configuration도 소스코드 까보면 @Component 에노테이션 붙어있어서 자동등록됨 )
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 이미 자동으로 등록된 스프링빈과 같은 이름으로 스프링빈을 수동으로 등록한다면?
    // 스프링 상으로는 수동빈이 자동빈을 오버라이딩 하면서 에러 없이 실행됨 그래서 테스트는 통과되는데..
    // 하지만 스프링부트는 최근 오버라이딩이 아닌 에러를 띄우는 방식으로 기본값을 변경하였음.
    // 그래서 스프링부트 @SpringBootApplication 메인 메서드 실행해보면 에러 남
    // 이는 스프링부트 설정 allow-bean-definition-overriding=true로 바꾸면 에러 안뜸.. 근데 에러띄우는게 나음.
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
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

     3. 컴포넌트 스캔 기본 대상
        - 컴포넌트 스캔은 @Component뿐 아니라 아래 내용도 스캔 대상에 포함
            - @Component :
            - @Controller : 스프링MVC컨트롤러에서 사용
            - @Service : 스프링 비즈니스 로직에서 사용
            - @Repository : 스프링 데이터 접근 계층에서 사용
            - @Configuration : 스프링 설정 정보에서 사용
            => 위 에노테이션들 소스 까보면 전부 @Component가 포함되어 있음.
            #참고 : 사실 에노테이션에는 상속관계가 없음. 위처럼 에노테이션이 특정 에노테이션을 포함하고 있는 것을
                인식할 수 있는 것은 자바 언어가 아니라 스프링이 지원하는 기능임
        - 컴포넌트 스캔 용도 뿐 아니라 다음 에노테이션은 스프링이 부가 기능을 수행하게 함.
            - @Controller : 스프링 mvc 컨트롤러로 인식
            - @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해줌
            - @Configuration : 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 해줌
            - @Service : 스프링이 특별한 처리를 하지는 않음. 개발자들이 소스를 읽을때 비즈니스 로직의 위치를 파악하는데 도움.
      */
