package parking.space;

import java.util.Objects;

/**
 * @author zakyalvan
 */
public class CarInfo implements Occupant {
    private String registerNumber;
    private PaintColor paintColor;

    CarInfo(String registerNumber, PaintColor paintColor) {
        this.registerNumber = registerNumber;
        this.paintColor = paintColor;
    }

    @Override
    public String registerNumber() {
        return registerNumber;
    }
    public PaintColor paintColor() {
        return paintColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarInfo carInfo = (CarInfo) o;
        return Objects.equals(registerNumber.toUpperCase(), carInfo.registerNumber.toUpperCase());
    }
    @Override
    public int hashCode() {
        return Objects.hash(registerNumber.toUpperCase());
    }

    public static CarInfo withDetails(String registerNumber, PaintColor paintColor) {
        return new CarInfo(registerNumber, paintColor);
    }
}
