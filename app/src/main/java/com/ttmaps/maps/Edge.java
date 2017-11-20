package com.ttmaps.maps;

public class Edge {
    //private final String id;
    private final String name;
    private final int weight;


    Edge(String name, int weight) {
        //this.id = id;
		this.name = name;
        this.weight = weight;


    }

    public String getName() {
     return name;
	 }

    int getWeight() {
        return weight;
    }




    //setter for poi
}
