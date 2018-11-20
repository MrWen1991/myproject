package top.winkin.aop;

/**
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
 */
public class HelloWord {

    public void sayHello() {
        System.out.println("hello world !");
    }

    public static void main(String args[]) {
        HelloWord helloWord = new HelloWord();
        helloWord.sayHello();
    }
}
