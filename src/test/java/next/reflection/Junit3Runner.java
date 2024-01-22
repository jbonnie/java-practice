package next.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Junit3Runner {
    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;
        Method[] methods = clazz.getMethods();
        for(int i = 0; i < methods.length; i++) {
            if(methods[i].getName().indexOf("test") == 0) {
                methods[i].invoke(clazz.newInstance());
            }
        }
    }
}
