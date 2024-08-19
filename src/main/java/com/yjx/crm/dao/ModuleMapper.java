package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.model.TreeDto;
import com.yjx.crm.vo.Module;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Module record);

    Integer insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Module record);

    Integer updateByPrimaryKey(Module record);

//    List<Module> queryAllModules(Integer roleId);
    List<TreeDto> queryAllModules();


    List<Module> queryModules();

    Module queryModuleByGradeAndModuleName(Integer grade, String moduleName);

    Module queryModuleByGradeAndUrl(Integer grade, String url);

    Module queryModuleByOptValue(String optValue);

    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    Integer countSubModuleByParentId(Integer mid);
}