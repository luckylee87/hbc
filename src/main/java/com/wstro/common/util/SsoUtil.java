package com.wstro.common.util;

import com.wstro.common.constants.ChkStatusConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SsoUtil
 * 类描述：项目工具类
 * 创建人：danagao
 * 创建时间：2017/6/1 19:42
 *
 * @version 1.0
 */
public class SsoUtil {

    //可以修改的状态，可以申报的状态
    private static final List chkStatusEditList = new ArrayList();

    static {
        chkStatusEditList.add(ChkStatusConstant.CHK_STATUS_S);//暂存
        chkStatusEditList.add(ChkStatusConstant.CHK_STATUS_N);//审批不通过
    }
}
