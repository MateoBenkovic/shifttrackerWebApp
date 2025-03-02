package com.hita.shifttracker.utils;

import java.math.BigDecimal;

public class TimeConverterHelper {

    public static BigDecimal convertToBigDecimal(int hours, int minutes) {
        return BigDecimal.valueOf(hours)
                .add(BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60)));
    }

    public static BigDecimal roundToHalfHour(BigDecimal decimalTime) {
        return decimalTime
                .multiply(BigDecimal.valueOf(2))
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .divide(BigDecimal.valueOf(2));
    }

    public static BigDecimal convertAndRoundToHalf(int hours, int minutes) {
        BigDecimal decimalTime = BigDecimal.valueOf(hours)
                .add(BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60)));
        return decimalTime
                .multiply(BigDecimal.valueOf(2))
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .divide(BigDecimal.valueOf(2));
    }

    public static BigDecimal calculateTotalHours(BigDecimal hoursFrom, BigDecimal hoursTo) {
        return hoursTo.subtract(hoursFrom).abs();
    }
}
