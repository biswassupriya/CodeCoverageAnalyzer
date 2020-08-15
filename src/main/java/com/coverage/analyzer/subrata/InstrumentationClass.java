package com.coverage.analyzer.subrata;

import com.coverage.analyzer.library.MyRunnable;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class InstrumentationClass {
    public static void main(String[] args) throws NotFoundException {
        //printSyncOrNot(MyRunnable.class, "keepRunning");
        //printSyncOrNot(MyRunnable.class, "run");
    }

    private static void printSyncOrNot(final Class classzz, final String methodName) throws NotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassPool cp = ClassPool.getDefault();

        CtClass cc = cp.get(classzz.getCanonicalName());


        Arrays.asList(cc.getMethods()).forEach(ctMethod ->  {
            if(ctMethod.hasAnnotation(Test.class)) {
                //

            }
        });
        final Object o = classzz.newInstance();
        Arrays.asList(classzz.getMethods()).forEach(method -> {
            try {
                method.invoke(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        CtMethod cm = cc.getDeclaredMethod(methodName);
        System.out.println("[Javassist]  " +  methodName + " is synchronized : " + Modifier.isSynchronized(cm.getModifiers()));
    }
}
