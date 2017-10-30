package com.ttmaps.maps;
import java.util.List;

public class Edge {
    //private final String id;
    private final String name;
    private final int weight;
    private POI POI1;
    private POI POI2;
    private POI[] POI; //a list of point of interest this edge is
                                  //connected to

    public Edge(String name, int weight, POI a, POI b) {
        //this.id = id;
		this.name = name;
        this.weight = weight;
        POI1 = a;
        POI2 = b;

    }

    public String getName() {
     return name;
	 }

    public int getWeight() {
        return weight;
    }


    //getter for getting the List of Point of Interest this edge is connected to
    public POI[] getPOI() {
        POI = new POI[2];
        POI[0] = POI1;
        POI[1] = POI2;


        return POI;
	}

    //setter for poi
}
