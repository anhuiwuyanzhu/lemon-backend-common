package test;


import com.lemon.violet.annotation.LogRef;
import lombok.Data;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class MyTest {
    public static void main(String[] args) throws IllegalAccessException {
        TArr ta = new TArr();
        Class<? extends TArr> clz = ta.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {

            System.out.println(field.getName());
//            Class<?> type = field.getType();

//            field.setAccessible(true);
//            Object fieldVal = field.get(ta);
//            System.out.println(type.isArray());
//            int len = Array.getLength(fieldVal);
//            for (int i = 0; i < len; i++) {
//                Object o = Array.get(fieldVal, i);
//                System.out.println(o);
//            }

//            System.out.println(type);
        }


    }


}
@Data
class TArr{
    private String[] strs={"a","b","c"};

    private int[] its={1,2,3,4};
}