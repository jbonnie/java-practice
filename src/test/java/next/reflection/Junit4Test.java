package next.reflection;

public class Junit4Test {
    @MyTest
    public void one() throws Exception {
        System.out.println("Running Test1");
    }

    @MyTest
    public void two() throws Exception {
        System.out.println("Running Test2");
    }

    @ElapsedTime
    public void testThree() throws Exception {
        System.out.println("Running Test3");
    }

    @ElapsedTime
    public void longTimeFunc() throws InterruptedException {
        Thread.sleep(500);
        System.out.println("a few seconds later...");
    }
}
