package com.wstro.entity.base;

import lombok.Data;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：PageInfo
 * 类描述：分页对应实体类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
@Data
public class PageInfo {

    private int totalNumber; // 总记录

    private int currentPage; //当前显示页

    private int pageNumber = 12; //默认显示的记录数

    private int totalPage; //总页数

    private int dbIndex; //数据库中limit的参数，从第几条开始取

    private int dbNumber; //数据库中的limit的参数，一共取多少条

    public void count() {
        //计算总页数
        int totalPageTemp = this.totalNumber / this.pageNumber;
        int plus = (this.totalNumber % this.pageNumber) == 0 ? 0 : 1;
        totalPageTemp = totalPageTemp + plus;
        if (totalPageTemp <= 0) {
            totalPageTemp = 1;
        }
        this.totalPage = totalPageTemp;
        //设置当前页数（总页数小于当前页数，应将当前页数设置为总页数）
        if (this.totalPage < this.currentPage) {
            this.totalPage = this.currentPage;
        }
        //当前页数小于1设置为1
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        //设置limit的参数
        this.dbIndex = (this.currentPage - 1) * this.pageNumber;
        this.dbNumber = this.pageNumber;
    }
}
