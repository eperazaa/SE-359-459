package se459.extremers.simulator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SensorArray {
    PathOptionsEnum n_sensor; //path info top of the cell
    PathOptionsEnum s_sensor; //path info bottom of the cell
    PathOptionsEnum e_sensor; //path info right of the cell
    PathOptionsEnum w_sensor; //path info left of the cell
    boolean bottom_sensor;  //there is a stair/decline
    boolean dirt_sensor; //whether there is dirt
    SurfaceEnum surface_sensor;  //kind of surface
    boolean charging_station; //whether there is a charging station

    float battery_charge; //battery capacity of robot

    SensorArray() {
        this.n_sensor = null;
        this.s_sensor = null;
        this.e_sensor = null;
        this.w_sensor = null;
        this.bottom_sensor = false;
        this.dirt_sensor = false;
        this.surface_sensor = null;
        this.charging_station = false;
        this.battery_charge = 250f;
    }

    public float GetCharge() {
        return this.battery_charge;
    }

    public void RemoveCharge(float reductionAmount) {
        this.battery_charge -= reductionAmount;
    }
}
