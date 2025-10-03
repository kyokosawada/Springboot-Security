package com.exist.helpdesk.mapper;

import com.exist.helpdesk.dto.*;
import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Remark;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HelpdeskTicketMapper {

    @Mapping(target = "id", ignore = true)
    HelpdeskTicket toEntity(HelpdeskTicketCreateRequestDTO dto, Employee assignee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HelpdeskTicketUpdateRequestDTO dto, @MappingTarget HelpdeskTicket entity, @Context Employee assignee);

    @Mapping(target = "assigneeId", source = "assignee.id")
    HelpdeskTicketResponseDTO toResponse(HelpdeskTicket ticket);

    @Mapping(target = "addedAt", expression = "java(now)")
    Remark toEntity(RemarkCreateRequestDTO dto, @Context LocalDateTime now);

    RemarkResponseDTO remarkToDto(Remark remark);

    List<RemarkResponseDTO> remarksToDto(List<Remark> remarks);
}