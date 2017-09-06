package com.tocheck.parent.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class FileReader {

    private static final Log LOG = LogFactory.getLog(FileReader.class);

    public static String readContent(File file) {
        String content = "";
        String fileName = file.getName();
        int pointLastIndexOf = fileName.lastIndexOf(".");
        String fileSuffix = pointLastIndexOf == -1 ? "" : fileName.substring(pointLastIndexOf).toLowerCase();
        if (".doc".equals(fileSuffix)) {
            content = readDoc(file);
            if (StringUtils.isAnyBlank(content)) {
                content = readDocx(file);
            }
            //option = BaseOptionUtils.addFromWord(option)
        } else if (".docx".equals(fileSuffix)) {
            content = readDocx(file);
            //option = BaseOptionUtils.addFromWord(option)
        } else if (".txt".equals(fileSuffix)) {
            content = readTxt(file);
        } else {
            LOG.error(fileName + " has unknow file type " + fileSuffix);
        }
        return content;
    }

    private static String readTxt(File file) {
        BufferedReader reader = null;
        String content = null;
        StringBuilder strBuff = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), getEncode(file)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuff.append(line);
                strBuff.append("\n");
            }
            content = strBuff.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String readDoc(File file) {
        String content = null;
        try {
            FileInputStream in = new FileInputStream(file);
            WordExtractor extractor = new WordExtractor(in);
            content = extractor.getText();
            extractor.close();
            in.close();
        } catch (Exception e) {
            LOG.error(e);
        }
        return content;
    }

    private static String readDocx(File file) {
        String content = null;
        try {
            OPCPackage oPCPackage = POIXMLDocument.openPackage(file.getAbsolutePath());
            XWPFDocument xwpf = new XWPFDocument(oPCPackage);
            POIXMLTextExtractor ex = new XWPFWordExtractor(xwpf);
            content = ex.getText();
            ex.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String readPdf(File file) {
        PDDocument document = null;
        String content = null;
        try {
            document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    document = null;
                }
            }
        }
        return content;
    }

    private static String getEncode(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        InputStream in = null;
        try {
            boolean checked = false;
            in = new FileInputStream(file);
            bis = new BufferedInputStream(in);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charset;
    }

//    public static void main(String[] args) {
        //File file = new File("f:/bb.docx");
//        File file = new File("D:\\upload\\aa.doc");
//        System.out.println(readContent(file));
//		String content = " HYPERLINK \\l \"_Toc418427934\" 个人简介	28";
//		System.out.println(content.replaceAll("[ \\t\\r\\n]HYPERLINK \\\\l \"_Toc\\d+\"", ""));
//		String aa = "TOC \\o \"1-2\" \\h \\z \\u  HYPERLINK \\l \"_Toc418427916\" 引言	1";
//		System.out.println(aa.replaceAll("TOC \\\\o \"1-2\" \\\\h \\\\z \\\\u", "0000"));
//    }

    /**
     * 读取电子表格,只能是xls,或xlsx,返回的 List<List<Map<String , String>>>
     *     其中每页电子表格一个List<Map<String , String>>
     *         每一行Map<String , String>,若有标题haveTitle为true,则 map<标题,值>,若没有标题,则map<列号-1,值>
     * @param filepath
     * @param haveTitle
     * @return
     * @throws Exception
     */
    public static List<List<Map<String , String>>> readExcel(String filepath, boolean haveTitle) throws Exception{
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(filepath);

            switch (fileType) {
                case "xls":
                    wb = new HSSFWorkbook(is);
                    break;
                case "xlsx":
                    wb = new XSSFWorkbook(is);
                    break;
                default:
                    throw new Exception("读取的不是excel文件");
            }

            List<List<Map<String, String>>> result = new ArrayList<>();//对应excel文件

            int sheetSize = wb.getNumberOfSheets();
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页
                Sheet sheet = wb.getSheetAt(i);
                List<Map<String , String>> sheetList = new ArrayList<>();//对应sheet页

                List<String> titles = new ArrayList<>();//放置所有的标题

                int rowSize = sheet.getLastRowNum() + 1;
                for (int j = 0; j < rowSize; j++) {//遍历行
                    Row row = sheet.getRow(j);
                    if (row == null) {//略过空行
                        continue;
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                    if (j == 0&& haveTitle) {//第一行是标题行
                        for (int k = 0; k < cellSize; k++) {
                            Cell cell = row.getCell(k);
                            titles.add(cell.toString());
                        }
                    } else {//其他行是数据行
                        Map<String, String> rowMap = new HashMap<>();//对应一个数据行
                        for (int k = 0; k < cellSize; k++) {
                            Cell cell = row.getCell(k);
                            String key = null;
                            if(!haveTitle)key=""+k;
                            else key=titles.get(k);
                            String value = null;
                            if (cell != null) {
                                value = cell.toString().trim();
                            }
                            if (value=="")value=null;
                            rowMap.put(key, value);
                        }
                        sheetList.add(rowMap);
                    }
                }
                result.add(sheetList);
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
