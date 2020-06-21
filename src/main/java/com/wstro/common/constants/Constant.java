package com.wstro.common.constants;
/**
 * 
 * @ClassName TransTypeEnum
 * @Description 动作状态
 * @author Simon.xie
 * @Date 2017年6月4日 下午4:21:46
 * @version 1.0.0
 */
public class Constant {
	//最后一个环节号
	public static final String Last="D9";
	//默认最后一个环节审批岗位为系统管理员，岗位编码为0
	public static  final String LastPosCode="0";

	//是否处理
	public static final String Y = "Y";
	public static final String N = "N";

	/*********************单据状态*****************************/
	public static final String CHK_STATUS_S = "S";//暂存
	public static final String CHK_STATUS_D = "D";//提交申报
	public static final String CHK_STATUS_1 = "1";//入库成功
	public static final String CHK_STATUS_2 = "2";//入库失败
	public static final String CHK_STATUS_3 = "3";//待审
	public static final String CHK_STATUS_P = "P";//审批通过
	public static final String CHK_STATUS_N = "N";//审批不通过

	/********************通道*****************************/
	public static final String CHANNEL_C0000 = "C0000";//审批端入库成功
	public static final String CHANNEL_C0010 = "C0010";//审批端入库不成功
}
