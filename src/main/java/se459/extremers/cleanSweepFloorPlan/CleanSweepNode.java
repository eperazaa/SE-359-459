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
				boolean clean, surfaceType surface, boolean chargingStation, int dirt_level) {
		this.id= id;
		this.surface = surface;
		this.isClean = clean;
			
		this.northEdge = northEdge;
		this.eastEdge = eastEdge;
		this.southEdge = southEdge;
		this.westEdge = westEdge;

		this.isChargingStation = chargingStation;
		this.dirt = dirt_level;
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
		this.dirt = node.dirt;

		this.northNode = null;
		this.eastNode = null;
		this.southNode=null;
		this.westNode = null;
	}


    public boolean decreaseDirt() {
		if (this.dirt > 0){
			this.dirt--;
			if (this.dirt == 0 ) this.isClean = true; 
		}
		return isClean();
    }

	public boolean isClean(){
		return this.isClean;
	}
}


	