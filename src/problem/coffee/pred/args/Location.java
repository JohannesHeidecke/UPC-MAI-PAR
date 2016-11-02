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
	
	public int getX() {
		return ((Coordinate) this.value).getX();
	}
	
	public int getY() {
		return ((Coordinate) this.value).getY();
	}
	
	@Override 
	public String toString() {
		if (this.value != null) {
			Coordinate c = (Coordinate) this.value;
			return "["+c.getX()+","+c.getY()+"]";
		} else {
			return "_";
		}
		
	}

}
