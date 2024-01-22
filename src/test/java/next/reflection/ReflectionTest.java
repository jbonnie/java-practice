package next.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.LocalTime;

import next.Student;
import next.optional.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;
        // 클래스 이름
        logger.debug("Class Name {}", clazz.getName());
        logger.debug("----------------------------------------");
        // 클래스의 메소드
        Method[] methods = clazz.getMethods();
        for(int i = 0 ; i < methods.length; i++)
            logger.debug("Method #"  + i + ": {}", methods[i].getName());
        logger.debug("----------------------------------------");
        // 클래스의 생성자
        Constructor[] constructors = clazz.getConstructors();
        for(int i = 0; i < constructors.length; i++)
            logger.debug("Constructor #" + i + " parameter types: {}", (Object)constructors[i].getParameterTypes());
    }

    @Test
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug("Class name: {}", clazz.getName());
        logger.debug("----------------------------------------");
        try {
            Student student = new Student();
            Field nameField = clazz.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(student, "보경");
            Field ageField = clazz.getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(student, 23);
        }
        catch(NoSuchFieldException e) {
            logger.debug("No such field...");
        } catch (IllegalAccessException e) {
            logger.debug("Illegal acess...");
        }
    }

    @Test
    public void userCreate() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug("Class name: {}", clazz.getName());
        logger.debug("----------------------------------------");
        Constructor[] constructors = clazz.getDeclaredConstructors();

        for(Constructor constructor: constructors) {
            Class<?>[] p = constructor.getParameterTypes();
            if(p.length == 2 && p[0] == String.class && p[1] == Integer.class) {
                User user = (User)constructor.newInstance("보경", 23);
                logger.debug("new user name: {}, age: {}", user.getName(), user.getAge());
                return;
            }
        }
    }

    @Test
    public void timeCalculate() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = Junit4Test.class;
        Method[] methods = clazz.getDeclaredMethods();
        for(int i = 0; i < methods.length; i++) {
            if(methods[i].isAnnotationPresent(ElapsedTime.class)) {
                long start = System.currentTimeMillis();
                methods[i].invoke(clazz.newInstance());
                long end = System.currentTimeMillis();
                logger.debug("Elapsed Time: {} ms", end - start);
            }
        }
    }

    @Test
    public void helloWorldByteCode() {
        String hello = "hello world";
        logger.debug("hello world byte: {}", hello.getBytes());
    }
}
