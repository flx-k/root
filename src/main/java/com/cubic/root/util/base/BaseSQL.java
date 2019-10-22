package com.cubic.root.util.base;

import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BaseSQL {
    public String buildSql(final String sql) {
        System.out.println(sql);
        return sql;
    }

    public String getOne(final Object obj) {
        SQL sql = new SQL();
        Class cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        List<String> arr = new ArrayList();
        sql.SELECT("*");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (null != field.get(obj)) {
                    arr.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        sql.FROM(StringUtil.upper2Lower(cla.getSimpleName()));
        for (String str : arr) {
            sql.WHERE(StringUtil.upper2Lower(str) + " = #{" + str + "}");
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String insert(final Object obj) throws IllegalAccessException {
        Class cla = obj.getClass();
        SQL sql = new SQL();
        sql.INSERT_INTO(StringUtil.upper2Lower(cla.getSimpleName()));
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (null != field.get(obj)) {
                sql.VALUES(StringUtil.upper2Lower(field.getName()), "#{" + field.getName() + "}");
            }
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String update(final SimpleParam param) {
        SQL sql = new SQL();
        sql.UPDATE(StringUtil.upper2Lower(param.getClassName()));
        if (null != param.getSet())

            for (String key : param.getSet().keySet()) {
                sql.SET(key + "='" + param.getSet().get(key) + "'");
            }
        if (null != param.getWhere())

            for (String key : param.getWhere().keySet()) {
                sql.WHERE(key + "='" + param.getWhere().get(key) + "'");
            }
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String select(final SimpleParam param) {
        SQL sql = new SQL();
        for (String str : param.getFields()) {
            sql.SELECT(StringUtil.upper2Lower(str));
        }
        sql.FROM(StringUtil.upper2Lower(param.getClassName()));
        if (null != param.getWhere())
            for (String key : param.getWhere().keySet()) {
                String[] p = (String[]) param.getWhere().get(key);
                if (p[0].equals("eq")) {
                    sql.WHERE(p[1] + "='" + p[2] + "'");
                }
            }
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String count(final SimpleParam param) {
        SQL sql = new SQL();

        sql.SELECT("count(*)");

        sql.FROM(StringUtil.upper2Lower(param.getClassName()));
        if (null != param.getWhere())
            for (String key : param.getWhere().keySet()) {
                String[] p = (String[]) param.getWhere().get(key);
                if (p[0].equals("eq")) {
                    sql.WHERE(p[1] + "='" + p[2] + "'");
                }
            }
        System.out.println(sql.toString());
        return sql.toString();
    }


}
