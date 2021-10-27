package se459.extremers.cleanSweepFloorPlan;

import java.util.*;
import org.springframework.data.geo.Point;

public class CleanSweepNode  {
	int id;
	surfaceType surface;
	int dirt;
	boolean isClean;
	boolean obstacle;
	boolean visited;
	boolean isChargingStation;

	//for pathfinding
	List<CleanSweepNode> neighbors;
    CleanSweepNode parent;
    int f;
    int g;
    int h;
	Point pos;
    int cost;

	edgeType northEdge, eastEdge, westEdge, southEdge;
	CleanSweepNode northNode, eastNode, southNode, westNode;

	public CleanSweepNode() {
		this.northNode =  null;
		this.eastNode = null;
		this.southNode = null;
		this.westNode = null;
		this.visited = false;
		this.neighbors = new ArrayList<CleanSweepNode>();
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
		this.neighbors = new ArrayList<CleanSweepNode>();
	}

	public CleanSweepNode(CleanSweepNode node, Point position) {
		this.id= node.id;
		this.surface = node.surface;
		this.isClean = node.isClean;
			
		this.northEdge = node.northEdge;
		this.eastEdge = node.eastEdge;
		this.southEdge = node.southEdge;
		this.westEdge = node.westEdge;

		this.neighbors = new ArrayList<CleanSweepNode>();

		this.pos = position;

		this.isChargingStation = node.isChargingStation;
	}

	public void AssignNorthInternal(CleanSweepNode node) {
		this.northNode = node;
		this.neighbors.add(node);
	}

	public void AssignEastInternal(CleanSweepNode node) {
		this.eastNode = node;
		this.neighbors.add(node);
	}

	public void AssignSouthInternal(CleanSweepNode node) {
		this.southNode = node;
		this.neighbors.add(node);
	}

	public void AssignWestInternal(CleanSweepNode node) {
		this.westNode = node;
		this.neighbors.add(node);
	}
}


	