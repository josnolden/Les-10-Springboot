package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.dtos.IdInputDto;
import nl.novi.techiteasy.dtos.TelevisionDto;
import nl.novi.techiteasy.dtos.TelevisionInputDto;
import nl.novi.techiteasy.dtos.WallBracketDto;
import nl.novi.techiteasy.services.TelevisionService;
import nl.novi.techiteasy.services.TelevisionWallBracketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;

@RestController
public class TelevisionController {
    private final TelevisionService televisionService;

    private final TelevisionWallBracketService televisionWallBracketService;

    public TelevisionController(TelevisionService televisionService,
                                TelevisionWallBracketService televisionWallBracketService){
        this.televisionService = televisionService;
        this.televisionWallBracketService = televisionWallBracketService;
    }


    @GetMapping
    public ResponseEntity<List<TelevisionDto>> getAllTelevisions(@RequestParam(value = "brand", required = false) Optional<String> brand) {
        List<TelevisionDto> dtos;
        if (brand.isEmpty()){
            dtos = televisionService.getAllTelevisions();
        }
        else {
            dtos = televisionService.getAllTelevisionsByBrand(brand.get());
        }
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/televisions/{id}")
    public ResponseEntity<TelevisionDto> getTelevision(@PathVariable("id")Long id) {
        TelevisionDto television = televisionService.getTelevisionById(id);
        return ResponseEntity.ok().body(television);
    }

    @PostMapping("/televisions")
    public ResponseEntity<Object> addTelevision(@Valid @RequestBody TelevisionInputDto televisionInputDto) {
        TelevisionDto dto = televisionService.addTelevision(televisionInputDto);
        return ResponseEntity.created(null).body(dto);

    }

    @DeleteMapping("/televisions/{id}")
    public ResponseEntity<Object> deleteTelevision(@PathVariable Long id) {
        televisionService.deleteTelevision(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/televisions/{id}")
    public ResponseEntity<Object> updateTelevision(@PathVariable Long id, @Valid @RequestBody TelevisionInputDto newTelevision) {
        TelevisionDto dto = televisionService.updateTelevision(id, newTelevision);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/televisions/{id}/remotecontroller")
    public ResponseEntity<Object> assignRemoteControllerToTelevision(@PathVariable("id") Long id,@Valid @RequestBody IdInputDto input) {
        televisionService.assignRemoteControllerToTelevision(id, input.id);
        return ResponseEntity.noContent().build();
    }
}
