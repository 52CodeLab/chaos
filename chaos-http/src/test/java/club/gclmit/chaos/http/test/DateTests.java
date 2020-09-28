package club.gclmit.chaos.http.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * <p>
 *  时间工具测试工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/27 10:35 上午
 * @version: V1.0
 * @since 1.8
 */
public class DateTests {

    public static void main(String[] args) {
        long timestamp = 1585275097596L;
        LocalDateTime dateTime = LocalDateTime.now();
        getWeekName(timestamp,dateTime);
    }

    public static void getWeekName(long timestamp,LocalDateTime dateTime){
        System.out.println(DateUtils.timestampToWeekName(timestamp));
        System.out.println("=========================================");
        System.out.println(DateUtils.localDateTimeToWeekName(LocalDateTime.now()));
        System.out.println(DateUtils.localDateTimeToWeekName(LocalDateTime.now().plusDays(1L)));
        System.out.println(DateUtils.localDateTimeToWeekName(LocalDateTime.now().plusDays(2L)));
        System.out.println("=========================================");

        long second = dateTime.toEpochSecond(ZoneOffset.of("+8"));
        long second2 = dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long milli  =  dateTime.atZone(ZoneId.of("+8")).toInstant().toEpochMilli();
        System.out.println("second："+ second +"\tsecond2："+ second2 +"\tmilli：" + milli);
        System.out.println("=========================================");
        System.out.println(DateUtils.timestampToWeekName(second));
        System.out.println("-----------------------------------------");
        System.out.println(DateUtils.timestampToWeekName(second2));
        System.out.println("-----------------------------------------");
        System.out.println(DateUtils.timestampToWeekName(milli));
        System.out.println(DateUtils.timestampToWeekName(158527486L));
    }
}

