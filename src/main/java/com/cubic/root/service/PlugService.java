package com.cubic.root.service;

import com.cubic.root.dao.plug.PlugDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class PlugService {
    @Autowired
    public PlugDAO plugDAO;
    public abstract Object create(Object t);
    public abstract Object delete(Object o);
    public abstract Boolean update(Object t);
    public abstract List list(Object t);
    public abstract Object get(Object t);
}
