package se459.extremers.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import se459.extremers.cleanSweepFloorPlan.CleanSweepNode;

public class CleanSweep {
    static int row = 0;
    static int col = 0;
    static int maxI= 0;
    static int maxJ= 0;
    private static HashMap<String, String> floorPlan = new HashMap<String, String>();  //TODO:  Replace String for BuiltMapNode or SensorArray
    
    static NavigationOptionsEnum direction = NavigationOptionsEnum.EAST;
    public static void main(String args[]) throws FileNotFoundException {
       

        SensorArray sa = new SensorArray();
            
        File file = new File("./src/test/file.csv");
        
        Scanner scanner = new Scanner(file);
        
        while(scanner.hasNext()){
            String[] tokens = scanner.nextLine().split(",");
            String id = tokens[0];
            //SENSOR ARRAY VALUES
            PathOptionsEnum n_sensor= PathOptionsEnum.getEnumValue(tokens[1]); //path info top of the cell
            PathOptionsEnum s_sensor=  PathOptionsEnum.getEnumValue(tokens[2]); //path info bottom of the cell
            PathOptionsEnum e_sensor=  PathOptionsEnum.getEnumValue(tokens[3]); //path info right of the cell
            PathOptionsEnum w_sensor=  PathOptionsEnum.getEnumValue(tokens[4]); //path info left of the cell
            boolean bottom_sensor= Boolean.parseBoolean(tokens[5]);  //there is a stair/decline
            boolean dirt_sensor= Boolean.parseBoolean(tokens[6]); //whether there is dirt
            SurfaceEnum surface_sensor=  SurfaceEnum.valueOf(tokens[7]);  //kind of surface
            boolean charging_station=  Boolean.parseBoolean(tokens[8]); //whether there is a charging station
            
            // Because the sensor array is unaware of the floor type it is moving to, the battery reduction for traversal has to be done here
            if (sa.surface_sensor != null) {
                // Manage power for traversal: remove average of last surface and current surface
                sa.RemoveCharge((sa.surface_sensor.getUnits() + surface_sensor.getUnits())/2f);
                System.out.println("Capacity after traversal = " + sa.GetCharge());
                System.out.println( );
            }
            
            float battery_capacity = sa.GetCharge(); // Get battery capacity from last sa, or get default value of 250 if first iteration

            System.out.print(id + "- ");
            System.out.print(n_sensor.toString() + " | ");
            System.out.print(s_sensor.toString() + " | ");
            System.out.print(e_sensor.toString() + " | ");
            System.out.print(w_sensor.toString() + " | ");
            System.out.print(bottom_sensor + " | ");
            System.out.print(dirt_sensor + " | ");
            System.out.print(surface_sensor.toString() + " | ");
            System.out.println(charging_station); 

            

            sa = new SensorArray(n_sensor,s_sensor,e_sensor,w_sensor,bottom_sensor,dirt_sensor,surface_sensor,charging_station, battery_capacity);
            addCell(sa);
            clean(sa);
            traverse(sa);
            System.out.println( );

            
         }
         scanner.close();
         maxI++;
         maxJ++;
         System.out.println( "--------------------------------------");
         System.out.println( floorPlan );
         System.out.println( "--------------------------------------");
         printFloorPlan();
        
 
    }

    private static void printFloorPlan() {
        System.out.println("MAX ROW: " + maxI);
        System.out.println("MAX COL: " + maxJ);
        String key = "";
        String value = "";
        for (int i = 0; i < maxI; i++) {
            for (int j = 0; j < maxJ; j++) {
                key = i +"," + j;
                value = (String)floorPlan.get(key);
                System.out.print(value);
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }

    private static void addCell(SensorArray sa) {
        
        String key = row +"," + col;
        floorPlan.put(key, row + "x" + col);

         //TODO:  Replace String for BuiltMapNode
    }

  

    private static void traverse(SensorArray sa) {
        System.out.println("Capacity before traversal = " + sa.GetCharge());
        System.out.println("Traversing...");

        switch (direction) {
            case EAST:
                if (sa.e_sensor.equals(PathOptionsEnum.OPEN) /*&& !visited(row, col + new Integer(1)) */) 
                {
                    moveEast();

                } else 
                {  
                    if (sa.s_sensor.equals(PathOptionsEnum.OPEN)) {
                        CleanSweep.direction = NavigationOptionsEnum.SOUTH;
                        moveSouth();
                    }
                }   
                break;
            case SOUTH:
                if (sa.w_sensor.equals(PathOptionsEnum.OPEN)) {
                    CleanSweep.direction = NavigationOptionsEnum.WEST;
                    moveWest();

                } else 
                {
                    if (sa.e_sensor.equals(PathOptionsEnum.OPEN)) {
                        CleanSweep.direction = NavigationOptionsEnum.EAST;
                        moveEast();
                    } 
                }
                break;
            case WEST:
                if (sa.w_sensor.equals(PathOptionsEnum.OPEN) /* && !visited(row, col - new Integer(1))*/) 
                {   
                    moveWest();

                } else {
                    if (sa.s_sensor.equals(PathOptionsEnum.OPEN)) {
                        CleanSweep.direction = NavigationOptionsEnum.SOUTH;
                        moveSouth();
                    }
                } 
                break;
            case NORTH:
                
                break;
        
            default:
                break;
        }
    }

   /*  private static boolean visited(Integer i, Integer j) {
        
        return floorPlan.containsKey((i * maxColumns) + j);
    } */

    private static void clean(SensorArray sa) {
        System.out.println("Capacity before clean = " + sa.GetCharge());
        System.out.println("Cleaning...");

        // remove battery charge depending on current floor type
        sa.RemoveCharge(sa.surface_sensor.getUnits());

        System.out.println("Capacity after clean = " + sa.GetCharge());
    }

    public static void moveNorth() {
        System.out.println("N");
        CleanSweep.row--;
    }
    public static void moveSouth() {
        System.out.println("S");
        CleanSweep.row++;
        setMaxRow();
    }

    private static void setMaxRow() {
        if (row > maxI) maxI = row;
    }

    public static void moveEast() {
        System.out.println("E");
        CleanSweep.col++;
        setMaxCol();
        
    }
    private static void setMaxCol() {
        if (col > maxJ) maxJ = col;
    }

    public static void moveWest(){
        System.out.println("W");
        CleanSweep.col--;
    }
    
}
