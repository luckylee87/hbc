package com.wstro.dao.ws;

import com.powerbridge.sso.dto.FunctionDto;
import com.powerbridge.sso.dto.ProjectDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APIMapper {

    ProjectDto selectProjectByCode(@Param("projectCode") String projectCode);

//    List<ProjectDto> selectListByProjectCodes(@Param("projectCodes") String projectCodes);

    List<FunctionDto> selectFuncList(@Param("userName") String userName, @Param("projectCode") String projectCode);

}
