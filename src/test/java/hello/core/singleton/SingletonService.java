package hello.core.singleton;

public class SingletonService {

    // 내부에 자기자신을 private, static으로 하나 가지고 있음.
    // 자바 기초 복습:
    // static -> class 레벨에 딱 하나만 올라감.클래스로더가 클래스를 로딩해 메소드 메모리 영역에 적재할때 클래스별로 관리.
    // 정적 필드(or 메소드) <--> 인스턴스 필드(or 메소드) 인스턴스 필드는 당연히 매 객체마다 따로 생성 값도 다를 수 있음..
    private static final SingletonService instance = new SingletonService();

    // 위 객체를 조회하려면 아래 메서드로.
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자로 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 방지!!!!
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
   }

   /* 정리
   1. static 영역에 객체 instance를 미리 하나 생성해서 올려둠
   2. 이 객체 인스턴스가 필요시 오직 getInstance() 메서드를 통해서만 조회 가능 - 메서드 호출시 항상 같은 인스턴스 반환
   3. 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서
        혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 방지!!!!
    */
}
