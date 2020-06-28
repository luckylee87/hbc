package com.dsg.common.util;import java.util.ArrayList;import java.util.Enumeration;/** * 通用枚举 */public class CommonEnum implements Enumeration {    private final ArrayList oArray_ = new ArrayList();    private int nCurrentCursor_ = 0;    public CommonEnum() {    }    public void add(Object obj) {        if (obj != null) oArray_.add(obj);    }    public boolean hasMoreElements() {        return this.nCurrentCursor_ < oArray_.size();    }    public Object nextElement() {        return oArray_.get(nCurrentCursor_++);    }    public int size() {        return oArray_.size();    }}