package com.s14222.tau;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;
import com.s14222.tau.repository.UndeadRepository;
import com.s14222.tau.repository.UndeadRepositoryFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

public class UndeadTest {

    UndeadRepository undeadRepository;
    @Test
    public void TestDodania(){
        Undead undead = new Undead(0, "Trups",1,100,100);
        try {
            UndeadManagerImpl undeadManager = new UndeadManagerImpl();
            undeadManager.addUndead(undead);
            List<Undead> undeads = undeadManager.getAllUndeads();
            for(Undead u : undeads) {
                if(u.getNazwa().contains("Trups")){

                    return;
                }
            }
        } catch (SQLException e) {
            fail();
        }
        fail();

    }

    @Test
    public void TestUsuniecia() {
        Undead undead = new Undead(0, "Trups", 1, 100, 100);
        Undead toDelete = null;
        try {
            UndeadManagerImpl undeadManager = new UndeadManagerImpl();
            undeadManager.addUndead(undead);
            List<Undead> undeads = undeadManager.getAllUndeads();

            for (Undead u : undeads) {
                if (u.getNazwa().contains("Trups")) {
                    toDelete = u;
                }
            }
            if(toDelete == null) {
                fail();
            }
            undeadManager.deleteUndead(toDelete);
            undeads = undeadManager.getAllUndeads();
            for (Undead u : undeads) {
                if (u.getNazwa().contains("Trups")) {
                   fail();
                }
            }
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void TestUpdate() {

        Undead undeadToUpdate = undeadRepository.getById(2);

        undeadToUpdate.setNazwa("Zombie");
        undeadRepository.updateById(undeadToUpdate);

        assertEquals(undeadRepository.getById(2).getNazwa(), undeadToUpdate.getNazwa());
        assertNotNull(undeadRepository.getById(1));



    }

    @Before
    public void initRepository(){
        undeadRepository = UndeadRepositoryFactory.getInstance();

        Undead firstUndead = new Undead(1 , "Duch", 1, 200, 200);
        Undead secondUndead = new Undead(2, "Wampir", 2,200,150);
        Undead thirdUndead = new Undead(4, "Wilkolak", 1,100,100);
        Undead fourthUndead = new Undead(5, "Gnom", 4, 45, 50);
        Undead fifthUndead = new Undead(6, "Elf", 2, 120, 1);

        undeadRepository.add(firstUndead);
        undeadRepository.add(secondUndead);
        undeadRepository.add(thirdUndead);
        undeadRepository.add(fourthUndead);
        undeadRepository.add(fifthUndead);
    }
}

