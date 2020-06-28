package com.dsg.service.ws;

import com.dsg.common.dto.GetMenuInputDto;
import com.powerbridge.sso.dto.ProjectDto;

import java.util.List;

public interface IAPIService {

    List<ProjectDto> selectList(GetMenuInputDto inputDto);

    List<ProjectDto> getProjectCode(GetMenuInputDto inputDto);

}
