package se459.extremers.simulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellIndex {
    int row;
    int col;


    @Override
public String toString () {
        return "["+ this.row + "," + this.col + "]";
}



@Override
    public boolean equals(Object obj)
    {
        return ((CellIndex)obj).row == this.row && ((CellIndex)obj).col == this.col;    
        }


}

