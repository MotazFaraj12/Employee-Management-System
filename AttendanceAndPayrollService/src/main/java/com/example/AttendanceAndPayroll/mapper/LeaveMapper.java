package com.example.AttendanceAndPayroll.mapper;

import com.example.AttendanceAndPayroll.DTO.LeaveDTO;
import com.example.AttendanceAndPayroll.DTO.LeaveRequest;
import com.example.AttendanceAndPayroll.model.Leave;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface LeaveMapper {
    LeaveDTO LeaveToLeaveDTO(Leave leave);
    Leave LeaveDToTOLLeave(LeaveDTO leaveDTO);
    List<LeaveDTO> LeaveListToLeaveDTOList(List<Leave> leaves);
    List<Leave> LeaveDTOListToLeaveList(List<LeaveDTO> leaveDTOS);
    Leave LeaveRequestToLeave(LeaveRequest leaveRequest);

}
