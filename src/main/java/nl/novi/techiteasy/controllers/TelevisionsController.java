package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/televisions")
public class TelevisionsController {
    private ArrayList<String> televisionDataBase = new ArrayList<>();

    @GetMapping
    public ResponseEntity<ArrayList<String>> getAllTelevisions() {
        return ResponseEntity.ok(this.televisionDataBase);
    }

    @GetMapping("/{index}")
    public ResponseEntity<String> getTelevision(@PathVariable int index) {
        if (index >= 0 && index < this.televisionDataBase.size()) {
            return ResponseEntity.ok(this.televisionDataBase.get(index));
        }
        else {
            throw new RecordNotFoundException("Television not found");
        }
    }

    @PostMapping
    public ResponseEntity<String> createTelevision(@RequestBody String television) {
        this.televisionDataBase.add(television);

        return ResponseEntity.created(null).body(television);
    }

    @PutMapping("/{index}")
    public ResponseEntity<String> updateTelevision(@PathVariable int index, @RequestBody String television) {
        if (index >= 0 && index < this.televisionDataBase.size()) {
            this.televisionDataBase.set(index, television);
            return ResponseEntity.ok(television);
        }
        else {
            throw new RecordNotFoundException("Television not found");
        }
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<String> DeletePerson(@PathVariable int index) {
        if (index >= 0 && index < this.televisionDataBase.size()) {
            this.televisionDataBase.remove(index);
            return new ResponseEntity<>("Television deleted", HttpStatus.NO_CONTENT);
        }
        else {
            throw new RecordNotFoundException("Television not found");
        }
    }
}
