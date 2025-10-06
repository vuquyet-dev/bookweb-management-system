package com.example.bookweb_management.mapper;

import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordCreateDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordResponseDTO;
import com.example.bookweb_management.dto.borrowrecorddto.BorrowRecordUpdateDTO;
import com.example.bookweb_management.entity.BorrowRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface BorrowRecordMapper {

    // Create -> Entity
    // map từ Long id bên Book -> Entity
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    BorrowRecord toEntity(BorrowRecordCreateDTO createDTO);

    // Update -> Entity
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    BorrowRecord toEntity(BorrowRecordUpdateDTO updateDTO);

    // Entity -> Response
//    @Mapping(source = "user", target = "user")
//    @Mapping(source = "book", target = "book")

    BorrowRecordResponseDTO toResponseDTO(BorrowRecord entity);

    // List<Entity> -> List<Response>
//    @Mapping(source = "user", target = "user")
//    @Mapping(source = "book", target = "book")
    List<BorrowRecordResponseDTO> toResponseDTOs(List<BorrowRecord> entities);

}
