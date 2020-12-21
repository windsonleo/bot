package com.tecsoluction.bot.framework;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Classe abstrata para criar um controller básico
 *
 * @param <Entity>
 */


public abstract class AbstractRestController<Entity> {


    protected abstract AbstractEntityService<Entity> getservice();


    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entity> AdicionarEntity(@RequestBody Entity entity) {
        try {
            getservice().validateSave(entity);
            getservice().save(entity);
            return new ResponseEntity<Entity>(entity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Entity>(entity, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entity> buscarEntity(@PathVariable String id) {


        Entity entity = getservice().findOne(UUID.fromString(id));

        if (entity == null) {
            return new ResponseEntity<Entity>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Entity>(entity, HttpStatus.OK);
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
//    public @ResponseBody List<Entity> listarEntity() {
//        return getservice().findAll();
//
//    }
    

    @RequestMapping(method = RequestMethod.GET)
    public  List<Entity> listarEntity() {
      return getservice().findAll();

  }   
    
    

    @Transactional
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void updateEntity(@PathVariable Entity entity) {
        getservice().validateEdit(entity);
        getservice().save(entity);
    }


    @Transactional
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteEntity(@PathVariable String id) {
        UUID idf = UUID.fromString(id);
        getservice().validateDelete(idf);
        getservice().delete(idf);
    }


}