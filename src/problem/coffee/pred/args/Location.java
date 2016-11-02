package problem.coffee.pred.args;

import model.Variable;

public class Location extends Variable {

	public Location(Coordinate value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Location) {
			Location l = (Location) o;
			
			if (this.value == null || l.value == null) {
				return this.value == l.value;
			}
			
			if(this.value.equals(l.value)) {
				return true;
			}
			
		}
		return false;
	}
	
	@Override 
	public String toString() {
		Coordinate c = (Coordinate) this.value;
		return "["+c.getX()+","+c.getY()+"]";
	}

}
