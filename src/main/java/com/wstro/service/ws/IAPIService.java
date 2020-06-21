package com.wstro.service.ws;

import com.wstro.common.dto.GetMenuInputDto;
import com.powerbridge.sso.dto.ProjectDto;

import java.util.List;

public interface IAPIService {

    List<ProjectDto> selectList(GetMenuInputDto inputDto);

    List<ProjectDto> getProjectCode(GetMenuInputDto inputDto);

}
