package com.wstro.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.controller.base.BaseController;
import com.powerbridge.core.util.UUIDGenerator;
import com.powerbridge.core.util.toolbox.StringUtil;
//import com.powerbridge.sso.config.RepeatSubmitValidation;
import com.wstro.entity.setting.SsoPwConfig;
import com.wstro.entity.setting.SsoUser;
import com.wstro.service.setting.ISsoPwConfigService;
import com.wstro.service.setting.ISsoUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @Description: SsoPwConfig控制层
 * 注意：该类通过平台代码自动生成，有些地方如：字段处理因通用性问题有缺失，请根据实际情况增加
 * @author: powerbridge@powerbridge.com
 * @Date: 2019年07月09日 10:56:45
 */
@Slf4j
@RequestMapping("/ssoPwConfig")
@RestController
@Api(description=" 操作接口", tags= {"SsoPwConfigController"})
public class SsoPwConfigController extends BaseController {

    @Autowired
    private ISsoPwConfigService ssoPwConfigService;
    @Autowired
    private ISsoUserService ssoUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增
     *
     * @param ssoPwConfig
     * @return
     */
//    @RepeatSubmitValidation
    @PostMapping("/list/add")
    @ApiOperation(value="新增")
    @ApiResponses({
        @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional

    public AjaxResult addSsoPwConfig(@RequestBody SsoPwConfig ssoPwConfig) {
        String oid = UUIDGenerator.getUUID();
        AjaxResult ajaxResult = null;
        try {
            String inputCopNo ="";
            com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser==null){
                return setErrorJson("获取企业编号失败。");
            }else{
                inputCopNo = systemUser.getInputCopNo();
            }
            ssoPwConfig.setOid(oid);
            Integer pwleng = ssoPwConfig.getPwLeng();
            Integer validDay = ssoPwConfig.getValidDay();
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("INPUT_COP_NO",inputCopNo);
            SsoPwConfig config = ssoPwConfigService.getOne(entityWrapper);
            if(config!=null){
                return setErrorJson("该企业已存在密码校验规则，无法再新增密码校验规则！");
            }
            if(pwleng<8){
                return setErrorJson("密码至少长度为8");
            }
            if(pwleng>32){
                return setErrorJson("密码最大长度为32");
            }
            if(validDay<1){
                return setErrorJson("密码有效日期不能小于1天");
            }
            if(validDay>90){
                return setErrorJson("密码有效日期不能大于90天");
            }
            ssoPwConfig.setPwLittleLetter("Y");
            ssoPwConfig.setCreateTime(new Date());
            ssoPwConfig.setUpdateTime(new Date());
            ssoPwConfig.setCreateName(systemUser.getInputCopName());
            ssoPwConfig.setUpdateName(systemUser.getInputCopName());
            ssoPwConfig.setCreateBy(systemUser.getUserId());
            ssoPwConfig.setUpdateBy(systemUser.getUserId());
            ssoPwConfig.setInputCopName(systemUser.getInputCopName());
            ssoPwConfig.setInputCopNo(inputCopNo);
            ssoPwConfig.setPwNumber("Y");
            boolean flag = ssoPwConfigService.save(ssoPwConfig);
            if (flag) {
                ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoPwConfig);
            } else {
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("addSsoPwConfig()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 根据id删除
     *
     * @param id oid主键
     * @return
     */
    @DeleteMapping("/list/{id}/delete")
    @ApiOperation(value="根据id删除")
    @ApiResponses({
        @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional
//    @RepeatSubmitValidation
    public AjaxResult deleteSsoPwConfigById(@ApiParam(value="id", name="id", required=true) @PathVariable String id) {
        try {
            //再删除主表数据
            if (ssoPwConfigService.removeById(id)) {
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            }
        } catch (Exception e) {
            log.error("deleteSsoPwConfigById()--err", e);
            return json(MessageConstants.SSO_STATUS_FAIL, e.getMessage());
        }
        return result(MessageConstants.SSO_STATUS_FAIL);
    }

    /**
     * @return
     */
//    @RepeatSubmitValidation
    @PostMapping("/list/checkOutPwd")
    @ApiOperation(value="密码校验")
    @ApiResponses({
            @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional
    public AjaxResult checkOutPwd(@ApiParam(value="pwd", name="pwd", required=true) @RequestParam("pwd") String pwd) {
        String regex = "";
        String message = "";
        String inputCopNo ="";
        try {
            com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser==null){
                return setErrorJson("获取企业编号失败。");
            }else{
                inputCopNo = systemUser.getInputCopNo();
            }
//            if(StringUtil.isEmpty(inputCopNo)){
//                return setErrorJson("获取登录用户企业失败。");
//            }else{
                QueryWrapper entityWrapper = new QueryWrapper();
                entityWrapper.eq("INPUT_COP_NO",inputCopNo);
                SsoPwConfig ssoPwConfig = ssoPwConfigService.getOne(entityWrapper);
                if(ssoPwConfig!=null){
                    Integer pwLeng = ssoPwConfig.getPwLeng();
                    Boolean pwCapitalLetter = "Y".equals(ssoPwConfig.getPwCapitalLetter())? true:false;
                    Boolean pwSpecoaCharacters = "Y".equals(ssoPwConfig.getPwSpecoaCharacters())? true:false;
                    if(pwCapitalLetter&&pwSpecoaCharacters){
                        regex = "^(?![a-zA-z0-9]+$)(?![A-Z0-9\\W]+$)(?![a-z0-9\\W]+$)[a-zA-Z0-9\\W]{"+pwLeng.toString()+",18}$";//匹配大写字母+小写字母+数字+特殊字符的组合
                        message = "密码不符合字符长度为"+pwLeng.toString()+"-18+大写字母+小写字母+数字+特殊字符的校验规则！";
                    }else if(pwCapitalLetter&&!pwSpecoaCharacters){
                        regex = "^(?![a-z0-9]+$)(?![A-Z0-9]+$)[a-zA-Z0-9]{"+pwLeng.toString()+",18}$";//匹配小写字母+大写字母+数字的组合
                        message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+大写字母+数字的组合的校验规则！";
                    }else if(!pwCapitalLetter&&pwSpecoaCharacters){
                        regex = "^(?![a-z0-9]+$)(?![0-9\\W]+$)[a-z0-9\\W]{"+pwLeng.toString()+",18}$";//匹配小写字母+数字+特殊字符
                        message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+数字+特殊字符的校验规则！";
                    }else if(!pwCapitalLetter&&!pwSpecoaCharacters){
                        regex = "^(?![0-9]+$)(?![a-z]+$)[a-z0-9]{"+pwLeng.toString()+",18}$"; //匹配小写字母+数字的组合
                        message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+数字的校验规则！";
                    }
                }else{
                    regex = "^(?![0-9]+$)(?![a-z]+$)[a-z0-9]{8,18}$"; //匹配小写字母+数字的组合
                    message = "密码不符合长度为8-18+小写字母+数字的校验规则！";
                }
            //}
            if(pwd.matches(regex)){
                return json(MessageConstants.SSO_STATUS_SUCCESS, pwd);
            }else{
                return setErrorJson(message);
            }
        } catch (Exception e) {
            log.error("checkOutPwd()--err", e);
            return json(MessageConstants.SSO_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * @return
     */
//    @RepeatSubmitValidation
    @PostMapping("/list/getRegex")
    @ApiOperation(value="获取密码校验规则")
    @ApiResponses({
            @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional
    public AjaxResult getRegex() {
        String regex = "";
        String message = "";
        String inputCopNo ="";
        try {
            com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser==null){
                return setErrorJson("获取密码校验规则失败");
            }else{
                inputCopNo = systemUser.getInputCopNo();
            }
//            if(StringUtil.isEmpty(inputCopNo)){
//                return setErrorJson("获取登录用户企业失败。");
//            }else{
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("INPUT_COP_NO",inputCopNo);
            SsoPwConfig ssoPwConfig = ssoPwConfigService.getOne(entityWrapper);
            if(ssoPwConfig!=null){
                Integer pwLeng = ssoPwConfig.getPwLeng();
                Boolean pwCapitalLetter = "Y".equals(ssoPwConfig.getPwCapitalLetter())? true:false;
                Boolean pwSpecoaCharacters = "Y".equals(ssoPwConfig.getPwSpecoaCharacters())? true:false;
                if(pwCapitalLetter&&pwSpecoaCharacters){
                    regex = "^(?![a-zA-z0-9]+$)(?![A-Z0-9\\W]+$)(?![a-z0-9\\W]+$)[a-zA-Z0-9\\W]{"+pwLeng.toString()+",18}$";//匹配大写字母+小写字母+数字+特殊字符的组合
                    message = "密码不符合字符长度为"+pwLeng.toString()+"-18+大写字母+小写字母+数字+特殊字符的校验规则！";
                }else if(pwCapitalLetter&&!pwSpecoaCharacters){
                    regex = "^(?![a-z0-9]+$)(?![A-Z0-9]+$)[a-zA-Z0-9]{"+pwLeng.toString()+",18}$";//匹配小写字母+大写字母+数字的组合
                    message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+大写字母+数字的组合的校验规则！";
                }else if(!pwCapitalLetter&&pwSpecoaCharacters){
                    regex = "^(?![a-z0-9]+$)(?![0-9\\W]+$)[a-z0-9\\W]{"+pwLeng.toString()+",18}$";//匹配小写字母+数字+特殊字符
                    message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+数字+特殊字符的校验规则！";
                }else if(!pwCapitalLetter&&!pwSpecoaCharacters){
                    regex = "^(?![0-9]+$)(?![a-z]+$)[a-z0-9]{"+pwLeng.toString()+",18}$"; //匹配小写字母+数字的组合
                    message = "密码不符合长度为"+pwLeng.toString()+"-18+小写字母+数字的校验规则！";
                }
            }else{
                regex = "^(?![0-9]+$)(?![a-z]+$)[a-z0-9]{8,18}$"; //匹配小写字母+数字的组合
                message = "密码不符合长度为8-32+小写字母+数字的校验规则！";
            }
            //}
            return json(MessageConstants.SSO_STATUS_SUCCESS, regex+"@"+message);
        } catch (Exception e) {
            log.error("getRegex()--err", e);
            return json(MessageConstants.SSO_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 批量删除操作
     *
     * @param
     * @return AjaxResult
     */
    @DeleteMapping("/list/deleteByList")
    @ApiOperation(value="批量删除")
    @ApiResponses({
        @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional
    public AjaxResult deleteSsoPwConfigByList(@ApiParam(value="idList", name="idList", required=true)  @RequestParam("idList") String idList) {
        AjaxResult ajaxResult = null;
        try {
            //获取前台传输的oid集合
            boolean flag = ssoPwConfigService.deleteBySeqNoList(Arrays.asList(idList.split(",")));
            if (flag) {
                ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            } else {
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("deleteSsoPwConfigByList()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 修改
     *
     * @param ssoPwConfig 详细信息
     * @return
     */
    @PostMapping("/list/update")
    @ApiOperation(value="修改")
    @ApiResponses({
        @ApiResponse(response=Boolean.class, code = 1, message = "ok")
    })
    @Transactional
    public AjaxResult updateSsoPwConfig(@RequestBody SsoPwConfig ssoPwConfig) {
        AjaxResult ajaxResult = null;
            try {
            Integer pwleng = ssoPwConfig.getPwLeng();
            Integer validDay = ssoPwConfig.getValidDay();
            if(pwleng<8){
                return setErrorJson("密码至少长度为8");
            }
            if(pwleng>32){
                return setErrorJson("密码最大长度为32");
            }
            if(validDay<1){
                return setErrorJson("密码有效日期不能小于1天");
            }
            if(validDay>90){
                return setErrorJson("密码有效日期不能大于90天");
            }
            ssoPwConfig.setUpdateTime(new Date());
            boolean flag = ssoPwConfigService.updateById(ssoPwConfig);
            if (flag) {
                ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoPwConfig);
            } else {
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("updateSsoPwConfig()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 根据id查询信息
     *
     * @return
     */
    @GetMapping("/list/view")
    @ApiOperation(value="根据id查询")
    @ApiResponses({
        @ApiResponse(response=SsoPwConfig.class, code = 1, message = "ok")
    })

    public AjaxResult selectSsoPwConfigById() {
        AjaxResult ajaxResult;
        String inputCopNo="";
        try {
            com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser==null){
                return setErrorJson("获取登录用户失败。");
            }else{
                inputCopNo = systemUser.getInputCopNo();
            }
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("INPUT_COP_NO",inputCopNo);
            SsoPwConfig ssoPwConfig = ssoPwConfigService.getOne(entityWrapper);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoPwConfig);
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("selectSsoPwConfigById()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 分页查询
     *
     * @param ssoPwConfig
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value="分页查询")
    @ApiResponses({
        @ApiResponse(response=SsoPwConfig.class, code = 1, message = "ok")
    })

    public AjaxResult selectSsoPwConfigList(SsoPwConfig ssoPwConfig) {
        //TODO 如要做非空检查，可在这里添加

        AjaxResult ajaxResult = null;
        try {
            Page page = getPage();  // 分页
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));
//            if (StringUtil.isNotEmpty(sort)) {
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);  // 排序
//            } else {
//                //默认按录入日期倒排序
//                page.setAsc(false);
//                page.setOrderByField("decTime");  // 排序
//            }
            Page<SsoPwConfig> ssoPwConfigPage = ssoPwConfigService.selectSsoPwConfigList(page, ssoPwConfig);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoPwConfigPage.getRecords(), (int) page.getTotal());
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("selectSsoPwConfigList()--error", e);
        }
        return ajaxResult;
    }

    @RequestMapping(value = "/checkPwdValidateDate",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="密码有效期校验")
    @ApiResponses({
            @ApiResponse(response=SsoPwConfig.class, code = 1, message = "ok")
    })

    public AjaxResult checkPwdValidateDate(){
        AjaxResult ajaxResult=null;
        String inputCopNo = null;
        long day = 1000*24*60*60;
        long validateTime = 0;
        long validateDay = 0;
        long maxTime = 0;
        try{
            com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser==null){
                return setErrorJson("获取登录用户失败。");
            }else{
                inputCopNo = systemUser.getInputCopNo();
            }
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("INPUT_COP_NO",inputCopNo);
            SsoPwConfig ssoPwConfig = ssoPwConfigService.getOne(entityWrapper);

            QueryWrapper<SsoUser> userWrapper = new QueryWrapper<>();
            userWrapper.eq("USER_NAME",systemUser.getUserName());
            SsoUser ssoUser = ssoUserService.getOne(userWrapper);
            Date date = new Date();

            if(ssoPwConfig!=null){
                maxTime = ssoPwConfig.getValidDay()*24*60*60*1000L;
            }else{
                maxTime=80*24*60*60*1000L;
            }
            //判断用户企业是否做了密码策略
            if(ssoUser!=null){
                if(ssoUser.getUpdpwdTime()==null){
                    ssoUser.setUpdpwdTime(new Date());
                    ssoUserService.updateById(ssoUser);
                    ajaxResult= json(MessageConstants.SSO_STATUS_SUCCESS,ssoPwConfig);
                }else{
                    //密码到期时间=更新密码时间+有效日期-系统时间
                    validateTime = ssoUser.getUpdpwdTime().getTime()+maxTime-date.getTime();
                    validateDay = validateTime/day;
                    if(validateDay<=0){
                        ajaxResult= setErrorJson("密码已经过期，请修改密码。");
                    }else if(validateDay<=5){
                        ajaxResult= json(MessageConstants.SSO_STATUS_SUCCESS,false);
                    }else{
                        ajaxResult= json(MessageConstants.SSO_STATUS_SUCCESS,true);
                    }
                }
            }
        }catch (Exception e){
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("checkPwdValidateDate()--error", e);
        }
        return  ajaxResult;
    }

}