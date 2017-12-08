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

    Pair(POI first, Edge second, boolean [] data) {
        poi = first;
        edge = second;
        lit = data[0];
        safeBox = data[1];
        stairs = !data[2];
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
