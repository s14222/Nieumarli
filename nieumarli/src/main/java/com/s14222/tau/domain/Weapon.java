package com.s14222.tau.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


@Entity
@NamedQueries({
        @NamedQuery(name = "weapon.czyWyposazony", query = "Select w from Weapon w where w.czyWyposazony = false")
})

public class Weapon {

    private Long id;
    private String nazwa;
    private String rodzaj;
    private Boolean czyWyposazony = false;

    private List<Weapon> weapon;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){
        return id;
    }

    public void setId(Long id){

        this.id = id;
    }

    public String getNazwa(){

        return nazwa;
    }

    public void setNazwa(String nazwa){

        this.nazwa = nazwa;

    }

    public String getRodzaj(){

        return rodzaj;
    }

    public void setRodzaj(String rodzaj){

        this.rodzaj = rodzaj;

    }
    public Boolean getCzyWyposazony(){

        return czyWyposazony;
    }

    public void setCzyWyposazony(Boolean czyWyposazony){
        this.czyWyposazony = czyWyposazony;

    }



}
