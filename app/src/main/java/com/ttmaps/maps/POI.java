package com.ttmaps.maps;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class POI {


	final private String id;
	final private String name;
  	private boolean done;  //has it been explored?
  	private int distance;
  	private POI prev;
  	private Edge prevEdge;
	private List<Edge> edges; // a list of all the edges names this POI is connected to


	public POI (String name) {
	    this.id = "UCSD";
		this.name = name;
        this.done = false;
        this.distance = 0;
        this.prev = null;
        this.prevEdge = null;
        edges = new ArrayList<Edge>();
	}

	public String getId() {
		return id;
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

	public List<Edge> getEdges(){
		return edges;
	}

	public void setEdges(Edge e){
	    edges.add(e);
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
