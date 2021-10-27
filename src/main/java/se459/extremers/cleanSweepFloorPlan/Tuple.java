package se459.extremers.cleanSweepFloorPlan;

public class Tuple {
    public final CleanSweepNode node;
    public final NavigationOptionsEnum dir;
    public Tuple(CleanSweepNode node, NavigationOptionsEnum dir) {
        this.node = node;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object tmp) {
        return (this.node.id == ((Tuple)tmp).node.id && this.dir == ((Tuple)tmp).dir);
    }
    
}
