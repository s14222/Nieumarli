package com.s14222.tau.repository;
import com.s14222.tau.domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UndeadRepository{

    //public void initDatabase();
    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;

    public Undead getById(int id);
    public List<Undead> getAll();
    public int add(Undead undead);


    public int deleteById(int id);


    public int updateById(Undead undead);


    void dropTable();

}