package com.coverage.analyzer.subrata;

import java.lang.reflect.InvocationTargetException;

public class ReflectionMethodInvoke {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ReflectionMethodInvoke.class.getMethod("print").invoke(ReflectionMethodInvoke.class.newInstance());
    }

    public void print() {
        System.out.println("I am called");
    }
}
