package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl();

    MemberService memberService;

    @BeforeEach // 매test를 실행하기전 실행할 메서드
    public void beforeEach() {
        // 여기서 매 테스트 실행전 AppConfig 객체 생성해서 메스드 실행을 통해 서비스 객체 생성 및 의존관계 주입해줘야함
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        //given 이것들이 주어졌을때
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when 이렇게 했을때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then 이렇게 된다.
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
