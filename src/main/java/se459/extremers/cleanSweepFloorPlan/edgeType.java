package se459.extremers.cleanSweepFloorPlan;

public enum edgeType {
	UNKNOWN, 
    OPEN,
    OBSTACLE,
    STAIRS,
    DOOR;

    public static edgeType getEnumValue(String string) {
        edgeType result;
        switch (string) {
            case "OPEN":
                result  = edgeType.OPEN;
                break;
            
            case "OBSTACLE":
                result  = edgeType.OBSTACLE;
                break;
            case "STAIRS":
                result  = edgeType.STAIRS;
                break;
            case "DOOR":
                result  = edgeType.DOOR;
                break;
            default:
             result  = edgeType.UNKNOWN;
             break;
        }
        
        return result;
    }
}
