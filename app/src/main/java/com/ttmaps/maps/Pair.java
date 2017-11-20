package com.ttmaps.maps;

/** Links POI to another POI
 * Each POI will have a list of pairs of <POI, edge>
 */
class Pair {
    private POI poi; //first member of pair
    private Edge edge; //second member of pair

    Pair(POI first, Edge second) {
        poi = first;
        edge = second;
    }


// --Commented out by Inspection START (11/19/2017 9:15 PM):
//    public void setFirst(POI first) {
//        poi = first;
//    }
// --Commented out by Inspection STOP (11/19/2017 9:15 PM)

// --Commented out by Inspection START (11/19/2017 9:15 PM):
//    public void setSecond(Edge second) {
//        edge = second;
//    }
// --Commented out by Inspection STOP (11/19/2017 9:15 PM)

    POI getPOI() {
        return poi;
    }

    Edge getEdge() {
        return edge;
    }
}
