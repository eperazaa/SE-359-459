package se459.extremers.cleanSweepFloorPlan;

public class CleanSweepNode  {
	int id;
	surfaceType surface;
	int dirt;
	boolean isClean;
	boolean obstacle;
	boolean visited;
	boolean isChargingStation;
	
	edgeType northEdge, eastEdge, westEdge, southEdge;
	CleanSweepNode northNode, eastNode, southNode, westNode;

	public CleanSweepNode() {
		this.northNode =  null;
		this.eastNode = null;
		this.southNode = null;
		this.westNode = null;
		this.visited = false;
	}


	public CleanSweepNode(int id, edgeType northEdge, edgeType southEdge, edgeType eastEdge, edgeType westEdge, 
				boolean clean, surfaceType surface, boolean chargingStation) {
		this.id= id;
		this.surface = surface;
		this.isClean = clean;
			
		this.northEdge = northEdge;
		this.eastEdge = eastEdge;
		this.southEdge = southEdge;
		this.westEdge = westEdge;

		this.isChargingStation = chargingStation;
	}
	
	public CleanSweepNode(CleanSweepNode node) {
		this.id= node.id;
		this.surface = node.surface;
		this.isClean = node.isClean;
			
		this.northEdge = node.northEdge;
		this.eastEdge = node.eastEdge;
		this.southEdge = node.southEdge;
		this.westEdge = node.westEdge;

		this.isChargingStation = node.isChargingStation;

		this.northNode = null;
		this.eastNode = null;
		this.southNode=null;
		this.westNode = null;
	}
}


	