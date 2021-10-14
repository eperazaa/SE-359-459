package se459.extremers.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CleanSweep {
    static Integer row = 0;
    static Integer maxColumns = 5;
    static Integer col = 0;
    private static HashMap<Integer, String> floorPlan = new HashMap<Integer, String>();;
    static NavigationOptionsEnum direction = NavigationOptionsEnum.EAST;
    public static void main(String args[]) throws FileNotFoundException {
       

        SensorArray sa;
       // CleanSweep cs = new CleanSweep();
       
            
        File file = new File("C:\\Users\\eperazaa\\VSCode\\SE-459\\file.csv");
        
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

         

            System.out.print(id + "- ");
            System.out.print(n_sensor.toString() + " | ");
            System.out.print(s_sensor.toString() + " | ");
            System.out.print(e_sensor.toString() + " | ");
            System.out.print(w_sensor.toString() + " | ");
            System.out.print(bottom_sensor + " | ");
            System.out.print(dirt_sensor + " | ");
            System.out.print(surface_sensor.toString() + " | ");
            System.out.println(charging_station); 


            sa = new SensorArray(n_sensor,s_sensor,e_sensor,w_sensor,bottom_sensor,dirt_sensor,surface_sensor,charging_station);
            addCell(sa);
            clean(sa);
            traverse(sa);
            System.out.println( );

            
         }
         scanner.close();
         System.out.println( "--------------------------------------");
         System.out.println( floorPlan );
        
 
    }

    private static void addCell(SensorArray sa) {
        
        floorPlan.put(calculateArrayPos(), row.toString() + "x" + col.toString());
    }

    private static Integer calculateArrayPos() {

        return (row * (maxColumns-1)) + col;
    }

    private static void traverse(SensorArray sa) {
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
        
        System.out.println("Cleaning...");
    }

    public static void moveNorth() {
        System.out.println("N");
        CleanSweep.row--;
    }
    public static void moveSouth() {
        System.out.println("S");
        CleanSweep.row++;
    }

    public static void moveEast() {
        System.out.println("E");
        CleanSweep.col++;
        
    }
    public static void moveWest(){
        System.out.println("W");
        CleanSweep.col--;
    }
    
}
