package com.dsg.common.constants;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ChkStatusConstant
 * 类描述：单据状态常量
 * 创建人：danagao
 * 创建时间：2017/6/1 19:42
 *
 * @version 1.0
 */
public class ChkStatusConstant {

    /*********************单据状态*****************************/
    public static final String CHK_STATUS_S = "S";//暂存
    public static final String CHK_STATUS_D = "D";//提交申报
    public static final String CHK_STATUS_1 = "1";//入库成功
    public static final String CHK_STATUS_2 = "2";//入库失败
    public static final String CHK_STATUS_3 = "3";//待审
    public static final String CHK_STATUS_P = "P";//审批通过
    public static final String CHK_STATUS_N = "N";//审批不通过

    /*********************审批状态*****************************/
    public static final String EMAPV_MARKCD_SAS_A = "A";//暂存
    public static final String EMAPV_MARKCD_SAS_B = "B";//申报
    public static final String EMAPV_MARKCD_SAS_1 = "1";//通过
    public static final String EMAPV_MARKCD_SAS_2 = "2";//转人工
    public static final String EMAPV_MARKCD_SAS_3 = "3";//退单
    public static final String EMAPV_MARKCD_SAS_Y = "Y";//入库成功
    public static final String EMAPV_MARKCD_SAS_Z = "Z";//入库失败

    /*********************申报类型*****************************/
    public static final String DCL_TYPECD_1 = "1";//备案
    public static final String DCL_TYPECD_2 = "2";//变更申报
    public static final String DCL_TYPECD_3 = "3";//结案

    /*********************申报类型*****************************/
    public static final String CHGOFF_TYPECD_1 = "1";//正常申报
    public static final String CHGOFF_TYPECD_2 = "2";//补充申报
    public static final String CHGOFF_TYPECD_3 = "3";//海关处置
    /*********************申报类型*****************************/
    public static final String DCL_TYPECD_STOCK_1 = "1";//备案
    public static final String DCL_TYPECD_STOCK_2 = "2";//变更
    public static final String DCL_TYPECD_STOCK_3 = "3";//作废

    /*********************修改标志代码类型*****************************/
    public static final String MODF_MARKCD_0 = "0";//未修改
    public static final String MODF_MARKCD_1 = "1";//修改
    public static final String MODF_MARKCD_2 = "2";//删除
    public static final String MODF_MARKCD_3 = "3";//增加

    /*********************料件成品标志代码*****************************/
    public static final String MTPCK_ENDPRD_TYPECD_I = "I";//料件/半成品
    public static final String MTPCK_ENDPRD_TYPECD_E = "E";//成品/残次品
    public static final String MTPCK_ENDPRD_TYPECD_R = "R";//边角料、副产品

    /*********************单据类别*****************************/
    public static final String BILLS_TYPE_1 = "1";//1—物流
    public static final String BILLS_TYPE_2 = "2";//2—加工
    public static final String BILLS_TYPE_3 = "3";//3-其它

    /*********************操作类型代码*****************************/
    public static final String OPERATE_TYPE_0 = "0";//正常
    public static final String OPERATE_TYPE_1 = "1";//退运

    /*********************核扣标记代码*****************************/
    public static final String VRFDED_MARKCD_0 = "0";//未核扣
    public static final String VRFDED_MARKCD_1 = "1";//预核扣
    public static final String VRFDED_MARKCD_2 = "2";//已核扣
    public static final String VRFDED_MARKCD_3 = "3";//已核销

    /*********************进出口标记代码*****************************/
    public static final String IMPEXP_MARKCD_I = "I";//进口
    public static final String IMPEXP_MARKCD_E = "E";//出口

}
