package com.s14222.tau;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;
import com.s14222.tau.repository.UndeadRepository;
import com.s14222.tau.repository.UndeadRepositoryFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import static org.hamcrest.CoreMatchers.*;


import java.util.List;

import org.junit.After;


public class UndeadTest {

    UndeadRepository undeadRepository;

    @Test
    public void TestDodania(){
        Undead undead1 = new Undead(7, "Trups",1,100,100);

        undeadRepository.add(undead1);

        assertNotNull(undeadRepository.getById(undead1.getId()));

    }

    @Test
    public void TestUsuniecia() {

        undeadRepository.deleteById(6);

        List<Undead> undeads = undeadRepository.getAll();

        assertNull(undeadRepository.getById(6).getNazwa());
        assertEquals(true, !undeads.isEmpty());

        }

    @Test
    public void TestUpdate() {

        Undead undeadToUpdate = undeadRepository.getById(2);

        undeadToUpdate.setNazwa("Zombie");

        undeadRepository.updateById(undeadToUpdate);

        assertThat(undeadRepository.getById(2).getNazwa(), is(undeadToUpdate.getNazwa()));
        assertEquals(undeadRepository.getById(2).getNazwa(), undeadToUpdate.getNazwa());

        assertFalse("nie powinno modyfikowac",undeadRepository.getById(4).getNazwa().equals(undeadToUpdate.getNazwa()));


    }

    @Test
    public void getAllTest(){

        List<Undead> undeadsList = undeadRepository.getAll();

        assertNotNull(undeadsList);

        Undead undead = undeadsList.get(3);
        assertNotNull(undead);

        try{
            Undead undeadToCatch = undeadsList.get(0);
        }

        catch(IndexOutOfBoundsException aIndexOutOfBoundsException){
            assertThat(aIndexOutOfBoundsException.getMessage(), is("Index: 3, Size: 3"));
        }
    }

    @Before
    public void initRepository(){
        undeadRepository = UndeadRepositoryFactory.getInstance();

        Undead zeroUndead = new Undead(0, "Trups", 1, 100, 100);
        Undead firstUndead = new Undead(1 , "Duch", 1, 200, 200);
        Undead secondUndead = new Undead(2, "Wampir", 2,200,150);
        Undead thirdUndead = new Undead(4, "Wilkolak", 1,100,100);
        Undead fourthUndead = new Undead(5, "Gnom", 4, 45, 50);
        Undead fifthUndead = new Undead(6, "Elf", 2, 120, 1);

        undeadRepository.add(zeroUndead);
        undeadRepository.add(firstUndead);
        undeadRepository.add(secondUndead);
        undeadRepository.add(thirdUndead);
        undeadRepository.add(fourthUndead);
        undeadRepository.add(fifthUndead);
    }

    
    @After
    public void dropRepository(){
        undeadRepository.dropTable();
    }
}

