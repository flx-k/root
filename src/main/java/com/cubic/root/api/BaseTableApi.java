package com.cubic.root.api;
import com.cubic.root.model.BaseTable;
import com.cubic.root.service.BaseTableService;
import com.cubic.root.util.base.ExceptionHandle;
import com.cubic.root.util.base.SimpleParam;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseTableApi {
    Logger logger= LogManager.getLogger(BaseTableApi.class);
    @Autowired
    BaseTableService baseTableService;
    @RequestMapping("/base-table/list")
    public Map<String, Object> list(@RequestBody SimpleParam simpleParam) {
        simpleParam.setClassName(BaseTable.class.getSimpleName());
        Map<String,Object> map=new HashMap<>();
        try {
            map.put("status", true);
            map.put("datas", baseTableService.list(simpleParam));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
    @RequestMapping("/test-createTable")
    public Map<String, Object> testPlug() {
        logger.info("testPlug");
        Map<String,Object> map=new HashMap<>();
        try {
            String param="{'base_table1_p1':{id:'base_table1_p1',name:'id',type:'varchar(255)',isnull:false,iskey:true}}";
            BaseTable baseTable=new BaseTable();
            baseTable.setId("base_table1");
            baseTable.setCreator("admin");
            baseTable.setName("t1");
            baseTable.setNote("test");
            baseTable.setCreateTime(new Timestamp(new Date().getTime()));
            baseTable.setLastModifyTime(baseTable.getCreateTime());
            baseTable.setType("---");
            baseTable.setParams(param);
            baseTableService.createTable(baseTable);
            map.put("datas", ("admin"));
            return map;
        } catch (Exception e) {
            return ExceptionHandle.buildExceptMsg(e);
        }
    }
}
