package com.xunlei.framework.common.util;

import com.xunlei.framework.common.enums.DatePattern;
import com.xunlei.framework.common.enums.TimeStage;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    private static final int ONE_MINUTE = 60;
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
    private static final int ONE_DAY = 24 * ONE_HOUR;

    /**
     * 按默认格式将字符串转化成Date对象
     *
     * @param strDate 日期字符串，格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse(String strDate) {
        return DateUtils.parse(strDate, DatePattern.YMD_HMS);
    }

    /**
     * 按指定格式将字符串转化成Date对象
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式枚举
     * @return
     */
    public static Date parse(String strDate, DatePattern pattern) {
        return pattern.getFormat().parseLocalDateTime(strDate).toDate();
    }

    /**
     * 将日期转成字符串
     * 默认转成格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return
     */
    public static String toString(Date date) {
        return new DateTime(date).toString(DatePattern.YMD_HMS.getFormat());
    }

    /**
     * 将日期转成字符串
     *
     * @param date    日期
     * @param pattern 日期格式枚举
     */
    public static String toString(Date date, DatePattern pattern) {
        return new DateTime(date).toString(pattern.getFormat());
    }

    /**
     * 按指定格式将日期格式化Date 对象
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式枚举
     * @return
     */
    public static Date parse(Date strDate, DatePattern pattern) {
        return DateUtils.parse(DateUtils.toString(strDate, pattern), pattern);
    }

    /**
     * 简单的是否工作日判断，不是周末，则是工作日
     *
     * @param date
     * @return
     */
    public static boolean isWorkDay(Date date) {
        switch (getDayOfWeek(date)) {
            case Calendar.SUNDAY:
            case Calendar.SATURDAY:
                return false;
            default:
                return true;
        }

    }

    /**
     * 将日期转换字符串带周一
     */
    public static String getDayOfWeekStr(Date date) {
        String dateStr = toString(date, DatePattern.MD);
        switch (getDayOfWeek(date)) {
            case Calendar.SUNDAY:
                dateStr += "周日";
                break;
            case Calendar.MONDAY:
                dateStr += "周一";
                break;
            case Calendar.TUESDAY:
                dateStr += "周二";
                break;
            case Calendar.WEDNESDAY:
                dateStr += "周三";
                break;
            case Calendar.THURSDAY:
                dateStr += "周四";
                break;
            case Calendar.FRIDAY:
                dateStr += "周五";
                break;
            case Calendar.SATURDAY:
                dateStr += "周六";
                break;
        }

        return dateStr;
    }

    /**
     * 获取指定日期周一开始时间
     */
    public static Date getNowWeekStartTime(Date date) {
        return plusDays(getNextWeekStartTime(date), -7);
    }

    /**
     * 获取下周一开始时间
     */
    public static Date getNextWeekStartTime(Date date) {
        Integer plusDays = 0;
        switch (getDayOfWeek(date)) {
            case Calendar.SUNDAY:
                plusDays = 1;
                break;
            case Calendar.MONDAY:
                plusDays = 7;
                break;
            case Calendar.TUESDAY:
                plusDays = 6;
                break;
            case Calendar.WEDNESDAY:
                plusDays = 5;
                break;
            case Calendar.THURSDAY:
                plusDays = 4;
                break;
            case Calendar.FRIDAY:
                plusDays = 3;
                break;
            case Calendar.SATURDAY:
                plusDays = 2;
                break;
        }
        return plusDays(getStartOfDay(date), plusDays);
    }

    /**
     * 将日期转换字符串带周一
     */
    public static String dateToWeek(Date date) {
        switch (getDayOfWeek(date)) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
        }
        return "";
    }

    /**
     * 获取指定日期的周天
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期的年数
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期的月数
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定日期的天数
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期的小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的分钟
     */
    public static int getMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取指定日期的分钟
     */
    public static int getSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当前时间的小时
     */
    public static int getCurrHour() {
        return getHour(new Date());
    }

    /**
     * 获取当前时间的分钟
     */
    public static int getCurrMinutes() {
        return getMinutes(new Date());
    }

    /**
     * 获取当前时间的秒
     */
    public static int getCurrSeconds() {
        return getSeconds(new Date());
    }

    /**
     * 获取给定时间与当前时间的毫秒差值，当前时间减去给定的时间
     *
     * @param date 指定日期
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffMills(Date date) {
        return new Duration(date.getTime(), System.currentTimeMillis()).getMillis();
    }

    /**
     * 获取两个日期相差毫秒数
     *
     * @param earlyDate
     * @param latelyDate
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffMills(Date earlyDate, Date latelyDate) {
        return new Duration(earlyDate.getTime(), latelyDate.getTime()).getMillis();
    }

    /**
     * 获取给定时间与当前时间的差值，当前时间减去给定的时间
     *
     * @param strDate 匹配 yyyy-MM-dd HH:mm:ss格式的日期字符串
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffSeconds(String strDate) {
        return DateUtils.getDiffSeconds(DateUtils.parse(strDate));
    }

    /**
     * 获取给定时间与当前时间的差值，当前时间减去给定的时间
     *
     * @param date 指定日期
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffSeconds(Date date) {
        return new Duration(date.getTime(), System.currentTimeMillis()).getStandardSeconds();
    }

    /**
     * 获取两个日期相差秒数
     *
     * @param earlyDate
     * @param latelyDate
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffSeconds(Date earlyDate, Date latelyDate) {
        return DateUtils.getDiffSeconds(earlyDate, latelyDate, false);
    }

    /**
     * 获取两个日期相差秒数
     *
     * @param earlyDate
     * @param latelyDate
     * @param roundUp    是否需要四舍五入，默认false
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffSeconds(Date earlyDate, Date latelyDate, boolean roundUp) {
        if (roundUp) {
            long mills = DateUtils.getDiffMills(earlyDate, latelyDate);
            return (mills + 500) / 1000;
        }
        return new Duration(earlyDate.getTime(), latelyDate.getTime()).getStandardSeconds();
    }

    /**
     * 获取两个日期相差秒数
     *
     * @param earlyDateStr
     * @param latelyDateStr
     * @return
     */
    public static Long getDiffSeconds(String earlyDateStr, String latelyDateStr) {
        return DateUtils.getDiffSeconds(DateUtils.parse(earlyDateStr), DateUtils.parse(latelyDateStr));
    }

    /**
     * 获取两个日期毫秒数相差秒数
     *
     * @param earlyTime
     * @param latelyTime
     * @return
     */
    public static Long getDiffSeconds(Long earlyTime, Long latelyTime) {
        return new Duration(earlyTime, latelyTime).getStandardSeconds();
    }

    /**
     * 获取两个日期相差分钟，当前时间减去给定的时间
     *
     * @param date 指定日期
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffMinutes(Date date) {
        return new Duration(date.getTime(), System.currentTimeMillis()).getStandardMinutes();
    }

    /**
     * 获取两个日期相差分钟数
     *
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffMinutes(Date earlyDate, Date latelyDate) {
        return new Duration(earlyDate.getTime(), latelyDate.getTime()).getStandardMinutes();
    }

    /**
     * 获取两个日期相差小时，当前时间减去给定的时间
     *
     * @param date 指定日期
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffHours(Date date) {
        return new Duration(date.getTime(), System.currentTimeMillis()).getStandardHours();
    }

    /**
     * 获取两个日期相差小时数
     *
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffHours(Date earlyDate, Date latelyDate) {
        return new Duration(earlyDate.getTime(), latelyDate.getTime()).getStandardHours();
    }

    /**
     * 获取两个日期相差天数，当前时间减去给定的时间
     *
     * @param date 指定日期
     * @return 若小于0，说明当前时间小于指定时间
     */
    public static Long getDiffDays(Date date) {
        return new Duration(date.getTime(), System.currentTimeMillis()).getStandardDays();
    }

    /**
     * 获取两个日期相差天数
     *
     * @return 若小于0，说明latelyDate小于earlyDate
     */
    public static Long getDiffDays(Date earlyDate, Date latelyDate) {
        return new Duration(earlyDate.getTime(), latelyDate.getTime()).getStandardDays();
    }

    /**
     * 获取当天的当前秒数
     *
     * @param date
     * @return
     */
    public static Integer getSecondOfDay(Date date) {
        return new DateTime(date).getSecondOfDay();
    }

    /**
     * 上次直播时间，显示文案：上次直播：[时间]前。T=当前时间-下播时间，时间显示规则如下：
     * 3.1 若0秒<T<59秒，则T=1分钟。
     * 3.2 若1分钟<T<60分钟，T=m分钟s秒，则T=m分钟。
     * 3.3 若1小时<T<24小时，T=h小时m分钟s秒，则T=h小时。
     * 3.4 若T>=1天，T=d天h小时m分钟s秒，则T=d天。
     */
    public static String getDiffString(Date lastTime) {
        if (lastTime == null) {
            return "刚刚";
        }
        String timeDifference = "1分钟前";
        long dValue = DateUtils.getDiffSeconds(lastTime);

        if (dValue >= ONE_MINUTE && dValue < ONE_HOUR) {
            timeDifference = (dValue / ONE_MINUTE) + "分钟前";
        } else if (dValue >= ONE_HOUR && dValue < ONE_DAY) {
            timeDifference = (dValue / ONE_HOUR) + "小时前";
        } else if (dValue >= ONE_DAY) {
            timeDifference = (dValue / ONE_DAY) + "天前";
        }

        return timeDifference;
    }

    /**
     * 访客时间，显示文案：时间显示规则如下：
     * 时间[0,1分钟)，显示“刚刚”
     * 时间[1,60分钟)，显示“x分钟前”
     * 时间[1小时，24小时），显示“x小时前”
     * 时间[1天，30天]，显示“x天前”
     */
    public static String getVisitString(Date lastTime) {
        if (lastTime == null) {
            return "刚刚";
        }
        String timeDifference = "刚刚";
        long dValue = DateUtils.getDiffSeconds(lastTime);
        if (dValue < ONE_MINUTE) {
            timeDifference = "刚刚";
        } else if (dValue >= ONE_MINUTE && dValue < ONE_HOUR) {
            timeDifference = (dValue / ONE_MINUTE) + "分钟前";
        } else if (dValue >= ONE_HOUR && dValue < ONE_DAY) {
            timeDifference = (dValue / ONE_HOUR) + "小时前";
        } else if (dValue >= ONE_DAY) {
            timeDifference = (dValue / ONE_DAY) + "天前";
        }
        return timeDifference;
    }

    /**
     * 格式化时间显示
     * <p>
     * 当日，显示“时:分”
     * 前一天，显示“昨天”
     * 7天内，显示“星期X”
     * 7天外，显示yyyy/MM/dd
     *
     * @param dateTime
     * @return
     */
    public static String getDiffString1(Date dateTime) {
        DateTime nowTime = new DateTime();
        DateTime targetTime = new DateTime(dateTime.getTime());
        int nowDayOfYear = nowTime.getDayOfYear();
        int targetDayOfYear = targetTime.getDayOfYear();
        if (targetDayOfYear > nowDayOfYear) {
            return "刚刚";
        }
        // 当日，显示“时:分”
        if (nowDayOfYear == targetDayOfYear) {
            return DateUtils.toString(dateTime, DatePattern.HM);
        }
        // 前一天，显示“昨天”
        if (nowDayOfYear - targetDayOfYear == 1) {
            return "昨天";
        }
        // 7天内，显示“星期X”
        if (nowDayOfYear - targetDayOfYear < 7) {
            return DateUtils.toString(dateTime, DatePattern.WEEK);
        }
        // 7天外，显示yyyy/MM/dd
        return DateUtils.toString(dateTime, DatePattern.YMD7);
    }

    /**
     * 广场动态 发布时间creatTime，显示文案，时间显示规则如下：
     * 1.时间小于60秒，显示“刚刚“
     * 2.时间大于60秒，小于60分钟，显示格式为“X分钟前”
     * 3.时间大于60分钟，小于当前自然天，显示格式为 "时:分"，如“10:56”
     * 4.一天到二天内的消息显示为：“昨天 时:分”，如“昨天 11:21”
     * 5.时间大于2天，小于600小时，显示格式为 “月-日 时:分" ，如“1-16 10:56”
     * 6.时间大于600小时，小于当前自然年，显示格式为“月-日”，如“12-11”
     * 7.时间以年为跨度时，显示格式为“年-月-日”，如“2018-12-11”
     * 1.7时、分不足二位时，前面用0补齐，月、日不足二位时不补位。如：2018-7-13 09:22
     * 天数按照自然天数来计算
     *
     * @param createTime
     * @return
     */

    public static String getDiffTimeByRule(Date createTime) {
        if (createTime == null) {
            return "刚刚";
        }
        String timeDifference = "刚刚";
        long dValue = DateUtils.getDiffSeconds(createTime);
        Date now = new Date();
        if (0 <= dValue && dValue < ONE_MINUTE) {
            timeDifference = "刚刚";
        } else if (dValue >= ONE_MINUTE && dValue < ONE_HOUR) {
            timeDifference = (dValue / ONE_MINUTE) + "分钟前";
        } else if (getDaysBetween(createTime) < 1 && dValue > ONE_HOUR) {
            timeDifference = DateUtils.toString(createTime, DatePattern.HM);
        } else if (getDaysBetween(createTime) == 1) {
            timeDifference = "昨天 " + DateUtils.toString(createTime, DatePattern.HM);
        } else if ((DateUtils.getYear(now) - DateUtils.getYear(createTime)) < 1 && getDaysBetween(createTime) >= 2 && dValue < 600 * ONE_HOUR) {
            timeDifference = DateUtils.toString(createTime, DatePattern.MD_HM2);
        } else if ((DateUtils.getYear(now) - DateUtils.getYear(createTime)) == 1 && getDaysBetween(createTime) >= 2 && dValue < 600 * ONE_HOUR) {
            timeDifference = DateUtils.toString(createTime, DatePattern.YMD_HM2);
        } else if ((DateUtils.getYear(now) - DateUtils.getYear(createTime)) < 1 && dValue >= 600 * ONE_HOUR) {
            timeDifference = DateUtils.toString(createTime, DatePattern.MD3);
        } else if ((DateUtils.getYear(now) - DateUtils.getYear(createTime)) >= 1) {
            timeDifference = DateUtils.toString(createTime, DatePattern.YMD4);
        }
        return timeDifference;
    }

    /**
     * 输入一个 {@link Calendar} ，将它的 时，分，秒，毫秒 都设置为 0
     */
    public static void clearHourMinuteSecondMill(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 去掉日期的时分秒
     */
    public static Date trunc(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateUtils.clearHourMinuteSecondMill(calendar);
        return calendar.getTime();
    }

    /**
     * 去掉毫秒
     * <p>
     * mysql驱动5.1.30+版本后，时间毫秒数会四舍五入，毫秒数大于500的时间会导致数据库实际多1s
     * 如果对时间精度有要求，插入数据库的时间可调用该方法解决
     *
     * @param time
     * @return
     */
    public static Date truncMillis(Long time) {
        return new Date(time / 1000 * 1000);
    }

    /**
     * 设置时间
     */
    public static Date setTime(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, value);
        return calendar.getTime();
    }

    /**
     * 设置时间的小时数
     */
    public static Date setHour(Date date, int hour) {
        return setTime(date, Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * 设置时间的秒数
     */
    public static Date setSeconds(Date date, int seconds) {
        return setTime(date, Calendar.SECOND, seconds);
    }

    /**
     * 设置时间的毫秒数
     */
    public static Date setMillis(Date date, int millis) {
        return setTime(date, Calendar.MILLISECOND, millis);
    }

    /**
     * 判断该时间是否是本月
     */
    public static boolean isThisMonth(Date date) {
        DateTime now = new DateTime();
        DateTime d = new DateTime(date);

        return now.getMonthOfYear() == d.getMonthOfYear();
    }

    /**
     * 判断该时间是否是本周第一天
     */
    public static boolean isFirstDayOfThisWeek(Date date) {
        return new DateTime(date).getDayOfWeek() == 1;
    }

    /**
     * 判断今天是否是本周第一天
     */
    public static boolean isFirstDayOfThisWeek() {
        return isFirstDayOfThisWeek(new Date());
    }

    /**
     * 是否在指定时间范围内
     */
    public static boolean isInTimeRange(Date beginTime, Date endTime) {
        return isInTimeRange(beginTime, endTime, Calendar.MILLISECOND, new Date());
    }

    /**
     * 是否在指定时间范围内
     */
    public static boolean isInTimeRange(Date beginTime, Date endTime, Date compareTime) {
        return isInTimeRange(beginTime, endTime, Calendar.MILLISECOND, compareTime);
    }


    /**
     * 是否在指定时间范围内
     * 比如传入：201909200011, 201909200059, 20190920005959 则返回true
     * 比如传入：201909200011, 201909200059, 20190920005959999 则返回true
     *
     * @param truncField Calendar Field，要裁剪的字段
     */
    public static boolean isInTimeRange(Date beginTime, Date endTime, int truncField, Date compareTime) {
        return DateUtils.getTimeStage(beginTime, endTime, truncField, compareTime) == TimeStage.ING;
    }

    /**
     * 获取指定日期所处阶段
     *
     * @param beginTime
     * @param endTime
     * @param compareTime
     * @return
     */
    public static TimeStage getTimeStage(Date beginTime, Date endTime, Date compareTime) {
        return DateUtils.getTimeStage(beginTime, endTime, Calendar.MILLISECOND, compareTime);
    }

    /**
     * 获取指定日期所处阶段
     *
     * @param beginTime
     * @param endTime
     * @param truncField  Calendar Field，要裁剪的字段
     * @param compareTime
     * @return BEFORE、在指定日期之前 ING、在指定日期中 AFTER、在指定日期后
     */
    public static TimeStage getTimeStage(Date beginTime, Date endTime, int truncField, Date compareTime) {
        if (compareTime.before(beginTime)) {
            return TimeStage.BEFORE;
        }
        // After比较之前，优先裁剪掉比较时间的字段
        Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(compareTime);
        if (truncField == Calendar.MILLISECOND) {
            compareDate.set(Calendar.MILLISECOND, 0);
        } else if (truncField == Calendar.SECOND) {
            compareDate.set(Calendar.SECOND, 0);
            compareDate.set(Calendar.MILLISECOND, 0);
        } else if (truncField == Calendar.MINUTE) {
            compareDate.set(Calendar.MILLISECOND, 0);
            compareDate.set(Calendar.SECOND, 0);
            compareDate.set(Calendar.MINUTE, 0);
        } else if (truncField == Calendar.HOUR_OF_DAY) {
            compareDate.set(Calendar.MILLISECOND, 0);
            compareDate.set(Calendar.SECOND, 0);
            compareDate.set(Calendar.MINUTE, 0);
            compareDate.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (compareDate.getTime().after(endTime)) {
            return TimeStage.AFTER;
        }
        return TimeStage.ING;
    }

    /**
     * 判断当前日期是否在某个范围内
     *
     * @param dateRange 格式：2018.5.15-2018.5.20||5.15-5.20
     * @return -1、在日期范围之前 0、在日期范围内 1、在日期范围后
     */
    public static int isInDateRange(String dateRange) {
        if (StringUtils.isEmpty(dateRange)) {
            return -1;
        }
        String[] dates = StringUtils.split(dateRange, "-");
        String[] values = StringUtils.split(dates[0], ".");
        if (values.length != 3 && values.length != 2) {
            return -1;
        }
        int index = 0;
        Calendar cal = Calendar.getInstance();
        if (values.length == 3) {
            cal.set(Calendar.YEAR, new Integer(values[index++]));
        }
        cal.set(Calendar.MONDAY, new Integer(values[index++]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, new Integer(values[index++]));
        clearHourMinuteSecondMill(cal);
        Date startDate = cal.getTime();

        values = StringUtils.split(dates[1], ".");
        if (values.length != 3 && values.length != 2) {
            return -1;
        }
        index = 0;
        if (values.length == 3) {
            cal.set(Calendar.YEAR, new Integer(values[index++]));
        }
        cal.set(Calendar.MONDAY, new Integer(values[index++]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, new Integer(values[index++]));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date endDate = cal.getTime();

        Date nowDate = new Date();
        if (nowDate.before(startDate)) {
            return -1;
        }
        if (nowDate.after(endDate)) {
            return 1;
        }
        return 0;
    }

    /**
     * 给指定日期增加月数
     */
    public static Date plusMonths(Date date, int months) {
        return new DateTime(date).plusMonths(months).toDate();
    }

    /**
     * 给指定日期增加周数
     */
    public static Date plusWeeks(Date date, int weeks) {
        return new DateTime(date).plusWeeks(weeks).toDate();
    }

    /**
     * 给指定日期增加天数
     */
    public static Date plusDays(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }

    /**
     * 给指定日期增加年数
     */
    public static Date plusYear(Date date, int years) {
        return new DateTime(date).plusYears(years).toDate();
    }

    /**
     * 给指定日期增加分数
     */
    public static Date plusMinutes(Date date, int minutes) {
        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    /**
     * 给指定日期增加秒数
     */
    public static Date plusSeconds(Date date, int seconds) {
        return new DateTime(date).plusSeconds(seconds).toDate();
    }

    /**
     * 给指定日期增加毫秒数
     */
    public static Date plusMillis(Date date, int millis) {
        return new DateTime(date).plusMillis(millis).toDate();
    }

    /**
     * 给指定日期增加小时
     */
    public static Date plusHours(Date date, int hours) {
        return new DateTime(date).plusHours(hours).toDate();
    }

    /**
     * 获取当天的结束时间
     */
    public static Date getEndOfDay() {
        return DateUtils.getEndOfDay(new Date(), false);
    }

    /**
     * 获取当天的开始时间
     */
    public static Date getStartOfDay(Date date) {
        return DateUtils.plusDays(DateUtils.getEndOfDay(date, false), -1);
    }

    /**
     * 获取当天的结束时间
     */
    public static Date getEndOfDay(Date date) {
        return DateUtils.getEndOfDay(date, false);
    }

    /**
     * 获取当天的结束时间
     *
     * @param isSameDay 是否当天，true为当天23:59:59 否则为第二天00:00:00
     */
    public static Date getEndOfDay(boolean isSameDay) {
        return DateUtils.getEndOfDay(new Date(), isSameDay);
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date
     * @param isSameDay 是否当天，true为当天23:59:59 否则为第二天00:00:00
     */
    public static Date getEndOfDay(Date date, boolean isSameDay) {
        if (isSameDay) {
            // 毫秒不能使用999，mysql入库时会自动四舍五入变成第二天0点
            return new DateTime(date).withTime(23, 59, 59, 0).toDate();
        }
        return new DateTime(date).plusDays(1).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取本周周末结束时间
     */
    public static Date getEndOfWeek() {
        return DateUtils.getEndOfWeek(new Date());
    }

    /**
     * 获取指定日期所在周的周末结束时间
     */
    public static Date getEndOfWeek(Date date) {
        return new DateTime(date).plusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay().toDate();
    }

    /**
     * 湖区指定日期周末结束时间
     *
     * @param date
     * @param isSameDay 是否为同一天
     * @return
     */
    public static Date getEndOfWeek(Date date, boolean isSameDay) {
        if (isSameDay) {
            // 毫秒不能使用999，mysql入库时会自动四舍五入变成第二天0点
            return new DateTime(date).dayOfWeek().withMaximumValue().withTime(23, 59, 59, 0).toDate();
        }
        return DateUtils.getEndOfWeek(date);
    }

    /**
     * 获取本月月末结束时间
     */
    public static Date getEndOfMonth() {
        return DateUtils.getEndOfMonth(new Date());
    }

    /**
     * 获取指定日期所在月的月末结束时间
     */
    public static Date getEndOfMonth(Date date) {
        return new DateTime(date).plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取指定日期所在月的结束时间
     *
     * @param date
     * @param isSameDay
     * @return
     */
    public static Date getEndOfMonth(Date date, boolean isSameDay) {
        if (isSameDay) {
            // 毫秒不能使用999，mysql入库时会自动四舍五入变成第二天0点
            return new DateTime(date).dayOfMonth().withMaximumValue().withTime(23, 59, 59, 0).toDate();
        }
        return DateUtils.getEndOfMonth(date);
    }

    /**
     * 获取本周的开始时间
     */
    public static Date getFirstOfWeek() {
        return getFirstOfWeek(new Date());
    }

    /**
     * 获取指定日期所在周的周开始时间
     */
    public static Date getFirstOfWeek(Date date) {
        return (new DateTime(date)).dayOfWeek().withMinimumValue().withTime(0, 0, 0, 0).toDate();
    }

    /**
     * 获取本月的月开始时间
     */
    public static Date getFirstOfMonth() {
        return getFirstOfMonth(new Date());
    }

    /**
     * 获取指定日期所在月的月开始时间
     */
    public static Date getFirstOfMonth(Date date) {
        return (new DateTime(date)).dayOfMonth().withMinimumValue().withTime(0, 0, 0, 0).toDate();
    }

    /**
     * 获取当前时间
     */
    public static Date nowTime() {
        return new Date();
    }

    /**
     * 获取当前日期(没有时分秒)
     */
    public static Date nowDate() {
        return DateUtils.trunc(new Date());
    }

    /**
     * 获取当前时间Unix时间
     */
    public static long nowUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取剩余的时间字符串：2天、10小时、10分钟、10秒
     */
    public static String getRemainingTimeStr(long seconds) {
        if (seconds <= 0) {
            return null;
        }
        double secs = seconds * 1D;
        double days = secs / ONE_DAY;
        if (days >= 1) {
            return (int) Math.ceil(days) + "天";
        }
        double hour = secs / ONE_HOUR;
        if (hour >= 1) {
            return (int) Math.ceil(hour) + "小时";
        }
        double minutes = secs / ONE_MINUTE;
        if (minutes >= 1) {
            return (int) Math.ceil(minutes) + "分钟";
        }
        return seconds + "秒";
    }

    /**
     * 获取剩余天数
     * <p>
     * 不足1天显示1天，1天还未结束，显示整天
     *
     * @param latelyTime
     * @return
     */
    public static int getRemainingDays(Date latelyTime) {
        return DateUtils.getRemainingDays(new Date(), latelyTime);
    }

    /**
     * 获取剩余天数
     * <p>
     * 不足1天显示1天，1天还未结束，显示整天
     *
     * @param earlyTime
     * @param latelyTime
     * @return
     */
    public static int getRemainingDays(Date earlyTime, Date latelyTime) {
        if (earlyTime.after(latelyTime)) {
            return 0;
        }
        double days = (latelyTime.getTime() - earlyTime.getTime()) / 1000D / ONE_DAY;
        return (int) Math.ceil(days);
    }

    /**
     * 将分钟转成时间字符串
     *
     * @param minutes
     * @return
     */
    public static String minuteToTimeStr(int minutes) {
        if (minutes <= 0) {
            return "0分";
        }
        Integer days = minutes / (24 * 60);
        Integer hours = (minutes - (days * 24 * 60)) / 60;
        Integer mins = minutes % 60;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days + "天");
        }
        if (hours > 0) {
            sb.append(hours + "小时");
        }
        if (mins > 0) {
            sb.append(mins + "分");
        }
        return sb.toString();
    }

    /**
     * 将秒转成时间字符串
     *
     * @param second
     * @return
     */
    public static String secondToTimeStr(Integer second, boolean showSec) {
        if (null == second || second < 0) {
            return "0秒";
        }
        Integer days = second / (24 * 60 * 60);
        Integer hours = (second - (days * 24 * 60 * 60)) / 3600;
        Integer mins = (second - (days * 24 * 60 * 60) - (hours * 60 * 60)) / 60;
        Integer seconds = second % 60;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days + "天");
        }
        if (hours > 0) {
            sb.append(hours + "小时");
        }
        if (mins > 0) {
            sb.append(mins + "分钟");
        }
        if (seconds >= 0 && showSec) {
            sb.append(seconds + "秒");
        }
        return sb.toString();
    }

    /**
     * 将秒转成时间字符串
     *
     * @param second
     * @return
     */
    public static String secondToTimeStr(Integer second) {
        return secondToTimeStr(second, true);
    }

    /**
     * 将秒数转成xx:xx:xx的时间格式
     *
     * @param second
     * @return
     */
    public static String secondToTimeStr2(Integer second) {
        if (second == null || second <= 0) {
            return "00:00:00";
        }
        int hours = second / 3600;
        int minutes = second % 3600 / 60;
        int seconds = second % 60;
        StringBuffer sb = new StringBuffer();
        if (hours < 10) {
            sb.append("0");
        }
        sb.append(hours).append(":");
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes).append(":");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);
        return sb.toString();
    }

    /**
     * 将秒数转成xx:xx的时间格式
     *
     * @param second
     * @return
     */
    public static String secondToTimeStr5(Integer second) {
        if (second == null || second <= 0) {
            return null;
        }
        int minutes = second / 60;
        int seconds = second % 60;
        StringBuffer sb = new StringBuffer();
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes).append(":");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);
        return sb.toString();
    }

    /**
     * 将秒转成剩余时间
     *
     * @param second
     * @return
     */
    public static String secondsToTimeStr3(Integer second) {
        if (second <= ONE_MINUTE) {
            return "1分钟";
        }
        int days = second / ONE_DAY;
        int hours = (second % ONE_DAY) / ONE_HOUR;
        int minutes = (second % ONE_HOUR) / ONE_MINUTE;
        StringBuffer sb = new StringBuffer();
        if (days > 0) {
            sb.append(days + "天");
        }
        if (hours > 0) {
            sb.append(hours + "小时");
        }
        if (minutes > 0) {
            sb.append(minutes + "分钟");
        }
        return sb.toString();
    }

    /**
     * 将秒转成剩余时间
     *
     * @param second
     * @return
     */
    public static String secondsToTimeStr4(Integer second) {
        if (second <= ONE_MINUTE) {
            return "1分钟";
        }
        int days = second / ONE_DAY;
        int hours = (second % ONE_DAY) / ONE_HOUR;
        int minutes = (second % ONE_HOUR) / ONE_MINUTE;
        StringBuffer sb = new StringBuffer();
        if (days > 0) {
            sb.append(days + "天");
        }
        if (hours > 0) {
            sb.append(hours + "小时");
        }
        // 如果还有小时则不显示分
        if (sb.length() > 0) {
            return sb.toString();
        }
        if (minutes > 0) {
            sb.append(minutes + "分钟");
        }
        return sb.toString();
    }

    /**
     * 解析日期范围(开始时间和结束时间)
     *
     * @param dateTimeRange
     * @return
     */
    public static Date[] parseRangeDate(String[] dateTimeRange) {
        if (dateTimeRange == null || dateTimeRange.length != 2) {
            return null;
        }
        String startDateStr = dateTimeRange[0];
        String endDateStr = dateTimeRange[1];
        if (startDateStr.indexOf(":") == -1) {
            startDateStr = startDateStr + " 00:00:00";
            endDateStr = endDateStr + " 23:59:59";
        }
        return new Date[]{DateUtils.parse(startDateStr), DateUtils.parse(endDateStr)};
    }

    /**
     * 解析日期范围(开始时间和结束时间)
     *
     * @param dateTimeRange
     * @return
     */
    public static Date[] parseRangeDate(String dateTimeRange) {
        if (dateTimeRange == null) {
            return null;
        }
        return DateUtils.parseRangeDate(StringUtils.split(dateTimeRange, ","));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isSameDay(Date date) {
        return isSameDay(date, DateUtils.nowDate());
    }

    /**
     * 判断两个日期是否为同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * 判断两个日期是否为同一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        return dt1.getYear() == dt2.getYear() && dt1.getMonthOfYear() == dt2.getMonthOfYear();
    }

    /**
     * 获取两个时间中间的完整月数间隔(不包括开始月份和结束月份)
     * 如果是同一个月返回-1
     *
     * @param early
     * @param late
     * @return
     */
    public static int getDiffMonth(Date early, Date late) {
        if (isSameMonth(early, late)) {
            return -1;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(early);
        int eMonth = c.get(Calendar.MONTH);
        int eYear = c.get(Calendar.YEAR);
        c.setTime(late);
        int lMonth = c.get(Calendar.MONTH);
        int lYear = c.get(Calendar.YEAR);
        return (lYear - eYear) * 12 + (lMonth - eMonth) - 1;
    }


    /**
     * 当前日期和指定日期相差自然天数
     *
     * @param date
     * @return
     */
    public static int getDaysBetween(Date date) {
        Date now = DateUtils.parse(new Date(), DatePattern.YMD);
        Date tmpDate = DateUtils.parse(date, DatePattern.YMD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(now);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定日期星期几，如：星期一
     */
    public static String getWeekDay(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String currSun = dateFm.format(date);
        return currSun;
    }

    /**
     * 获取两个时间的间隔秒数
     *
     * @param earlyTime
     * @param latelyTime
     * @return
     */
    public static Long ttl(Long earlyTime, Long latelyTime) {
        return Math.max(0, (latelyTime - earlyTime) / 1000);
    }

    /**
     * 获取日期是当年的额第几周
     *
     * @param date
     * @return
     */
    public static Integer getWeekOfYear(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        instance.setTime(date);
        return instance.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 转换前端Antd提交过来的时间区域组件
     *
     * @param createTime 区间以数组传递过来的
     * @return 0 位 开始时间，1位结束时间
     */
    public static Date[] parseAntdTimeWidgets(String[] createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createTime != null && createTime.length == 2) {
            String startDateStr = createTime[0];
            String endDateStr = createTime[1];
            if (startDateStr.length() == 10) {
                startDateStr = startDateStr + " 00:00:00";
                endDateStr = endDateStr + " 23:59:59";
            }
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);
                return new Date[]{startDate, endDate};
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Date[]{null, null};
    }

    /**
     * 获取目标日期是目标日期所在月的第几天
     */
    public static Integer getDayOfMonth(Date date) {
        DateTime targetTime = new DateTime(date);
        return targetTime.getDayOfMonth();
    }

    public static void main(String[] args) {
////        System.out.println(DateUtils.toString(getFirstOfMonth(new Date()), DatePattern.YMD_HMS));
//        Date nowTime = DateUtils.parse("2021-04-29 23:00:00", DatePattern.YMD_HMS);
//        Date targetTime = DateUtils.parse("2021-05-29 12:00:00", DatePattern.YMD_HMS);
//        System.out.println(DateUtils.getRemainingDays(nowTime, targetTime));
//        System.out.println(DateUtils.getEndOfMonth(new Date()));
      /*  Date date = DateUtils.plusDays(new Date(), -3);
        System.out.println(date);
        System.out.println(DateUtils.isSameWeek(new Date(), date));*/
//        Long seconds = getDiffSeconds("2022-03-03 13:00:00", "2022-03-03 13:00:00");
//        System.out.println(secondsToTimeStr3(seconds.intValue()));
        System.out.println(DateUtils.parse("1989-04-16", DatePattern.YMD));

//        System.out.println(getDiffMonth(DateUtils.parse("2020-11-01 13:00:00"), DateUtils.parse("2022-11-11 13:00:00")));
    }
}
