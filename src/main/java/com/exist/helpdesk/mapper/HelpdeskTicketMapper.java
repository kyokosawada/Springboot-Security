package com.exist.helpdesk.mapper;

import com.exist.helpdesk.dto.helpdeskticket.*;
import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.model.Remark;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HelpdeskTicketMapper {

    @Mapping(target = "createdBy", source = "name")
    @Mapping(target = "updatedBy", source = "name")
    HelpdeskTicket toEntity(HelpdeskTicketCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HelpdeskTicketUpdateRequestDTO dto, @MappingTarget HelpdeskTicket entity);

    @Mapping(target = "assigneeId", source = "assignee.id")
    HelpdeskTicketResponseDTO toResponse(HelpdeskTicket ticket);

    Remark toEntity(RemarkCreateRequestDTO dto);

    RemarkResponseDTO remarkToDto(Remark remark);

    List<RemarkResponseDTO> remarksToDto(List<Remark> remarks);
}