/**
 * Copyright (c) 2007-2008, Magnetosoft, LLC
 * All rights reserved.
 * 
 * Licensed under the Magnetosoft License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.magnetosoft.ru/LICENSE
 *
 */
package ru.magnetosoft.objects;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.magnetosoft.objects.documenttype.DocumentType;
import ru.magnetosoft.objects.documenttype.TypeAttribute;
import ru.magnetosoft.objects.documenttype.TypeAttributeType;

/**
 * Performs basic operations with XMLGregorianCalendar.
 *
 * @author SheringaA
 */
public class ObjectsHelper {

    private static DecimalFormat formatter = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat sdfWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    public static final String defaultRepresentation = "$defaultRepresentation";
    public static final String comment = "$comment";
    public static final String authorId = "$authorId";
    private static final String order = "$order";
    private static final String blank = "$blank";
    private static final String extended = "$extended";
    private static final String stringMark = "$isString";
    private static final String fieldSizeMark = "$fieldSize";
    private static final String compositeMark = "$isComposite";
    private static final String tableMark = "$isTable";
    private static final String compositionMark = "$composition";
    
    // Сюда мы будем складывать значения атрибутов, соответствующих композиции 
    // (для того чтобы не вытаскивать каждый раз объекты по ссылкам)
    private static final String compositionValuesMark = "$compositionValues";
    
    private static final String withTimeMark = "$withTime";
    private static final String defaultValueAsCurrentDateMark = "$defaultValueAsCurrentDate";
    private static final String computationalMark = "$isComputational";
    private static final String readonlyMark = "$isReadonly";
    private static final String dictionaryMark = "$dictionaryType";
    
    public static final String emptyFiled = "[empty]";
    public static final String userTag = "user";
    public static final String deptTag = "department";
    public static final String posnTag = "position";

    /**
     * Transforms XMLGregorianCalendar to date string.
     *
     * @param date XMLGregorianCalendar
     * @return String
     */
    public static String xmlgregoriancalendar2string(XMLGregorianCalendar date) {
        try {
            return sdf.format(date.toGregorianCalendar().getTime());
        }
        catch (Exception ex) {}
        return "";
    }

    /**
     * Transforms XMLGregorianCalendar to time string.
     *
     * @param date XMLGregorianCalendar
     * @return String
     */
    public static String xmlgregoriancalendar2timestring(XMLGregorianCalendar date) {
        try {
            return sdfTime.format(date.toGregorianCalendar().getTime());
        }
        catch (Exception ex) {}
        return "";
    }

    /**
     * Transforms String to Date.
     *
     * @param date
     * @param time 
     * @return XMLGregorianCalendar
     */
    public static Date string2date(String date) {
        GregorianCalendar gcal = new GregorianCalendar();
        try {
            gcal.setTime(sdf.parse(date));
            return gcal.getTime();
        } catch (Exception ex) {}
        return null;
    }
    
    /**
     * Transforms String to XMLGregorianCalendar.
     *
     * @param date
     * @param time 
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar string2xmlgregoriancalendar(String date, String time) {
        GregorianCalendar gcal = new GregorianCalendar();

        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
            if (time != null && !"".equals(time.trim())) {
                gcal.setTime(sdfWithTime.parse(date + " " + time));
            } else {
                gcal.setTime(sdf.parse(date));
            }

            return df.newXMLGregorianCalendar(gcal);
        } catch (Exception ex) {}
        return null;
    }

    /**
     * Transforms Date to XMLGregorianCalendar.
     *
     * @param date
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar date2xmlgregoriancalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar gcal = new GregorianCalendar();

        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
            gcal.setTime(date);
            XMLGregorianCalendar xmlCal = df.newXMLGregorianCalendar(gcal);
            return xmlCal;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static String normalizePeriod(String begin, String end) {
        if (begin == null) {
            begin = "";
        }
        if (end == null) {
            end = "";
        }
        if ("".equals(begin) && !"".equals(end)) {
            return "по " + end;
        }
        if (!"".equals(begin) && "".equals(end)) {
            return "с " + begin;
        }
        if ("".equals(begin) && "".equals(end)) {
            return emptyFiled;
        }
        if (!"".equals(begin) && !"".equals(end)) {
            return begin + " - " + end;
        }
        return emptyFiled;
    }

    public static String normalizePeriod(XMLGregorianCalendar begin, XMLGregorianCalendar end) {
        return normalizePeriod(xmlgregoriancalendar2timestring (begin), xmlgregoriancalendar2timestring (end));
    }

    public static String normalizeName(String name) {
        if (name == null || "".equals(name.trim())) {
            name = emptyFiled;
        }
        return name;
    }

    public static void unmarshallRepresentationInfo(DocumentType type) {
        if (type.getRepresentationInfo() == null) {
            return;
        }
        int oldReprIndex = type.getRepresentationInfo().indexOf(ObjectsHelper.extended);
        if (oldReprIndex < 0) {
            oldReprIndex = type.getRepresentationInfo().length();
        } else {
            type.getRepresentation().setExtended(type.getRepresentationInfo().substring(oldReprIndex + ObjectsHelper.extended.length()+1));
        }
        Map<String, String> map = unmarshallMap(type.getRepresentationInfo().substring(0, oldReprIndex), ";", "=");
        for (Entry<String, String> entry: map.entrySet()) {
            if (ObjectsHelper.defaultRepresentation.equals(entry.getKey())) {
                type.getRepresentation().setDefaultRepresentation(entry.getValue());
                continue;
            }
            if (ObjectsHelper.order.equals(entry.getKey())) {
                type.getRepresentation().setOrder(entry.getValue());
                continue;
            }
            if (ObjectsHelper.blank.equals(entry.getKey())) {
                type.getRepresentation().setBlank(entry.getValue());
                continue;
            }
        }
    }

    public static String marshallRepresentationInfo(DocumentType type) {
        StringBuilder out = new StringBuilder();

        try {
            out.append(ObjectsHelper.defaultRepresentation);
            out.append("=");
            if (type.getRepresentation().getDefaultRepresentation() != null) {
                out.append(type.getRepresentation().getDefaultRepresentation());
            }
            if (type.getRepresentation().getOrder() != null) {
                out.append(";");
                out.append(ObjectsHelper.order);
                out.append("=");
                out.append(type.getRepresentation().getOrder());
            }
            if (type.getRepresentation().getBlank() != null && !type.getRepresentation().getBlank().trim().equals("")) {
                out.append(";");
                out.append(ObjectsHelper.blank);
                out.append("=");
                out.append(type.getRepresentation().getBlank());
            }
            if (type.getRepresentation().getExtended() != null && !type.getRepresentation().getExtended().trim().equals("")) {
                out.append(";");
                out.append(ObjectsHelper.extended);
                out.append("=");
                out.append(type.getRepresentation().getExtended());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return out.toString();
    }

    public static void unmarshallDescriptionInfo(TypeAttribute attr) {
        Map<String, String> map = unmarshallMap(attr.getDescription(), ";", "=");
        for (Entry<String, String> entry: map.entrySet()) {
            if (ObjectsHelper.stringMark.equals(entry.getKey())) {
                attr.setType(TypeAttributeType.STRING);
                continue;
            }
            if (ObjectsHelper.fieldSizeMark.equals(entry.getKey())) {
                attr.setFieldSize(entry.getValue());
                continue;
            }
            if (ObjectsHelper.compositeMark.equals(entry.getKey())) {
                attr.setType(TypeAttributeType.COMPOSITE);
                continue;
            }
            if (ObjectsHelper.computationalMark.equals(entry.getKey())) {
                attr.setType(TypeAttributeType.COMPUTATIONAL);
                continue;
            }
            if (ObjectsHelper.readonlyMark.equals(entry.getKey())) {
                attr.setReadonly(true);
                continue;
            }
            if (ObjectsHelper.compositionMark.equals(entry.getKey())) {
                attr.setComposition(entry.getValue());
                continue;
            }
            if (ObjectsHelper.compositionValuesMark.equals(entry.getKey())) {
            	Map<String,String> result = new HashMap<String, String>();
            	if (entry.getValue()!=null) {
            		String[] pairs = entry.getValue().split("[|]");
            		for (String pair:pairs) {
            			String[] split = pair.split("\\s*(\\b--\\b)\\s*");
            			if (split.length==2) {
            				result.put(split[0], split[1]);
            			}
            		}
            	}
                attr.setCompositionValues(result);
                
                continue;
            }
            if (ObjectsHelper.tableMark.equals(entry.getKey())) {
                if (attr.getType() == TypeAttributeType.LINK) {
                    attr.setAsTableTypeId(entry.getValue());
                } else {
                    // System.out.println(attr.getType() + " can't have " + entry.getKey() + " mark");
                }
                continue;
            }
            if (ObjectsHelper.withTimeMark.equals(entry.getKey())) {
                attr.setWithTime(true);
                continue;
            }
            if (ObjectsHelper.defaultValueAsCurrentDateMark.equals(entry.getKey())) {
                attr.setDefaultValueAsCurrentDate(true);
                continue;
            }
            if (ObjectsHelper.dictionaryMark.equals(entry.getKey())) {
            	attr.setDictionaryType(entry.getValue());
                continue;            	
            }
        }
    }

    public static String marshallDescriptionInfo(TypeAttribute attr) {
        StringBuilder out = new StringBuilder();

        try {
            if (attr.getType() == TypeAttributeType.STRING) {
                out.append(ObjectsHelper.stringMark);
                out.append(";");
            }
            if (attr.getFieldSize() != null && !"".equals(attr.getFieldSize())) {
                out.append(ObjectsHelper.fieldSizeMark);
                out.append("=");
                out.append(attr.getFieldSize());
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.COMPOSITE) {
                out.append(ObjectsHelper.compositeMark);
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.COMPUTATIONAL) {
                out.append(ObjectsHelper.computationalMark);
                out.append(";");
            }
            if (attr.isReadonly()) {
                out.append(ObjectsHelper.readonlyMark);
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.DICTIONARY) {
                out.append(ObjectsHelper.dictionaryMark);
                out.append("=");
                out.append((attr.getDictionaryType()!=null)?attr.getDictionaryType():'1');
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.LINK) {
                out.append(ObjectsHelper.dictionaryMark);
                out.append("=");
                out.append((attr.getDictionaryType()!=null)?attr.getDictionaryType():'0');
                out.append(";");
            }
            if (attr.getComposition() != null && !"".equals(attr.getComposition())) {
                out.append(ObjectsHelper.compositionMark);
                out.append("=");
                out.append(attr.getComposition());
                out.append(";");
            }
            if (attr.getCompositionValues() != null && attr.getCompositionValues().size()>0) {
                out.append(ObjectsHelper.compositionValuesMark);
                out.append("=");
                Iterator<Entry<String, String>> iterator = attr.getCompositionValues().entrySet().iterator();
                while (iterator.hasNext()) {
                	Entry<String, String> entry = iterator.next();
                	out.append(entry.getKey()).append("--").append(entry.getValue());
                	if (iterator.hasNext()) { out.append("|"); }
                }
                out.append(";");
            }
            if (attr.getAsTableTypeId() != null && !"".equals(attr.getAsTableTypeId())) {
                out.append(ObjectsHelper.tableMark);
                out.append("=");
                out.append(attr.getAsTableTypeId());
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.DATE && attr.isWithTime()) {
                out.append(ObjectsHelper.withTimeMark);
                out.append(";");
            }
            if (attr.getType() == TypeAttributeType.DATE && attr.isDefaultValueAsCurrentDate()) {
                out.append(ObjectsHelper.defaultValueAsCurrentDateMark);
                out.append(";");
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return out.toString();
    }

    public static String getString4HTML(String input) {
        if (input == null) {
            return "";
        }
        char[] inp = input.toCharArray();
        int l = inp.length;
        StringBuilder out = new StringBuilder(l);
        for (int i = 0; i < l; i++) {
            char c = inp[i];
            if (c != 10) {
                out.append(c);
            } else {
                out.append("~");
            }
        }
        return out.toString();
    }
    
    public static Map<String, String> unmarshallMap(String target, String firstSpliter, String secondSpliter) {
        Map<String, String> ret = new HashMap<String, String>();
        try {
            if (target == null) {
                return ret;
            }
            String[] paramsAndValues = target.split(firstSpliter);

            for (String paramAndValue : paramsAndValues) {
                String[] param_value = paramAndValue.split(secondSpliter);
                String param, value;

                if (param_value.length == 1) {
                    value = "";
                } else {
                    value = param_value[1].trim();
                }
                param = param_value[0].trim();
                ret.put(param, value);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ret;
    }
    
    public static String getNowDateAsString() {
        return sdf.format(new Date());
    }

    public static String getNowTimeAsString() {
        return sdfTime.format(new Date());
    }

    public static String formatBigDecimal (String decimal) {
        BigDecimal bd;
        try {
            if (decimal != null && !"".equals(decimal.trim())) {
                bd = new BigDecimal(decimal);
                return getFormatter().format(bd);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static BigDecimal parseBigDecimal (String decimal) {
        try {
            if (decimal != null && !"".equals(decimal.trim())) {
                return new BigDecimal (getFormatter().parse(decimal).doubleValue());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DecimalFormat getFormatter() {
        if (formatter == null) {
            formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(18);
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            dfs.setGroupingSeparator(' ');
            formatter.setDecimalFormatSymbols(dfs);
        }
        return formatter;
    }
}