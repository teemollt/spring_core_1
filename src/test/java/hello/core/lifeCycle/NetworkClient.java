package hello.core.lifeCycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 메서드를 사용한 초기화, 소멸
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
        // 위 초기화 동작을 생성자에서 해주면 처음 예시처럼 liftCycle상 null 처리됨..
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    // config에서 Bean등록할때 설정을 통해 메서드 사용할 수 있게 만들어주면
    // 아래 메서드로 초기화 가능(BeanLifeCycleTest 참고)
    @PostConstruct // 설정정보에서 설정해주지 않아도 여기서 메서드에 바로 어노테이션으로 사용가능!!
    public void init() {
        // 여기서 아래 초기화 작업을 해줘야함.(InitializingBean 사용하는 경우)
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    // 마찬가지로 소멸 가능
    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
