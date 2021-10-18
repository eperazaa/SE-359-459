package se459.extremers.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class CleanSweep {
    private int row = 0; //initial or current row
    private int col = 0; //initial or current col
    private int maxI= 0; //max number of rows
    private int maxJ= 0; //max number of cols
    private  HashMap<CellIndex, String> floorPlan = new HashMap<CellIndex, String>();  //TODO:  Replace String for BuiltMapNode or SensorArray

    private NavigationOptionsEnum direction = NavigationOptionsEnum.EAST;
    private  SensorArray sa;


    // public  void main(String args[]) throws FileNotFoundException {
//
  //      simulateFromFile("./src/test/file.csv");
    //}

    
    public  void simulateFromFile(String filepath) throws FileNotFoundException {


        Scanner scanner = new Scanner(new File(filepath));
        
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
         maxI++;
         maxJ++;
         System.out.println( "--------------------------------------");
         System.out.println( floorPlan );
         System.out.println( "--------------------------------------");
         printFloorPlan();


    }


    public  void printFloorPlan() {
        System.out.println("MAX ROW: " + maxI);
        System.out.println("MAX COL: " + maxJ);
        CellIndex key = null;
        String value = null;
        for (int i = 0; i < maxI; i++) {
            for (int j = 0; j < maxJ; j++) {
                key = new CellIndex(i,j);
                value = (String)floorPlan.get(key);
                System.out.print(value);
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }


    public HashMap<CellIndex, String> getFloorPlan() {
        return floorPlan;
    }

    public  void addCell(SensorArray sa) {

        //String key = row +"," + col;
        CellIndex ci = new CellIndex(row, col);
        floorPlan.put(ci, row +"," + col);

         //TODO:  Replace String for BuiltMapNode
    }

  

    public  void traverse(SensorArray sa) {
        System.out.println("Traversing..." + direction.toString());
        switch (direction) {
            case EAST:
                if (sa.e_sensor.equals(PathOptionsEnum.OPEN) /*&& !visited(row, col + new Integer(1)) */) 
                {
                    moveEast();

                }
                else
                {  
                    if (sa.s_sensor.equals(PathOptionsEnum.OPEN)) {
                      //  CleanSweep.direction = NavigationOptionsEnum.SOUTH;
                      direction = NavigationOptionsEnum.SOUTH;

                        moveSouth();
                    }

                }   
                break;
            case SOUTH:
                if (sa.w_sensor.equals(PathOptionsEnum.OPEN)) {
                    //CleanSweep.
                    direction = NavigationOptionsEnum.WEST;
                    moveWest();

                } else 
                {
                    if (sa.e_sensor.equals(PathOptionsEnum.OPEN)) {
                       // CleanSweep.
                        direction = NavigationOptionsEnum.EAST;
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
                        //CleanSweep.
                        direction = NavigationOptionsEnum.SOUTH;
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

   /*  private  boolean visited(Integer i, Integer j) {
        
        return floorPlan.containsKey((i * maxColumns) + j);
    } */

    public  void clean(SensorArray sa) {
        
        System.out.println("Cleaning...");
    }

    public  void moveNorth() {
        System.out.println("N");
        //CleanSweep.
                row--;
    }
    public  void moveSouth() {
        System.out.println("S");
        //CleanSweep.
                row++;
        setMaxRow();
    }

    public  void setMaxRow() {
        if (row > maxI) maxI = row;
    }

    public  void moveEast() {
        System.out.println("E");
        //CleanSweep.
                col++;
        setMaxCol();
        
    }
    public  void setMaxCol() {
        if (col > maxJ) maxJ = col;
    }

    public  void moveWest(){
        System.out.println("W");
        //CleanSweep.
                col--;
    }

    public int getMaxI() {
        return maxI;
    }

    public int getMaxJ() {
        return maxJ;
    }
}
