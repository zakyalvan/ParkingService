package parking.space;

import java.io.Serializable;

/**
 * @author zakyalvan
 */
public interface Occupant extends Serializable {
    String registerNumber();

    public static CarInfo carInfo(String registerNumber, PaintColor paintColor) {
        return new CarInfo(registerNumber, paintColor);
    }
}
