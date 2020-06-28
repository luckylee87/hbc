package com.dsg.common.util;

import com.dsg.common.util.toolbox.StringUtil;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLUtils {


    /**
     * @return
     * @Description 获取xsd路径
     */
    public static File getXsdUrl() {
        File cfgFile = null;
        try {
            cfgFile = ResourceUtils.getFile("classpath:XSD");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cfgFile;
    }

    /**
     * 设置节点文本值
     *
     * @param element 节点
     * @param object  文本值
     */
    public static void setText(Element element, Object object) {
        if (null != object) {
            element.setText(object + "");
        }
    }

    /**
     * 根据xml文件路径取得document对象
     *
     * @return
     * @throws DocumentException
     */
    public static Document getDocument(File file) {

        if (file == null || !file.exists()) {
            return null;
        }

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 将xml类型的字符串转换成document对象进行操作
     *
     * @param xml
     * @return
     */
    public static Document parseStringToXml(String xml) {
        try {
            return DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取xml文件
     *
     * @param filepath
     * @return
     */
    public static Document parseFileToXml(String filepath) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(new File(filepath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 获取webapp 相对路径下的xml文件
     *
     * @param filename
     * @return
     */
    public static Document parseFIOToXml(String filename) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            InputStream in = XMLUtils.class.getResourceAsStream("xml/" + filename);
            doc = saxReader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 根据节点名获取指定的元素集合
     *
     * @param xpath
     * @param doc
     * @return
     */
    public static List<Node> getElementListByName(String xpath, Document doc) {
        if (!StringUtil.startWith(xpath, "//", true)) {
            xpath = "//" + xpath;
        }
        List<Node> parameterList = doc.selectNodes(xpath);
        return parameterList;
    }

    /**
     * 根据节点名获取指定的元素
     *
     * @param xpath
     * @param doc
     * @return
     */
    public static Node getElementByName(String xpath, Document doc) {
        List<Node> parameterList = getElementListByName(xpath, doc);
        if (parameterList != null && parameterList.size() > 0) {
            return parameterList.get(0);
        }
        return null;
    }

    /**
     * 根据xpath 获取指定的元素集合
     *
     * @param xpath
     * @param doc
     * @return
     */
    public static List<Node> getElementListByXpath(String xpath, Document doc) {
        if (xpath.startsWith("//")) {
            xpath = xpath.substring(2);
        }
        Map ns = new HashMap();
        ns.put("xd", "http://www.w3.org/2000/09/xmldsig#");
        XPath x = doc.createXPath("//xd:" + xpath);
        x.setNamespaceURIs(ns);
        return x.selectNodes(doc);
    }

    /**
     * 根据xpath 获取指定的元素
     *
     * @param xpath
     * @param doc
     * @return
     */
    public static Node getElementByXpath(String xpath, Document doc) {
        List<Node> parameterList = getElementListByXpath(xpath, doc);
        if (parameterList != null && parameterList.size() > 0) {
            return parameterList.get(0);
        }
        return null;
    }

    /**
     * 获取指定id的xml元素(单个xml中id唯一的情况下使用)
     *
     * @param id
     * @param doc
     * @return
     */
    public static Node getElementById(String id, Document doc) {
        return getElementByXpath("//*[@id='" + id + "']", doc);
    }

    /**
     * 复制节点数据到Bean
     */
    public static Object copyData(Object beanObj, Element xmlElement) throws Exception {

        if (xmlElement != null) {
            List<Element> elementList = xmlElement.elements();
            for (Element e : elementList) {
                System.out.println(e.getName() + "......." + e.getData());
                BeanUtil.setProperty(beanObj, e.getName(), e.getData());
            }

        }
        return beanObj;
    }

}