package next.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Junit4Runner {
    @Test
    public void run() throws Exception {
        Class clazz = Junit4Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        for(int i = 0; i < methods.length; i++) {
            if(methods[i].isAnnotationPresent(MyTest.class))
                methods[i].invoke(clazz.newInstance());
        }
    }
}


