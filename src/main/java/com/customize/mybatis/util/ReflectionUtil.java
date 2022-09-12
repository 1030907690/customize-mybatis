package com.customize.mybatis.util;

import com.customize.mybatis.entity.User;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtil {

    public static final char UNDERLINE = '_';


    public static void setPropToBeanFromResultSet(Object o, ResultSet resultSet) {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (String.class.getSimpleName().equals( declaredField.getType().getSimpleName())){
                try {
                    setPropToBean(o,declaredField.getName(),resultSet.getString(camelToUnderline(declaredField.getName(),1)));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (Integer.class.getSimpleName().equals( declaredField.getType().getSimpleName())){
                try {
                    setPropToBean(o,declaredField.getName(),resultSet.getInt(camelToUnderline(declaredField.getName(),1)));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //驼峰转下划线
    public static String camelToUnderline(String param, Integer charType) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
            }
            if (charType == 2) {
                sb.append(Character.toUpperCase(c));  //统一都转大写
            } else {
                sb.append(Character.toLowerCase(c));  //统一都转小写
            }


        }
        return sb.toString();
    }
    public static void main(String[] args) {
        User user = new User();

        setPropToBeanFromResultSet(user,null);
    }

    private static void setPropToBean(Object bean, String name, Object value) {
        try {
            Field f = bean.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(bean,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
