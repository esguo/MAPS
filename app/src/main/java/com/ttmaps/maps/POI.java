package com.ttmaps.maps;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class POI {


	private int id;
	private String name;
  	private boolean done;  //has it been explored?
  	private int distance;
  	private POI prev;
  	private Edge prevEdge;
  	private List<Pair> pairList;

  	// Filters
	private boolean isBathroom;
	private boolean isFood;
	private boolean isInfoCenter;	// Information Center eg. Student Services
	private boolean isClassroom;
	private boolean isAdmin;		// administrative building eg. APM
	private boolean isResHall;
	private boolean isRec;			// recreational area eg. RIMAC
	private boolean isParking;
	private boolean isStudyArea;

	public POI(){

	}

	public POI (int id, String name) {
	    this.id = id;
		this.name = name;
        this.done = false;
        this.distance = 0;
        this.prev = null;
        this.prevEdge = null;
        pairList = new ArrayList<Pair>();
        this.isBathroom = false;
        this.isFood = false;
		this.isInfoCenter = false;
		this.isClassroom = false;
		this.isAdmin = false;
		this.isResHall = false;
		this.isRec = false;
		this.isParking = false;
		this.isStudyArea = false;
        //poiList.add(new POI("PC")); 		// Need to figure out how to update list from database
        //poiList.add(new POI("Sixth"));
	}

	public int getId() {
		return id;
	}
	public void setId(int newId) {
		this.id = newId;
	}

	public String getName() {
		return name;
	}

    public boolean getDone(){
		return done;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public int getDistance()
    {
    	return distance;
    }

    public void setDistance (int distance){
        this.distance = distance;
    }

    public POI getPrev(){
        return prev;
    }

    public void setPrev (POI prev){
        this.prev = prev;
    }

    public Edge getPrevEdge(){
        return prevEdge;
    }

    public void setPrevEdge(Edge e){
        prevEdge = e;
    }

    public void addNeighbor(POI p, Edge e) { pairList.add(new Pair(p, e)); }

    public List<Pair> getNeighbors() { return pairList; }

    public void setIsBathroom() { isBathroom = true; }
    public boolean getIsBathroom() { return isBathroom; }

	public void setIsFood(){ isFood = true; }
	public boolean getIsFood(){ return isFood; }

	public void setIsInfoCenter(){ isInfoCenter = true; }
	public boolean getIsInfoCenter(){ return isInfoCenter; }

	public void setIsClassroom(){ isClassroom = true; }
	public boolean getIsClassroom(){ return isClassroom; }

	public void setIsAdmin(){ isAdmin = true; }
	public boolean getIsAdmin(){ return isAdmin; }

	public void setIsResHall(){ isResHall = true; }
	public boolean getIsResHall(){ return isResHall; }

	public void setIsRec(){ isRec = true; }
	public boolean getIsRec(){ return isRec; }

	public void setIsParking(){ isParking = true; }
	public boolean getIsParking(){ return isParking; }

	public void setIsStudyArea(){ isStudyArea= true; }
	public boolean getIsStudyArea(){ return isStudyArea; }
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		POI other = (POI) obj;
		if (id == 0) {
			if (other.id != 0)
				return false;
		} else if (id != (other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
