package com.dsg.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Objects;

/**
 * Created by wuxia on 2016/10/12 0012.
 */
public class DomWrite {
    /**
     * 发现了？我们的生成的xml文件比较丑 没有格式化 对吧？？
     */

    /**
     * 通过 XMLWriter 输出 Document
     *
     * @param filePath
     * @param document
     */
    public static void writeDocument(String filePath, Document document) {
        /**
         * 利用 OutputFormat得到美化的格式
         */
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setLineSeparator(System.getProperty("line.separator"));
        try {
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"), format);
            write(writer, document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 XMLWriter 输出XML ducument
     *
     * @param outputStream 给一个输出流就能往 输出流里输出XML文件内容啦
     * @param document
     * @param format       可以指定任意的格式
     * @throws IOException
     */
    public static void writeDocument(OutputStream outputStream, Document document, OutputFormat format) {
        try {
            XMLWriter writer = new XMLWriter(outputStream, format);
            write(writer, document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void write(XMLWriter writer, Document document) {
        try {
            if (Objects.nonNull(writer)) {
                writer.write(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    // 一定要记得flush 和 close
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 格式化XML字符串
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String formatXml(String str) throws Exception {
        Document document = null;
        document = DocumentHelper.parseText(str);
        // 格式化输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("gb2312");
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();
        return writer.toString();
    }
}
