package com.s14222.tau.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "undead.all", query = "Select u from Undead u"),
        @NamedQuery(name = "undead.byNazwa", query = "Select u from Undead u where u.nazwa = :nazwa")
})

public class Undead{

    public Long id;
    public String nazwa;
    public int level;
    public int zycie;
    public int sila;

    private List<Weapon> weapons;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    
    }

    /*public String getNazwa(){
        return nazwa;
    }

    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
    
    }*/

    public int getZycie(){
        return zycie;
    }

    public void setZycie(int zycie){
        this.zycie = zycie;
    
    }
    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    
    }
    public int getSila(){
        return sila;
    }

    public void setSila(int sila){
        this.sila = sila;
    
    }

    @Column(unique = true, nullable = false)
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    public List<Weapon> getWeapons() {
        return weapons;
    }
    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
    public Undead(){}

    public Undead(Long id, String nazwa, int level, int zycie, int sila){

        this.id = id;
        this.nazwa = nazwa;
        this.level = level;
        this.zycie = zycie;
        this.sila = sila;
    }
}
