package com.s14222.tau.web;

import com.s14222.tau.domain.Undead;
import com.s14222.tau.repository.UndeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class UndeadApi {

    @Autowired
    UndeadRepository undeadRepository;

    @RequestMapping("/")
    public String index(){
        return "This is not a rest, just checking if everything works.";
    }

    @RequestMapping(value = "/undeads", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Undead> getUndead() throws SQLException{
        List<Undead> undeads = new LinkedList<>();
        undeads.addAll(undeadRepository.getAll());

        return undeads;
    }

    @RequestMapping(value = "/undead/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Undead getUndead(@PathVariable("id") int id) throws SQLException{
        return undeadRepository.getById(id);
    }

    @RequestMapping(value  = "/undead/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public int deleteUndead(@PathVariable("id") int id){
        return (int) undeadRepository.deleteById(id);

    }

    @RequestMapping(value = "/undead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int addUndead (@RequestBody Undead u) {
        return (int) undeadRepository.add(u);
    }

    @RequestMapping(value = "/undead", method = RequestMethod.PUT)
    public int updateAccount (@RequestBody Undead u) throws SQLException {
        return (int) undeadRepository.updateById(u);
    }

}
