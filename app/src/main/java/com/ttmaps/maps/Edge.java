package com.ttmaps.maps;
import java.util.List;

public class Edge {
    //private final String id;
    private final String name;
    private final int weight;
    private POI[] POI; //a list of point of interest this edge is
                                  //connected to

    public Edge(String name, int weight) {
        //this.id = id;
		this.name = name;
        this.weight = weight;
        POI = new POI[2];
    }

    public String getName() {
     return name;
	 }

    public int getWeight() {
        return weight;
    }


    //getter for getting the List of Point of Interest this edge is connected to
    public POI[] getPOI() {
        return POI;
	}

    //setter for poi

    public void setPointofInterest(POI[] newPoi) {
		    POI = newPoi;
	}
}
