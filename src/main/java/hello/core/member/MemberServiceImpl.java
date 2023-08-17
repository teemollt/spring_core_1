package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 이제 Bean등록 따로 안해도 ComponentScan이 알아서 등록해줌.
// -> 기존 @Configuration에서는 명시적으로 의존관계를 주입해줬는데 AutoAppConfig는 의존관계 주입이고 뭐고 코드가 없음??
// -> 의존관계 주입 어떻게????
// -> @Autowired !!!!!
@Component
public class MemberServiceImpl implements MemberService{

// bad : MemoryMemberRepository구현체를 직접 의존함 => 저장소 구현체가 바뀔 경우 매번 직접 코드를 수정해줘야함.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // good : memberRepository 선언만 하고 아래 생성자를 통해 구현체를 주입해주는 방식, 이렇게 해야 추상화에만 의존(dip지킴)
    // 이제 이 클래스는 의존관계에 대한 고민은 외부(AppConfig)에 맡기고 "실행에만 집중"하면 됨.
    // 관심사의 분리 : 객체의 생성과 주입 - 실행 역할이 명확히 분리
    // 클라이언트인 이 클래스 입장에서 보면 의존관계를 외부에서 주입해주는 것이므로 의존관계 주입(DI)이라고 함.
    private final MemberRepository memberRepository;

    // 생성자에 @Autowired 사용하면 MemberRepository타입에 맞는놈을 알아서 찾아와서 주입해줌!
    // 앞에서 사용해본 ac.getBean(MemberRepository.class) 같이 작동한다고 생각하면됨.
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // @Configuration에서 싱글톤 유지 관련 Test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
