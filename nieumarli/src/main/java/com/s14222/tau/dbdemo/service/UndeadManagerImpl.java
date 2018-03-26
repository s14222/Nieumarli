package com.s14222.tau.dbdemo.service;

import com.s14222.tau.domain.Undead;
import com.s14222.tau.repository.UndeadRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UndeadManagerImpl {


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
    /*public UndeadManagerImpl() throws SQLException {
        public static UndeadRepository getInstance(){

        String url = "jdbc:mysql://localhost/aga";
        try {
            Class.forName("com.mysql.jdbc.Driver");  //STEP 2: Register JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.connection = DriverManager.getConnection(url,"root", "");

    }
    }*/

    public void createTables() throws SQLException {

        connection.createStatement().executeUpdate(
                "CREATE TABLE `aga`.`Undeads` ( " +
                        "`id` INT NOT NULL AUTO_INCREMENT , " +
                        "`nazwa` VARCHAR(250) NULL , " +
                        "`zycie` INT NULL , " +
                        "`sila` INT NULL , " +
                        "`level` INT NULL , " +
                        "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
    }

    public boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);//pobranie wszystkich tbel z bazy danych
            boolean tableExists = false;
            while (rs.next()) {
                if ("undeads".equalsIgnoreCase(rs.getString("TABLE_NAME"))) { //if tabela nazywa sie undead
                    tableExists = true;
                    break;
                }
            }
            return tableExists;

        } catch (SQLException e) {
            return false;
        }
    }

    public int addUndead(Undead undead) {
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

    public void deleteUndead(Undead undead){

        if(undead==null) {

            throw new NullPointerException("");
        }

        try {
            deleteUndeadsStmt.setInt(1, undead.getId());
            deleteUndeadsStmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Undead> getAllUndeads() {
        List<Undead> undeads = new LinkedList<>();
        try {
            ResultSet rs = getAllUndeadsStmt.executeQuery();

            while (rs.next()) {
                Undead u = new Undead();
                u.setId(rs.getInt("id"));
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

public void updateById(Undead undead){


    try {
        updateUndeadsStmt.setString(1, undead.getNazwa());
        updateUndeadsStmt.setInt(2,undead.getZycie());
        updateUndeadsStmt.setInt(3,undead.getSila());
        updateUndeadsStmt.setInt(4,undead.getLevel());
        updateUndeadsStmt.setInt(5,undead.getId());
        updateUndeadsStmt.executeUpdate();

    } catch (SQLException e) {

        e.printStackTrace();

    }

    }

    public Undead getById(int id){

        Undead undead = new Undead();

        try{
            getByIdStm.setLong(1, id);
            ResultSet rs = getByIdStm.executeQuery();

            while(rs.next()){
                undead.setId(rs.getInt("id"));
                undead.setNazwa(rs.getString("nazwa"));
                undead.setZycie(rs.getInt("Zycie"));
                undead.setSila(rs.getInt("Sila"));
                undead.setLevel(rs.getInt("Level"));
            }
        }

        catch (SQLException e){

            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return undead;
    }


    public void dropTable() {

        try{
            dropTableStm.executeUpdate();
        }

        catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }


    public Connection getConnection() {

        return connection;
    }


    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;

        addUndeadStmt = connection.
                prepareStatement
                        ("INSERT INTO `undeads` (`id`, `nazwa`, `zycie`, `sila`, `level`) VALUES (NULL, ?, ?, ?, ?);");
        getAllUndeadsStmt = connection.
                prepareStatement("SELECT id, nazwa, zycie, sila, level FROM undeads");
        deleteUndeadsStmt = connection.
                prepareStatement("DELETE FROM `undeads` WHERE `undeads`.`id` = ?");
        updateUndeadsStmt = connection.
                prepareStatement("UPDATE `undeads` SET `nazwa` = ?, `zycie` = ?, `sila` = ?, `level` = ? WHERE `undeads`.`id` = ?;");
        getByIdStm = connection.
                prepareStatement( "SELECT * FROM undeads WHERE id = ?");
        dropTableStm = connection.
                prepareStatement("DROP TABLE undeads");
    }
}
