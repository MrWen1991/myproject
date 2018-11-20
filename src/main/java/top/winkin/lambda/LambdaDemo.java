
package top.winkin.lambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/19 2:53 PM
 */
public class LambdaDemo {

    /**
     * 函数式接口：只包含一个抽象方法声明的接口 可用@FunctionalInterface 指明解决编译层面的问题
     *
     * lambda与匿名函数区别：匿名函数this解读为匿名类，top.winkin.lambda this 解读为外部类
     *
     * lambda的结构：
     *     1、一个 Lambda 表达式可以有零个或多个参数
     *     2、参数的类型既可以明确声明，也可以根据上下文来推断。例如：(int a)与(a)效果相同
     *     3、所有参数需包含在圆括号内，参数之间用逗号相隔。例如：(a, b) 或 (int a, int b) 或 (String a, int b, float c)
     *     4、空圆括号代表参数集为空。例如：() -> 42
     *     5、当只有一个参数，且其类型可推导时，圆括号（）可省略。例如：a -> return a*a
     *     6、Lambda 表达式的主体可包含零条或多条语句
     *     7、如果 Lambda 表达式的主体只有一条语句，花括号{}可省略。匿名函数的返回类型与该主体表达式一致
     *     8、如果 Lambda 表达式的主体包含一条以上语句，则表达式必须包含在花括号{}中（形成代码块）。匿名函数的返回类型与代码块的返回类型一致，若没有返回则为空
     *
     */

    private static final Logger log = LoggerFactory.getLogger(LambdaDemo.class);
    public static void main(String[] args){
//        单语句
        single();
//        块语句
        block();
//        泛型
        GenericType();
//        参数
        param();


    }

    public static String reverseString(MyString reverse,String param){
        return reverse.myStringFunc(param);
    }
    public static void param(){
        //lambda作为参数
        MyString reverse=s -> {
            String result = "";
            for (int i = s.length() - 1;i>=0;i--){
                result+= s.charAt(i);
            }
            return result;
        };

        log.info("result={}", reverseString(reverse,"goodMorning"));
    }


    interface MyGeneric<T>{
        T myGenericFunc(T t);
    }
    private static void GenericType(){
        //lambda泛型化
        MyGeneric<String> reverse = s->{
            String result = "";
            for (int i = s.length() - 1;i>=0;i--){
                result+= s.charAt(i);
            }
            return result;
        };
        log.info("result1={}",reverse.myGenericFunc("goodMorning"));

        MyGeneric<Integer> compute= n->{
            Integer result =1;
            for (int i = 0;i<n;i++ ){
                result += i*result;
            }
            return result;
        };

        log.info("result2={}",compute.myGenericFunc(5));

    }


    interface MyString{
        String myStringFunc(String str);
    }

    private static void block() {
        //多行需要return
        MyString reverse = s -> {
            String result = "";
            for (int i = s.length() - 1;i>=0;i--){
                result+= s.charAt(i);
            }
            return result;
        };

        String result = reverse.myStringFunc("goodmorning");
        System.out.println(result);
    }

    interface NumbericTest{
        boolean numbericTest(int i);
    }

    private static void single() {
        // 单行不需要return
        NumbericTest isEven = n -> n % 2 == 0;
        NumbericTest isNegative = n -> n < 0;
        System.out.println(isEven.numbericTest(3));
        System.out.println(isEven.numbericTest(4));
        System.out.println(isNegative.numbericTest(-1));
        System.out.println(isNegative.numbericTest(0));
    }



}
