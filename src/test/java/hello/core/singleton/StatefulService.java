package hello.core.singleton;

public class StatefulService {

//    private int price; // 상태를 유지하는 필드, 클라이언트가 값을 변경해버림 큰문제!

//    public void order(String name, int price) {
//        System.out.println("name = " + name + "  price = " + price);
//        this.price = price; // 문제가 되는 지점 : 공유되는 필드를 변경하는 것은 절대 금지! 아래와 같이 지역변수 리턴하는 방식으로 변경
//    }

    public int order(String name, int price) {
        System.out.println("name = " + name + "  price = " + price);
//        this.price = price; // 문제가 되는 지점 : 공유되는 필드를 변경하는 것은 절대 금지! 아래와 같이 변경
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
