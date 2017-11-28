package com.ttmaps.maps;

/** Links POI to another POI
 * Each POI will have a list of pairs of <POI, edge>
 */
class Pair {
    private POI poi; //first member of pair
    private Edge edge; //second member of pair
    private boolean lit;
    private boolean safeBox;
    private boolean stairs;

    Pair(POI first, Edge second) {
        poi = first;
        edge = second;

    }


    POI getPOI() {
        return poi;
    }

    boolean isWellLit(){
        return lit;
    }

    boolean hasSafeBox(){
        return safeBox;
    }

    boolean noStairs(){
        return !stairs;
    }

    Edge getEdge() {
        return edge;
    }
}
