package nl.novi.techiteasy.dtos;

import nl.novi.techiteasy.models.Television;

import java.util.Date;

public class SalesInfoDto {
    private Long id;
    private Double price;
    private Integer originalStock;
    private Integer sold;
    public static SalesInfoDto salesInfoDto (Television television){
        var dto = new SalesInfoDto();
        dto.id = television.getId();
        dto.price = television.getPrice();
        dto.originalStock = television.getOriginalStock();
        dto.sold = television.getSold();
        return dto;
    }
}
