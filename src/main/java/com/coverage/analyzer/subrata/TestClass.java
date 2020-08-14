package com.coverage.analyzer.subrata;

import com.coverage.analyzer.library.MyRunnable;
import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class TestClass {
    public static void main(String[] args) throws NotFoundException {
        printSyncOrNot(MyRunnable.class, "keepRunning");
        printSyncOrNot(MyRunnable.class, "run");
    }

    private static void printSyncOrNot(final Class classzz, final String methodName) throws NotFoundException {
        //Reflection
        Method method = Arrays.asList(classzz.getDeclaredMethods()).stream().filter(m -> m.getName().equals(methodName)).findFirst().get();
        System.out.println("[Reflection]  " +  methodName + " is synchronized : " + Modifier.isSynchronized(method.getModifiers()));

        //Javassist
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(classzz.getCanonicalName());

        CtMethod cm = cc.getDeclaredMethod(methodName);
        System.out.println("[Javassist]  " +  methodName + " is synchronized : " + Modifier.isSynchronized(cm.getModifiers()));
    }
}
