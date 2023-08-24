package hello.core.lifeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// lifeCycle 테스트를 위한..
public class NetworkClient implements InitializingBean, DisposableBean {

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

    @Override // afterPropertiesSet = 의존관계 주입이 끝나면 바로 호출되는 메서드
    public void afterPropertiesSet() throws Exception {
        // 여기서 아래 초기화 작업을 해줘야함.(InitializingBean 사용하는 경우)
        System.out.println("afterPropertiesSet = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    @Override   // 빈이 종료될때 호출되는 메서드
    public void destroy() throws Exception {
        System.out.println("destroy = " + url);
        disconnect();
    }
}
