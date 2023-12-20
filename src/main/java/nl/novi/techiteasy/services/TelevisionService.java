package nl.novi.techiteasy.services;

import nl.novi.techiteasy.repositories.CIModuleRepository;
import nl.novi.techiteasy.repositories.RemoteControllerRepository;
import nl.novi.techiteasy.repositories.TelevisionRepository;
import nl.novi.techiteasy.dtos.TelevisionDto;
import nl.novi.techiteasy.dtos.TelevisionInputDto;
import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import nl.novi.techiteasy.models.Television;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {
    private final TelevisionRepository televisionRepository;
    private final RemoteControllerRepository remoteControllerRepository;

    private final RemoteControllerService remoteControllerService;

    private final CIModuleRepository ciModuleRepository;

    private final CIModuleService ciModuleService;

    public TelevisionService(TelevisionRepository televisionRepository,
                             RemoteControllerRepository remoteControllerRepository,
                             RemoteControllerService remoteControllerService,
                             CIModuleRepository ciModuleRepository,
                             CIModuleService ciModuleService
    ){
        this.televisionRepository = televisionRepository;
        this.remoteControllerRepository = remoteControllerRepository;
        this.remoteControllerService = remoteControllerService;
        this.ciModuleRepository = ciModuleRepository;
        this.ciModuleService = ciModuleService;
    }

    public List<TelevisionDto> getAllTelevisions() {
        List<Television> tvList = televisionRepository.findAll();
        List<TelevisionDto> tvDtoList = new ArrayList<>();

        for(Television tv : tvList) {
            TelevisionDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    public List<TelevisionDto> getAllTelevisionsByBrand(String brand) {
        List<Television> tvList = televisionRepository.findAllTelevisionsByBrandEqualsIgnoreCase(brand);
        List<TelevisionDto> tvDtoList = new ArrayList<>();

        for(Television tv : tvList) {
            TelevisionDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    public TelevisionDto getTelevisionById(Long id) {
        Optional<Television> televisionOptional = televisionRepository.findById(id);
        if (televisionOptional.isPresent()){
            Television tv = televisionOptional.get();
            return transferToDto(tv);
        } else {
            throw new RecordNotFoundException("geen televisie gevonden");
        }
    }

    public TelevisionDto addTelevision(TelevisionInputDto dto) {

        Television tv = transferToTelevision(dto);
        televisionRepository.save(tv);

        return transferToDto(tv);
    }

    public void deleteTelevision(@RequestBody Long id) {

        televisionRepository.deleteById(id);

    }

    public TelevisionDto updateTelevision(Long id, TelevisionInputDto newTelevision) {

        Optional<Television> televisionOptional = televisionRepository.findById(id);
        if (televisionOptional.isPresent()){

            Television television1 = televisionOptional.get();

            television1.setAmbiLight(newTelevision.getAmbiLight());
            television1.setAvailableSize(newTelevision.getAvailableSize());
            television1.setAmbiLight(newTelevision.getAmbiLight());
            television1.setBluetooth(newTelevision.getBluetooth());
            television1.setBrand(newTelevision.getBrand());
            television1.setHdr(newTelevision.getHdr());
            television1.setName(newTelevision.getName());
            television1.setOriginalStock(newTelevision.getOriginalStock());
            television1.setPrice(newTelevision.getPrice());
            television1.setRefreshRate(newTelevision.getRefreshRate());
            television1.setScreenQuality(newTelevision.getScreenQuality());
            television1.setScreenType(newTelevision.getScreenType());
            television1.setSmartTv(newTelevision.getSmartTv());
            television1.setSold(newTelevision.getSold());
            television1.setType(newTelevision.getType());
            television1.setVoiceControl(newTelevision.getVoiceControl());
            television1.setWifi(newTelevision.getWifi());
            Television returnTelevision = televisionRepository.save(television1);

            return transferToDto(returnTelevision);
        } else {
            throw new  RecordNotFoundException("geen televisie gevonden");
        }
    }

    public Television transferToTelevision(TelevisionInputDto dto){
        var television = new Television();

        television.setType(dto.getType());
        television.setBrand(dto.getBrand());
        television.setName(dto.getName());
        television.setPrice(dto.getPrice());
        television.setAvailableSize(dto.getAvailableSize());
        television.setRefreshRate(dto.getRefreshRate());
        television.setScreenType(dto.getScreenType());
        television.setScreenQuality(dto.getScreenQuality());
        television.setSmartTv(dto.getSmartTv());
        television.setWifi(dto.getWifi());
        television.setVoiceControl(dto.getVoiceControl());
        television.setHdr(dto.getHdr());
        television.setBluetooth(dto.getBluetooth());
        television.setAmbiLight(dto.getAmbiLight());
        television.setOriginalStock(dto.getOriginalStock());
        television.setSold(dto.getSold());

        return television;
    }

    public TelevisionDto transferToDto(Television television){
        TelevisionDto dto = new TelevisionDto();

        dto.setId(television.getId());
        dto.setType(television.getType());
        dto.setBrand(television.getBrand());
        dto.setName(television.getName());
        dto.setPrice(television.getPrice());
        dto.setAvailableSize(television.getAvailableSize());
        dto.setRefreshRate(television.getRefreshRate());
        dto.setScreenType(television.getScreenType());
        dto.setScreenQuality(television.getScreenQuality());
        dto.setSmartTv(television.getWifi());
        dto.setWifi(television.getWifi());
        dto.setVoiceControl(television.getVoiceControl());
        dto.setHdr(television.getHdr());
        dto.setBluetooth(television.getBluetooth());
        dto.setAmbiLight(television.getAmbiLight());
        dto.setOriginalStock(television.getOriginalStock());
        dto.setSold(television.getSold());
        if(television.getCiModule() != null){
            dto.setCiModuleDto(CIModuleService.transferToDto(television.getCiModule()));
        }

        return dto;
    }

    public void assignRemoteControllerToTelevision(Long id, Long remoteControllerId) {
        var optionalTelevision = televisionRepository.findById(id);
        var optionalRemoteController = remoteControllerRepository.findById(remoteControllerId);

        if(optionalTelevision.isPresent() && optionalRemoteController.isPresent()) {
            var television = optionalTelevision.get();
            var remoteController = optionalRemoteController.get();

            television.setRemoteController(remoteController);
            televisionRepository.save(television);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public void assignCIModuleToTelevision(Long id, Long ciModuleId) {
        var optionalTelevision = televisionRepository.findById(id);
        var optionalCIModule = ciModuleRepository.findById(ciModuleId);

        if(optionalTelevision.isPresent() && optionalCIModule.isPresent()) {
            var television = optionalTelevision.get();
            var ciModule = optionalCIModule.get();

            television.setCiModule(ciModule);
            televisionRepository.save(television);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
