package com.cubic.root.service;


import com.cubic.root.dao.AccountDAO;
import com.cubic.root.dao.BaseTableDAO;
import com.cubic.root.dao.plug.PlugDAO;
import com.cubic.root.model.Account;
import com.cubic.root.model.BaseTable;
import com.cubic.root.util.base.SimpleParam;
import com.cubic.root.util.exception.ExistException;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseTableService {
    class Params{
        String id;
        String name;
        String type;
        boolean isnull;
        boolean iskey;
    }
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private BaseTableDAO baseTableDAO;
    @Transactional
    public Map<String,Object> list(SimpleParam simpleParam){
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("count",baseTableDAO.count(simpleParam));
        objectMap.put("datas",baseTableDAO.select(simpleParam));
        return objectMap;
    }

    @Transactional
    public void createTable(BaseTable baseTable) throws ExistException {
        Gson g=new Gson();
        Map<String,String> pmap=g.fromJson(baseTable.getParams(), Map.class);
        List<String> names=new ArrayList<>();
        List<Params> paramsList=new ArrayList<>();
        for(Object k:pmap.keySet()){
            Params p=g.fromJson(g.toJson(pmap.get(k)), Params.class);
            if(names.contains(p.name)){
                throw new ExistException("存在相同的name:"+p.name);
            }
            names.add(p.name);
            paramsList.add(p);
        }
        String sql=bulidTable(baseTable.getId(),paramsList);
        baseTable.setCreateSql(sql);
        baseTableDAO.insert(baseTable);
        try {
            baseTableDAO.sql_insert(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String bulidTable(String tableName,List<Params> paramsList){
        List<String> keys=new ArrayList<>();
        StringBuffer sql=new StringBuffer();
        sql.append(" CREATE TABLE `").append(tableName).append("`");
        sql.append("(");
        int i=0;
        for(Params p:paramsList){
            i++;
            if(i>1){
                sql.append(",");
            }
            sql.append(" `").append(p.id).append("` ");
            sql.append(" ").append(p.type).append(" ");
            sql.append(" ").append(p.isnull?"DEFAULT NULL":"NOT NULL").append(" ");
            if(p.iskey)
                keys.add(p.id);
        }
        if(keys.size()>0){
            sql.append(",").append("PRIMARY KEY").append("(");
            i=0;
            for(String k:keys){
                i++;
                if(i>1){
                    sql.append(",");
                }
                sql.append("`").append(k).append("`");
            }
            sql.append(")");
        }
        sql.append(");");
        return sql.toString();
    }
}
