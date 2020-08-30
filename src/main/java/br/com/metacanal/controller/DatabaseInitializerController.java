package br.com.metacanal.controller;

import br.com.metacanal.service.BasicDatabaseInitializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Check localhost:8081/swagger-ui.html for the Swagger Documentation!
 */

@Api(description = "Database Initializer Controller")
@RestController
public class DatabaseInitializerController {

    private final BasicDatabaseInitializer basicDatabaseInitializer;

    @Autowired
    private DatabaseInitializerController(BasicDatabaseInitializer basicDatabaseInitializer) {
        this.basicDatabaseInitializer = basicDatabaseInitializer;
    }

    @ApiOperation(value = "This route will create a new database entry")
    @PostMapping(path = "/initializeDb")
    public ResponseEntity<String> createInitialDatabase() {
        basicDatabaseInitializer.buildData();
        return new ResponseEntity<>("Database initialized", HttpStatus.OK);
    }


}
