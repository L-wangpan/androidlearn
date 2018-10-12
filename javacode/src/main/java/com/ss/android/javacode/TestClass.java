package com.ss.android.javacode;

import java.util.List;

public class TestClass{

    public void test(List<Base> bases) {
        A a = (A)bases.get(0);
        System.out.println(a.a);
    }

    public void test(Base base){

    }
}
