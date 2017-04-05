package com.owl;

import java.util.ArrayList;
import java.util.List;

public class SqwrlGenerator {

    public List<String> generateMessageQuery(int minData, int maxData) {
        List<String> data = new ArrayList<>();
        int avg = (minData+maxData)/2;
        data.add(generateMessageQueryFullMatches(minData, maxData));
        data.add(generateMessageQueryMinMatches(avg));
        data.add(generateMessageQueryWithoutPackage(avg));
        data.add(generateMessageQueryWithServiceAndFullMatches(minData, maxData));
        data.add(generateMessageQueryWithServiceMaxMatches(maxData));
        data.add(generateMessageQueryWithServiceMinMatches(avg));
        return data;
    }

    public List<String> generateCallQuery(int minData, int maxData) {
        List<String> data = new ArrayList<>();
         int avg = (minData+maxData)/2;
        data.add(generateCallQueryFullMatches(minData, maxData));
        data.add(generateCallQueryMinMatches(avg));
        data.add(generateCallQueryWithoutPackage(avg));
        data.add(generateCallQueryWithServiceAndFullMathces(minData, maxData));
        data.add(generateCallQueryWithServiceMaxMatches(maxData));
        data.add(generateCallQueryWithServiceMinMatches(avg));
        return data;
    }
    public List<String> generateInternetQuery(int minData, int maxData){
        List<String> data = new ArrayList<>();
        data.add(generateInternetQueryFullMatches(minData, maxData));
        data.add(generateInternetQueryWithoutPackage((maxData+maxData)/2));
        return data;
    }
    

    private String generateMessageQueryFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x) ^ "
                + "sqwrl:columnNames(\"Тариф\")";
    }

    private String generateMessageQueryMinMatches(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }

    private String generateMessageQueryWithoutPackage(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult," + minData + ",?ans)^"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }

    private String generateMessageQueryWithServiceAndFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:lessThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x,?q)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
    }

    private String generateMessageQueryWithServiceMaxMatches(int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x,?q)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
    }

    private String generateMessageQueryWithServiceMinMatches(int minData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)^swrlb:multiply(?mult,?d,?ans)"
                + "->sqwrl:select(?x,?q,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";

    }

    private String generateCallQueryFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_звонки_в_Новосибирскую_область(?x,?y) ^ diplom:иметь_время_звонков_в_минутах(?y,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x)^ "
                + "sqwrl:columnNames(\"Тариф\")";
    }

    private String generateCallQueryMinMatches(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_звонки_в_Новосибирскую_область(?x,?y) ^ diplom:иметь_время_звонков_в_минутах(?y,?t)^"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }

    private String generateCallQueryWithoutPackage(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult," + minData + ",?ans)"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }

    private String generateCallQueryWithServiceAndFullMathces(int minData, int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:lessThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x,?q)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
    }

    private String generateCallQueryWithServiceMaxMatches(int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x,?q)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
    }

    private String generateCallQueryWithServiceMinMatches(int minData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)^swrlb:multiply(?mult,?d,?ans)"
                + "->sqwrl:select(?x,?q,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }
    
     private String generateInternetQueryFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_интернет(?x,?y) ^ diplom:иметь_количество_интернета(?y,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
                + "->sqwrl:select(?x) ^ "
                + "sqwrl:columnNames(\"Тариф\")";
    }

//    private String generateInternetQueryMinMatches(int minData) {
//        return "diplom:Тариф(?x)^"
//                + "diplom:иметь_интернет(?x,?y) ^ diplom:иметь_количество_интернета(?y,?t)"
//                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
//                + "swrlb:subtract(?d," + minData + ",?t)^"
//                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
//                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
//                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
//                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
//                + "^swrlb:multiply(?mult,?d,?ans)"
//                + "->sqwrl:select(?x,?mult)^"
//                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
//    }
//
    private String generateInternetQueryWithoutPackage(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_цену_интернета_за_1_Мб(?x,?c)^"
                + "swrlb:multiply(?mult," + minData + ",1024)^"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }
//
//    private String generateMessageQueryWithServiceAndFullMatches(int minData, int maxData) {
//        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
//                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
//                + "swrlb:lessThanOrEqual(?t," + maxData + ")"
//                + "->sqwrl:select(?x,?q)^"
//                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
//    }
//
//    private String generateMessageQueryWithServiceMaxMatches(int maxData) {
//        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
//                + "swrlb:greaterThanOrEqual(?t," + maxData + ")"
//                + "->sqwrl:select(?x,?q)^"
//                + "sqwrl:columnNames(\"Тариф\",\"Услуга\")";
//    }
//
//    private String generateMessageQueryWithServiceMinMatches(int minData) {
//        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
//                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
//                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
//                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
//                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
//                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)^"
//                + "swrlb:subtract(?d," + minData + ",?t)^"
//                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)^swrlb:multiply(?mult,?d,?ans)"
//                + "->sqwrl:select(?x,?q,?mult)^"
//                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
//
//    }

}
