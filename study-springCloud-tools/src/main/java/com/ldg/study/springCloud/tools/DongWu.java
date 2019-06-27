package com.ldg.study.springCloud.tools;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： ldg
 * @create date： 2019/1/15
 */
public interface DongWu {
    void a();
}

interface YiFu {
    void b();
}

class DongWu_a implements DongWu, YiFu {
    @Override
    public void a() {

    }

    @Override
    public void b() {

    }
}


class DongWu_b implements DongWu {
    @Override
    public void a() {

    }
}

class main {
    public static void main(String[] args) {
        DongWu_a dongWu = new DongWu_a();
        Class<? extends DongWu_a> type = dongWu.getClass();
        System.out.println(type.getSuperclass().getName() + "     " + type.getSuperclass().getSuperclass());
        Class<?>[] interfaces = type.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface.getName());
        }

        List<Integer> aa = new ArrayList<>();
    }
}


class GenericType {

    public static void main(String[] args) {
        ChildCls childCls = new ChildCls();
    }
}

class SuperCls<K, V> {

    public SuperCls() {
        TypeVariable<?>[] typeParameters = getClass().getSuperclass().getTypeParameters();// [K,V]
        for (TypeVariable<?> typeVariable : typeParameters) {
            p("getSuperclass().getTypeParameters()=" + typeVariable);// 这里分别输出K,V
            Type[] bounds = typeVariable.getBounds();
            for (Type type : bounds) {
                p(type);// 分别输出class java.lang.Object
            }
        }
    }

    void p(Object obj) {
        System.out.println(obj.toString());
    }
}

class ChildCls extends SuperCls<SuperCls<? extends String, ? super String>, Integer> {

}
