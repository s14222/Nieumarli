package com.s14222.tau.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


@Entity
@NamedQueries({
        @NamedQuery(name = "player.czyWyposazony", query = "Select p from Player p where p.czyWyposazony = false")
})
public class Player {

    private int id;
    private String nazwa;
    //private String rodzaj;
    private Boolean czyWyposazony = false;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId(){
        return id;
    }

    public void setId(int id){

        this.id = id;
    }

    public String getNazwa(){

        return nazwa;
    }

    public void setNazwa(String nazwa){

        this.nazwa = nazwa;

    }


    public Boolean getCzyWyposazony(){

        return czyWyposazony;
    }

    public void setCzyWyposazony(Boolean czyWyposazony){
        this.czyWyposazony = czyWyposazony;

    }
}
