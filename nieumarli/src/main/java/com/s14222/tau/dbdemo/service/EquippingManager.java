package com.s14222.tau.dbdemo.service;

import java.util.List;

import com.s14222.tau.domain.Undead;
import com.s14222.tau.domain.Weapon;

public interface EquippingManager {

    void addEnemy(Undead undead);
    List<Undead> getAllEnemies();
    void deleteEnemy(Undead undead);
    Undead findEnemyByName(String nazwa);

    Long addnewWeapon(Weapon weapon);
    List<Weapon> getAvailableWeapons();
    void disposeWeapon(Undead undead, Weapon weapon);
    Weapon findWeaponById(Long id);

    List<Weapon>getEquippedCars(Undead undead);
    void unequipWeapon( Long undeadId, Long weaponId);

    void updateEnemy(Undead enemy);
}
