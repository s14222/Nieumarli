package com.s14222.tau.dbdemo.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.s14222.tau.domain.Undead;
import com.s14222.tau.domain.Weapon;

@Component
@Transactional
public class EquippingManagerHibernateImpl implements EquippingManager{

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addEnemy(Undead undead) {
        undead.setId(null);
        sessionFactory.getCurrentSession().persist(undead);
        sessionFactory.getCurrentSession().flush();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Undead> getAllEnemies() {

        return sessionFactory.getCurrentSession().getNamedQuery("undead.all").list();
    }

    @Override
    public void deleteEnemy(Undead undead) {
        undead = (Undead) sessionFactory.getCurrentSession().get(Undead.class, undead.getId());

        for (Weapon weapon : undead.getWeapons()){
            weapon.setCzyWyposazony(false);
            sessionFactory.getCurrentSession().update(weapon);
        }

        sessionFactory.getCurrentSession().delete(undead);

    }

    @Override
    public Undead findEnemyByName(String nazwa) {
        return (Undead) sessionFactory.getCurrentSession().getNamedQuery("undead.byNazwa").setString("nazwa", nazwa).uniqueResult();

    }

    @Override
    public Long addnewWeapon(Weapon weapon) {
        weapon.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(weapon);
    }

    @Override
    public List<Weapon> getAvailableWeapons() {
        return sessionFactory.getCurrentSession().getNamedQuery("weapon.czyWyposazony").list();
    }

    //@Override
    public List<Weapon> getAvailableWeapons(Undead undead) {
        undead = (Undead) sessionFactory.getCurrentSession().get(Undead.class, undead.getId());
        List<Weapon> weapons = new ArrayList<Weapon>(undead.getWeapons());
        return weapons;
    }

    @Override
    public void disposeWeapon(Undead undead, Weapon weapon) {

        undead = (Undead) sessionFactory.getCurrentSession().get(Undead.class, undead.getId());
        weapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, weapon.getId());

        Weapon toUnequip = null;

        for (Weapon aWeapon :undead.getWeapons())
            if (aWeapon.getId().compareTo(weapon.getId()) == 0){
            toUnequip = aWeapon;
            break;
            }

            if (toUnequip != null)
                undead.getWeapons().remove(toUnequip);

            weapon.setCzyWyposazony(false);

    }

    @Override
    public Weapon findWeaponById(Long id) {
        return (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, id);

    }

    @Override
    public List<Weapon> getEquippedCars(Undead undead) {
        return null;
    }

    @Override
    public void unequipWeapon(Long undeadId, Long weaponId) {

        Undead undead = (Undead)sessionFactory.getCurrentSession().get(Undead.class, undeadId);

        Weapon weapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, weaponId);

        weapon.setCzyWyposazony(true);
        if(undead.getWeapons() == null){
            undead.setWeapons(new LinkedList<Weapon>());
        }
        undead.getWeapons().add(weapon);

    }

    @Override
    public void updateEnemy(Undead undead) {

        sessionFactory.getCurrentSession().update(undead);
    }
}
