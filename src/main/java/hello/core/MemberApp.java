package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl(); //1, 기존에는 직접 구현객체를 생성해서 사용
        // 2. DI이용, AppConfig를 통해 생성해야함
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService(); // 2. 현재 상태론 이변수에 memberServiceImpl이 들어있겠지

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
