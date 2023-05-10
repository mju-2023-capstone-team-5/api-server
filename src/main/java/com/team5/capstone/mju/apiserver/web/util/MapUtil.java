package com.team5.capstone.mju.apiserver.web.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MapUtil {

    /**
     *
     * @param x target 위도
     * @param y target 경도
     * @param x1 왼쪽 위 위도
     * @param y1 왼쪽 위 경도
     * @param x2 오른쪽 아래 위도
     * @param y2 오른쪽 아래 경도
     * @return
     */
    public boolean isInsideRectangle(BigDecimal x, BigDecimal y, BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
        BigDecimal topLeftX = x1.min(x2);
        BigDecimal topLeftY = y1.max(y2);
        BigDecimal bottomRightX = x1.max(x2);
        BigDecimal bottomRightY = y1.min(y2);

        return (x.compareTo(topLeftX) >= 0 && x.compareTo(bottomRightX) <= 0 &&
                y.compareTo(bottomRightY) >= 0 && y.compareTo(topLeftY) <= 0);
    }

}
