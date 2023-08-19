package hello.core.scan.filter;

import java.lang.annotation.*;

// 아래 3개 어노테이션은 실제로 @Component에 포함된 어노테이션
@Target(ElementType.TYPE) // 어디에 붙냐? TYPE -> 클래스 레벨에 붙음
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}