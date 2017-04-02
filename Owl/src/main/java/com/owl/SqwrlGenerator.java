package com.owl;

import java.util.ArrayList;
import java.util.List;

public class SqwrlGenerator {

    public List<String> generateMessageQuery(int minData, int maxData) {
        List<String> data = new ArrayList<>();
        data.add(generateMessageQueryFullMatches(minData, maxData));
        data.add(generateMessageQueryMinMatches(minData));
        data.add(generateMessageQueryWithDaysPay(minData));
        data.add(generateMessageQueryWithoutPackage(minData));
        data.add(generateMessageQueryWithServiceAndFullMatches(minData, maxData));
        data.add(generateMessageQueryWithServiceMaxMatches(maxData));
        data.add(generateMessageQueryWithServiceMinMatches(minData));
        return data;
    }

    public List<String> generateCallQuery(int minData, int maxData) {
        List<String> data = new ArrayList<>();
        data.add(generateCallQueryFullMatches(minData, maxData));
        data.add(generateCallQueryMinMatches(minData));
        data.add(generateCallQueryWithDaysPay(minData));
        data.add(generateCallQueryWithoutPackage(minData));
        data.add(generateCallQueryWithServiceAndFullMathces(minData, maxData));
        data.add(generateCallQueryWithServiceMaxMatches(maxData));
        data.add(generateCallQueryWithServiceMinMatches(minData));
        return data;
    }

    private String generateMessageQueryFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:lessThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_абонентскую_плату_в_месяц_в_рублях(?x,?s)->sqwrl:select(?x,?s) ^ "
                + "sqwrl:columnNames(\"Тариф\",\"Абонентская плата\")";
    }

    private String generateMessageQueryMinMatches(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "diplom:иметь_абонентскую_плату_в_месяц_в_рублях(?x,?s)^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)"
                + "->sqwrl:select(?x,?mult,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\",\"Абонентская плата\")";
    }

    private String generateMessageQueryWithDaysPay(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "diplom:иметь_абонентскую_плату_в_день_в_рублях(?x,?s)^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)^"
                + "swrlb:multiply(?m,?s,30)"
                + "->sqwrl:select(?x,?mult,?m)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\",\"Абонентская плата\")";
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
                + "swrlb:lessThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)"
                + "->sqwrl:select(?x,?q,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }

    private String generateMessageQueryWithServiceMaxMatches(int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)"
                + "->sqwrl:select(?x,?q,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }

    private String generateMessageQueryWithServiceMinMatches(int minData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_количество_сообщений(?q,?t)^"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "diplom:иметь_цену_за_сообщения_абонентам_другого_оператора_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_сообщения_абонентам_МТС_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)^swrlb:multiply(?mult,?d,?ans)^"
                + "swrlb:add(?sum,?s,?mult)"
                + "->sqwrl:select(?x,?q,?sum)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";

    }

    private String generateCallQueryFullMatches(int minData, int maxData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_звонки_в_Новосибирскую_область(?x,?y) ^ diplom:иметь_время_звонков_в_минутах(?y,?t)"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:lessThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_абонентскую_плату_в_месяц_в_рублях(?x,?s)->sqwrl:select(?x,?s)^ "
                + "sqwrl:columnNames(\"Тариф\",\"Абонентская плата\")";
    }

    private String generateCallQueryMinMatches(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_звонки_в_Новосибирскую_область(?x,?y) ^ diplom:иметь_время_звонков_в_минутах(?y,?t)"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "diplom:иметь_абонентскую_плату_в_месяц_в_рублях(?x,?s)^ "
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)^"
                + "->sqwrl:select(?x,?mult,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\",\"Абонентская плата\")";
    }

    private String generateCallQueryWithDaysPay(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_звонки_в_Новосибирскую_область(?x,?y) ^ diplom:иметь_время_звонков_в_минутах(?y,?t)"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "diplom:иметь_абонентскую_плату_в_день_в_рублях(?x,?s)^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult,?d,?ans)^"
                + "swrlb:multiply(?m,?s,30)^"
                + "->sqwrl:select(?x,?mult,?m)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\",\"Абонентская плата\")";
    }

    private String generateCallQueryWithoutPackage(int minData) {
        return "diplom:Тариф(?x)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)"
                + "^swrlb:multiply(?mult," + minData + ",?ans)^"
                + "->sqwrl:select(?x,?mult)^"
                + "sqwrl:columnNames(\"Тариф\",\"Цена\")";
    }

    private String generateCallQueryWithServiceAndFullMathces(int minData, int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + minData + ")^"
                + "swrlb:lessThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)"
                + "->sqwrl:select(?x,?q,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }

    private String generateCallQueryWithServiceMaxMatches(int maxData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:greaterThanOrEqual(?t," + maxData + ")^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)"
                + "->sqwrl:select(?x,?q,?s)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }

    private String generateCallQueryWithServiceMinMatches(int minData) {
        return "diplom:Тариф(?x)^diplom:Услуга(?q)^diplom:иметь_услугу(?x,?q)^diplom:иметь_время_звонков_в_минутах(?q,?t)^"
                + "swrlb:lessThanOrEqual(?t," + minData + ")^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_МТС_Новосибирской_области(?x,?c)^"
                + "diplom:иметь_цену_за_минуту_звонка_абонентам_другого_оператора_Новосибирской_области(?x,?c1) ^"
                + "swrlb:add(?k, ?c, ?c1) ^ swrlb:divide(?mod, ?k, 2) ^ swrlb:subtract(?subs, ?c, ?mod) "
                + "^ swrlb:abs(?abs, ?subs) ^ swrlb:add(?ans, ?mod, ?abs)^"
                + "swrlb:subtract(?d," + minData + ",?t)^"
                + "diplom:иметь_стоимость_пользования_услугой(?q,?s)^swrlb:multiply(?mult,?d,?ans)^"
                + "swrlb:add(?sum,?s,?mult)"
                + "->sqwrl:select(?x,?q,?sum)^"
                + "sqwrl:columnNames(\"Тариф\",\"Услуга\",\"Цена\")";
    }
}
