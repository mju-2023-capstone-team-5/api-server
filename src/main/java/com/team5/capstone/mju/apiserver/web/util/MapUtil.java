package com.team5.capstone.mju.apiserver.web.util;

import org.springframework.stereotype.Component;

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
    public boolean isInsideRectangle(double x, double y, double x1, double y1, double x2, double y2) {
        double topLeftX = Math.min(x1, x2);
        double topLeftY = Math.max(y1, y2);
        double bottomRightX = Math.max(x1, x2);
        double bottomRightY = Math.min(y1, y2);

        return (x >= topLeftX && x <= bottomRightX && y >= bottomRightY && y <= topLeftY);
    }

}
