package hello.core.member;

public class MemberServiceImpl implements MemberService{

// bad : MemoryMemberRepository구현체를 직접 의존함 => 저장소 구현체가 바뀔 경우 매번 직접 코드를 수정해줘야함.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // good : memberRepository 선언만 하고 아래 생성자를 통해 구현체를 주입해주는 방식, 이렇게 해야 추상화에만 의존(dip지킴)
    // 이제 이 클래스는 의존관계에 대한 고민은 외부(AppConfig)에 맡기고 "실행에만 집중"하면 됨.
    // 관심사의 분리 : 객체의 생성과 주입 - 실행 역할이 명확히 분리
    // 클라이언트인 이 클래스 입장에서 보면 의존관계를 외부에서 주입해주는 것이므로 의존관계 주입(DI)이라고 함.
    private final MemberRepository memberRepository;

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
}
