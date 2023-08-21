package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// lombok 사용해서 따로 getter, setter 코드 없이 getter, setter 사용 가능
@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("asdf");

        String name = helloLombok.getName();
        System.out.println("name = " + name);
        System.out.println("helloLombok = " + helloLombok);
    }
}
