package com.ttmaps.maps;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.ArrayList;

public class POI {


	private int id;
	private String name;
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
	private String file_name;
	private LatLng latLng;

	public POI(){
	}

	POI(int id, String name) {
	    this.id = id;
		this.name = name;
        this.distance = 0;
        this.prev = null;
        this.prevEdge = null;
        pairList = new ArrayList<>();
        this.isBathroom = false;
        this.isFood = false;
		this.isInfoCenter = false;
		this.isClassroom = false;
		this.isAdmin = false;
		this.isResHall = false;
		this.isRec = false;
		this.isParking = false;
		this.isStudyArea = false;
		this.file_name = "";
        //poiList.add(new POI("PC")); 		// Need to figure out how to update list from database
        //poiList.add(new POI("Sixth"));
	}


	POI(int id, String name, String file_name) {
		this(id, name);
		this.file_name = file_name;

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

    int getDistance()
    {
    	return distance;
    }

    void setDistance(int distance){
        this.distance = distance;
    }

    POI getPrev(){
        return prev;
    }

    void setPrev(POI prev){
        this.prev = prev;
    }

    Edge getPrevEdge(){
        return prevEdge;
    }

    void setPrevEdge(Edge e){
        prevEdge = e;
    }

    void addNeighbor(POI p, Edge e) { pairList.add(new Pair(p, e)); }

    List<Pair> getNeighbors() { return pairList; }

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

	public String getFileName(){
		return file_name;
	}
	public void setLatLng(double lat, double lng){
		latLng = new LatLng(lat, lng);
	}
	public LatLng getLatLng(){
		return latLng;
	}

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
