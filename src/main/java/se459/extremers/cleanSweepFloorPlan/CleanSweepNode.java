package se459.extremers.cleanSweepFloorPlan;

public class CleanSweepNode  {
	int roomNumber;
	int surface;
	int dirt;
	boolean obstacle;
	
	edgeType northEdge;
	edgeType eastEdge ;
	edgeType westEdge;
	edgeType southEdge;

	public CleanSweepNode(int roomNumber, int surface, boolean obstacle, int dirt) {
		this.roomNumber= roomNumber;
		this.surface = surface;
		this.obstacle = obstacle;
		this.dirt = dirt;	
	}

	
}


	