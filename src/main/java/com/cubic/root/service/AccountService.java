package com.cubic.root.service;


import com.cubic.root.dao.AccountDAO;
import com.cubic.root.dao.plug.PlugDAO;
import com.cubic.root.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public final class AccountService {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PlugDAO plugDAO;

    public Account getByName(String name){
        Account account=new Account();
        account.setName(name);
        return accountDAO.getOne(account);
    }



    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("111111"));
    }
//    public List<Test> list(){
//        return testDAO.sql_select("select * from test" );
//    }
}
