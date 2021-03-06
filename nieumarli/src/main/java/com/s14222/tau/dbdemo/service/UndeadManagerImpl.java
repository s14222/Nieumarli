package com.s14222.tau.dbdemo.service;

import com.s14222.tau.domain.Undead;
import com.s14222.tau.repository.UndeadRepository;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class UndeadManagerImpl implements UndeadRepository {

    private Connection connection;
    private PreparedStatement addUndeadStmt;
    private PreparedStatement deleteUndeadsStmt;
    private PreparedStatement updateUndeadsStmt;
    private PreparedStatement getAllUndeadsStmt;
    private PreparedStatement dropTableStm;
    private PreparedStatement getByIdStm;

    public UndeadManagerImpl(Connection connection) {

        try {
            this.connection = connection;

            if (!isDatabaseReady()) {
                createTables();
            }
            setConnection(connection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public UndeadManagerImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
        checkDatabaseAndSetConnection(this.connection);
    }

    void checkDatabaseAndSetConnection(Connection connection) throws SQLException{
        if (!isDatabaseReady()) {
            createTables();
        }

        setConnection(connection);
    }

    public void createTables() throws SQLException {

        connection.createStatement().executeUpdate(
                "CREATE TABLE " +
                        "Undeads(id integer GENERATED BY DEFAULT AS IDENTITY, " +
                        "nazwa varchar(30) NOT NULL, " +
                        "zycie integer NOT NULL, " +
                        "sila integer NOT NULL, " +
                        "level integer NOT NULL)"
                );
    }

    public boolean isDatabaseReady() {

        boolean tableExists = false;
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);//pobranie wszystkich tbel z bazy danych
            while (rs.next()) {
                if ("Undeads".equalsIgnoreCase(rs.getString("TABLE_NAME"))) { //if tabela nazywa sie undead
                    tableExists = true;
                    break;
                }
            }
           

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableExists;
    }

    @Override
    public int add(Undead undead) {
        int count = 0;
        try {
            addUndeadStmt.setString(1, undead.getNazwa());
            addUndeadStmt.setInt(2, undead.getZycie());
            addUndeadStmt.setInt(3, undead.getSila());
            addUndeadStmt.setInt(4, undead.getLevel());

            count = addUndeadStmt.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public int deleteById(int id){

        int count = 0;

        try {
            deleteUndeadsStmt.setInt(1, id);
            count = deleteUndeadsStmt.executeUpdate();

        }         catch(SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());      
        }

        return count;
    }

    @Override
    public List<Undead> getAll() {

        List<Undead> undeads = new LinkedList<>();
        try {
            ResultSet rs = getAllUndeadsStmt.executeQuery();

            while (rs.next()) {
                Undead u = new Undead();
                u.setId(rs.getLong("id"));
                u.setNazwa(rs.getString("nazwa"));
                u.setZycie(rs.getInt("zycie"));
                u.setSila(rs.getInt("sila"));
                u.setLevel(rs.getInt("level"));
                undeads.add(u);
            }

        } catch (SQLException e) {

            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return undeads;
    }

    @Override
    public int updateById(Undead undead){

    int count = 0;

    try {
        updateUndeadsStmt.setString(1, undead.getNazwa());
        updateUndeadsStmt.setInt(2,undead.getZycie());
        updateUndeadsStmt.setInt(3,undead.getSila());
        updateUndeadsStmt.setInt(4,undead.getLevel());
        updateUndeadsStmt.setLong(5,undead.getId());
        count = updateUndeadsStmt.executeUpdate();

    } catch(SQLException e) {
        throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());     
    }
    return count;

 }

    @Override
    public Undead getById(int id){

        Undead undead = new Undead();

        try{
            getByIdStm.setLong(1, id);
            ResultSet rs = getByIdStm.executeQuery();

            while(rs.next()){
                undead.setId(rs.getLong("id"));
                undead.setNazwa(rs.getString("nazwa"));
                undead.setZycie(rs.getInt("zycie"));
                undead.setSila(rs.getInt("sila"));
                undead.setLevel(rs.getInt("level"));
            }
        }

        catch (SQLException e){

            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return undead;
    }

    @Override
    public void dropTable() {

        try{
            dropTableStm.executeUpdate();
        }

        catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }


    @Override
    public Connection getConnection() {

        return connection;
    }


    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;

        addUndeadStmt = connection.
                prepareStatement
                        ("INSERT INTO Undeads (nazwa, zycie, sila, level) VALUES ( ?, ?, ?, ?)");
        getAllUndeadsStmt = connection.
                prepareStatement("SELECT * FROM Undeads");
        deleteUndeadsStmt = connection.
                prepareStatement("DELETE FROM Undeads WHERE id = ?");
        updateUndeadsStmt = connection.
                prepareStatement("UPDATE Undeads SET nazwa = ?, zycie = ?, sila = ?, level = ? WHERE id = ?");
        getByIdStm = connection.
                prepareStatement( "SELECT * FROM undeads WHERE id = ?");
        dropTableStm = connection.
                prepareStatement("DROP TABLE Undeads");
    }




}
