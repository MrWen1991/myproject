package reflect;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/25 2:19 PM
 */
public class ReflectUtil {

    public static <T> T clone(Object original,Class<T> claz){
        T newInstance = null;
        try {
            newInstance = claz.newInstance();
            Method[] methods = claz.getDeclaredMethods();
            for (Method destGetMethod: methods) {
               int modifier = destGetMethod.getModifiers();
               if(Modifier.isAbstract(modifier)||Modifier.isInterface(modifier)){
                   continue;
               }
               if(!destGetMethod.getName().contains("get")){
                   continue;
               }
               if(destGetMethod.getName().equals("getClass")){
                   continue;
               }

               // 获取original的get方法 反射获取值 通过反射装入newInstance中
                Method originGetMethod = getMethod(original.getClass(), destGetMethod.getName());
                Object originalValue = originGetMethod.invoke(original);
                Class<?> resultType = destGetMethod.getReturnType();
                Method destSetMethod = claz.getDeclaredMethod("set" + destGetMethod.getName().substring(destGetMethod.getName().indexOf("get")+3), resultType);
                destSetMethod.invoke(newInstance, originalValue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return newInstance;
    }

    private static Method getMethod(Class claz, String methodName) {
        if(null ==claz){
            throw new IllegalArgumentException(claz.getName()+"."+methodName+"not exist");
        }
        Method originalMethod ;
        try {
            originalMethod = claz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            originalMethod = getMethod(claz.getSuperclass(),methodName);
        }
        return originalMethod;
    }
}
