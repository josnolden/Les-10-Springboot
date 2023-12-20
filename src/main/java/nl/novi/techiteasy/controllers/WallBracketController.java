package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.dtos.TelevisionDto;
import nl.novi.techiteasy.dtos.WallBracketDto;
import nl.novi.techiteasy.services.TelevisionWallBracketService;
import nl.novi.techiteasy.services.WallBracketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class WallBracketController {
    private final WallBracketService wallBracketService;
    private final TelevisionWallBracketService televisionWallBracketService;
    public WallBracketController(WallBracketService wallBracketService,
                                 TelevisionWallBracketService televisionWallBracketService) {
        this.wallBracketService = wallBracketService;
        this.televisionWallBracketService = televisionWallBracketService;
    }

    @GetMapping("/wallbrackets")
    public ResponseEntity<List<WallBracketDto>> getAllWallBrackets() {

        List<WallBracketDto> wallBrackets = wallBracketService.getAllWallBrackets();

        return ResponseEntity.ok(wallBrackets);
    }

    @GetMapping("/wallbrackets/{id}")
    public ResponseEntity<WallBracketDto> getWallBracket(@PathVariable("id") Long id) {

        WallBracketDto wallBracketDto = wallBracketService.getWallBracket(id);

        return ResponseEntity.ok(wallBracketDto);
    }

    @PostMapping("/wallbrackets")
    public ResponseEntity<WallBracketDto> addWallBracket(@RequestBody WallBracketDto dto) {
        WallBracketDto wallBracket = wallBracketService.addWallbracket(dto);
        return ResponseEntity.created(null).body(wallBracket);
    }

    @DeleteMapping("/wallbrackets/{id}")
    public ResponseEntity<Object> deleteWallBracket(@PathVariable("id") Long id) {
        wallBracketService.deleteWallBracket(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/wallbrackets/{id}")
    public ResponseEntity<WallBracketDto> updateWallBracket(@PathVariable("id") Long id, @RequestBody WallBracketDto dto) {
        var returnDto = wallBracketService.updateWallBracket(id, dto);
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("/wallbrackets/televisions/{wallBracketId}")
    public ResponseEntity<Collection<TelevisionDto>> getTelevisionsByWallBracketId(@PathVariable("wallBracketId") Long wallBracketId){
        return ResponseEntity.ok(televisionWallBracketService.getTelevisionsByWallBracketId(wallBracketId));
    }
}
