package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl(); //1, 기존에는 직접 구현객체를 생성해서 사용
        // 2. DI이용, AppConfig를 통해 생성해야함
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService(); // 2. 현재 상태론 이변수에 memberServiceImpl이 들어있겠지

        // 3. Spring Container 사용!!! @Configuration 어노테이션 달았던 AppConfig 객체를 통해 스프링 컨테이너가 관리하는 메서드를 호출!
        // annotation기반 config 구현 객체, xml기반으로도 가능.
        // 이게 "스프링컨테이너" 스프링빈들을 저장해놓고 사용할 수 있게 해주는..
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);// params는 불러오고자 하는 메서드 AppConfig 이름 그대로

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
