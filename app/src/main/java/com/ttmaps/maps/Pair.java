package com.ttmaps.maps;

/**
 * Created by tesia on 11/12/17.
 */

public class Pair {
    private POI poi; //first member of pair
    private Edge edge; //second member of pair

    public Pair(POI first, Edge second) {
        poi = first;
        edge = second;
    }

    public void setFirst(POI first) {
        poi = first;
    }

    public void setSecond(Edge second) {
        edge = second;
    }

    public POI getPOI() {
        return poi;
    }

    public Edge getEdge() {
        return edge;
    }
}
