package nl.novi.techiteasy.services;

import nl.novi.techiteasy.dtos.TelevisionDto;
import nl.novi.techiteasy.dtos.WallBracketDto;
import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import nl.novi.techiteasy.models.Television;
import nl.novi.techiteasy.models.TelevisionWallBracket;
import nl.novi.techiteasy.models.TelevisionWallBracketKey;
import nl.novi.techiteasy.models.WallBracket;
import nl.novi.techiteasy.repositories.TelevisionRepository;
import nl.novi.techiteasy.repositories.TelevisionWallBracketRepository;
import nl.novi.techiteasy.repositories.WallBracketRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TelevisionWallBracketService {


    private TelevisionRepository televisionRepository;

    private WallBracketRepository wallBracketRepository;

    private TelevisionWallBracketRepository televisionWallBracketRepository;

    public TelevisionWallBracketService(TelevisionRepository televisionRepository, WallBracketRepository wallBracketRepository, TelevisionWallBracketRepository televisionWallBracketRepository) {
        this.televisionRepository = televisionRepository;
        this.wallBracketRepository = wallBracketRepository;
        this.televisionWallBracketRepository = televisionWallBracketRepository;
    }

    public Collection<TelevisionDto> getTelevisionsByWallBracketId(Long wallBracketId) {
        Collection<TelevisionDto> dtos = new HashSet<>();
        Collection<TelevisionWallBracket> televisionWallbrackets = televisionWallBracketRepository.findAllByWallBracketId(wallBracketId);
        for (TelevisionWallBracket televisionWallbracket : televisionWallbrackets) {
            Television television = televisionWallbracket.getTelevision();
            TelevisionDto televisionDto = new TelevisionDto();

            televisionDto.setId(television.getId());
            televisionDto.setType(television.getType());
            televisionDto.setBrand(television.getBrand());
            televisionDto.setName(television.getName());
            televisionDto.setPrice(television.getPrice());
            televisionDto.setAvailableSize(television.getAvailableSize());
            televisionDto.setRefreshRate(television.getRefreshRate());
            televisionDto.setScreenType(television.getScreenType());
            televisionDto.setScreenQuality(television.getScreenQuality());
            televisionDto.setSmartTv(television.getSmartTv());
            televisionDto.setWifi(television.getWifi());
            televisionDto.setVoiceControl(television.getVoiceControl());
            televisionDto.setHdr(television.getHdr());
            televisionDto.setBluetooth(television.getBluetooth());
            televisionDto.setAmbiLight(television.getAmbiLight());
            televisionDto.setOriginalStock(television.getOriginalStock());
            televisionDto.setSold(television.getSold());

            dtos.add(televisionDto);
        }
        return dtos;
    }

    // Collection is de super klasse van zowel List als Set.
    public Collection<WallBracketDto> getWallBracketsByTelevisionId(Long televisionId) {
        //We gebruiken hier Set om te voorkomen dat er dubbele entries in staan.
        Set<WallBracketDto> dtos = new HashSet<>();
        List<TelevisionWallBracket> televisionWallbrackets = televisionWallBracketRepository.findAllByTelevisionId(televisionId);
        for (TelevisionWallBracket televisionWallbracket : televisionWallbrackets) {
            WallBracket wallBracket = televisionWallbracket.getWallBracket();
            var dto = new WallBracketDto();

            dto.setId(wallBracket.getId());
            dto.setName(wallBracket.getName());
            dto.setSize(wallBracket.getSize());
            dto.setAdjustable(wallBracket.getAdjustable());
            dto.setPrice(wallBracket.getPrice());

            dtos.add(dto);
        }
        return dtos;
    }


    public TelevisionWallBracketKey addTelevisionWallBracket(Long televisionId, Long wallBracketId) {
        var televisionWallBracket = new TelevisionWallBracket();
        if (!televisionRepository.existsById(televisionId)) {throw new RecordNotFoundException();}
        Television television = televisionRepository.findById(televisionId).orElse(null);
        if (!wallBracketRepository.existsById(wallBracketId)) {throw new RecordNotFoundException();}
        WallBracket wallBracket = wallBracketRepository.findById(wallBracketId).orElse(null);
        televisionWallBracket.setTelevision(television);
        televisionWallBracket.setWallBracket(wallBracket);
        TelevisionWallBracketKey id = new TelevisionWallBracketKey(televisionId, wallBracketId);
        televisionWallBracket.setId(id);
        televisionWallBracketRepository.save(televisionWallBracket);
        return id;
    }
}
