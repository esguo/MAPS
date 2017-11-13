package com.ttmaps.maps;
import java.util.List;

public class Edge {
    //private final String id;
    private final String name;
    private final int weight;


    public Edge(String name, int weight) {
        //this.id = id;
		this.name = name;
        this.weight = weight;


    }

    public String getName() {
     return name;
	 }

    public int getWeight() {
        return weight;
    }




    //setter for poi
}
