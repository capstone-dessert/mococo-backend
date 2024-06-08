package capstone.dessert.mococobackend.util;

import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

public class CoordinateConvertor {

    private static final double RE = 6371.00877; // 지구 반경(km)
    private static final double GRID = 5.0; // 격자 간격(km)
    private static final double SLAT1 = 30.0; // 투영 위도1(degree)
    private static final double SLAT2 = 60.0; // 투영 위도2(degree)
    private static final double OLON = 126.0; // 기준점 경도(degree)
    private static final double OLAT = 38.0; // 기준점 위도(degree)
    private static final double XO = 43; // 기준점 X좌표(GRID)
    private static final double YO = 136; // 기준점 Y좌표(GRID)

    private CoordinateConvertor() {
    }

    public static LatXLngY convertGpsToGrid(double latitude, double longitude) {

        double DEGRAD = PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = tan(PI * 0.25 + slat2 * 0.5) / tan(PI * 0.25 + slat1 * 0.5);
        sn = log(cos(slat1) / cos(slat2)) / log(sn);
        double sf = tan(PI * 0.25 + slat1 * 0.5);
        sf = pow(sf, sn) * cos(slat1) / sn;
        double ro = tan(PI * 0.25 + olat * 0.5);
        ro = re * sf / pow(ro, sn);

        double ra = tan(PI * 0.25 + (latitude) * DEGRAD * 0.5);
        ra = re * sf / pow(ra, sn);
        double theta = longitude * DEGRAD - olon;
        if (theta > PI) theta -= 2.0 * PI;
        if (theta < -PI) theta += 2.0 * PI;
        theta *= sn;

        LatXLngY rs = new LatXLngY();
        rs.setX((int) floor(ra * sin(theta) + XO + 0.5));
        rs.setY((int) floor(ro - ra * cos(theta) + YO + 0.5));

        return rs;
    }

    @Getter
    @Setter
    public static class LatXLngY {
        private int x;
        private int y;
    }
}