package com.ss.android.javacode;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
    static TestClass testA;
    static TestClass testB;

    static {
    }

    public static void main(String args[]) {
        testA = new TestClass();
        testB = new TestClass();


//        A a = new A();
//        B b = new B();

        List<A> as = new ArrayList<A>();
        as.add(new A());
//        testA.test(as);

        testA.test(new A());

        test1();
//        test2(as);
//        testA.test(a);
//        testB.test(b);

//        TestClass1.pick(a, b);
    }

    private static void test1(){
        Base[] bases = new A[10];
//        bases[1] = new B();
//        System.out.println(bases[1].getClass());
    }

    private static void test2(List<Object> objects){

    }

    private static void test3(){

    }

}
