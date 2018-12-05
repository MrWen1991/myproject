package top.winkin.aop.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * applicationContext.xml 添加配置
 * <aop:aspectj-autoproxy proxy-target-class="true"/>
 */
@Aspect
@Component
public class MyAspect {

    /**
     * 定义切点可以重复利用
     */
    @Pointcut("execution(* top.winkin.aop.spring.UserDao.addUser(..))")
    public void addUser(){}

    @Pointcut("execution(* top.winkin.aop.spring.UserDao.findUser(..))")
    public void findUser(){}

    /**
     * 前置通知
     */
//    @Before("execution(* top.winkin.aop.spring.UserDao.addUser(..))")
    @Before(value = "addUser()&& args(num))", argNames = "num")
    public void before1(int num) {
        System.out.println("前置通知1参数" + num + "....");
    }

    @Before(value = "addUser()&& args(num))", argNames = "num")
    public void before2(int num) {
        System.out.println("前置通知2参数" + num + "....");
    }


    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
//    @AfterReturning(value="execution(* top.winkin.aop.spring.UserDao.addUser(..))",returning = "returnVal")
    @AfterReturning(value="addUser()",returning = "returnVal")
    public void AfterReturning1(Object returnVal){
        System.out.println("后置通知1...."+returnVal);
    }

    @AfterReturning(value="addUser()",returning = "returnVal")
    public void AfterReturning2(Object returnVal){
        System.out.println("后置通知2...."+returnVal);
    }

    /**
     * 环绕通知
     * @param joinPoint 可用于执行切点的类 异常处理类
     * @return
     * @throws Throwable
     */
//    @Around("execution(* top.winkin.aop.spring.UserDao.addUser(..))")
    @Around("findUser()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知前....");
        Object obj= null;
        try {
            obj = (Object) joinPoint.proceed();
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());;
        }
        System.out.println("环绕通知后....");
        return obj;
    }

    /**
     * 抛出通知 此处无法try catch 异常
     * @param e
     */
//    @AfterThrowing(value="execution(* top.winkin.aop.spring.UserDao.addUser(..))",throwing = "e")
    @AfterThrowing(value="findUser()",throwing = "e")
    public void afterThrowable(Throwable e){
        System.out.println("出现异常:msg="+e.getMessage());
    }

    /**
     * 无论什么情况下都会执行的方法
     */
//    @After(value="execution(* top.winkin.aop.spring.UserDao.addUser(..))")
    @After("addUser()")
    public void after(){
        System.out.println("最终通知....");
    }


}