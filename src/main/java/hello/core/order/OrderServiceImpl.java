package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드값을 초기화해주는 생성자를 따로 코드없이 만들어주는 lombok 기능
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 1.구현체 직접의존
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 1. fix 구현체 의존
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 1. rate 구현체 의존
    // 2. 위처럼 코딩하면 정책 변경에 따라 할인정책을 호출하는 클라이언트의 코드가 변경됨 -> ocp, dip 위배
    // 2. 아래처럼 인터페이스에만 의존해야함 but 구현체가 없는데??
    // 2. 누군가 클라이언트인 OrderServiceImpl 클래스에 DiscountPolicy 구현 객체를 대신 생성 주입 해줘야함!!
    private final DiscountPolicy discountPolicy;

    // 2. 생성자로 구현체를 주입 받음 (주입은 AppConfig에서)
    //          => OrderServiceImpl(지금 이 클래스) 입장에선 어떤 구현체가 들어올지 전혀 알지 못함.(dip지킴)
    // 3. 이제는 AppConfig가 아니라 Autowired로 자동으로 주입
    // 4. lombok @RequiredArgsConstructor 로 대체 가능
    // 5. @Qualifier 사용 txt 참고 (DiscountPolicy 타입의 스프링빈이 2개인 경우 해결법)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,
//                            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy
                            @MainDiscountPolicy DiscountPolicy discountPolicy //Qualifier 대신 직접만든 어노테이션
    ) {
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문 생성 요청이 오면 회원 정보 우선 조회 후
        Member member = memberRepository.findById(memberId);
        // member의 grade를 할인정책에서 알아야 하므로 member를 itemPrice를 알아야하므로 itemPrice를
        // 할인정책으로 넘겨서 결과값만 받아옴.
        // 단일책임원칙이 잘 지켜진 설계임. 주문서비스에서는 할인 정책을 알지 못하고 결과만 받음
        // 할인정책의 변경이 있을 경우에는 discountPolicy쪽 구현체 변경을 하면됨
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 마지막으로 최종 생성된 주문을 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // @Configuration에서 싱글톤 유지 관련 Test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
