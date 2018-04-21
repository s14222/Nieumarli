package com.s14222.tau.web;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class Api {

    /*@Autowired @Qualifier("undeadMan")
    UndeadManagerImpl undeadManager;*/

    @RequestMapping("/")
    public String index(){
        return "This is not a rest, just checking if everything works.";
    }

    @RequestMapping(value = "/undeads", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Undead> getUndead() throws SQLException{
        //  List<Undead> undeads = new LinkedList<>();
        //  undeads.addAll(undeadRepository.getAll());
        return null;
        // return undeads;
    }

    @RequestMapping(value = "/undead/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Undead getUndead(@PathVariable("id") int id) throws SQLException {
        //  return undeadRepository.getById(id);
        return null;

    }
}