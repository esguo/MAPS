package com.ttmaps.maps;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class Dijkstra {

    static ArrayList<POI> allPOI = new ArrayList<POI>();

    //Comparator class that compares the distance of the POI
    Comparator<POI> comparator = new POIComparator();
    PriorityQueue<POI> toExplore = new PriorityQueue<POI> (50 ,comparator);
    Stack<POI> stack = new Stack<POI>();
    POI curr;
    String output = "";

    public String dijkstra(String start, String end) {
		int inf = Integer.MAX_VALUE;

        //Initialize each POI's fields for Dijkstra
        for (POI value : allPOI) {
            POI temp = value;       //get the POI object
            value.setDistance(inf); // set distance to infinity
            value.setDone(false);   // shows that the node is not done yet
            value.setPrev(null);
            if(value.getName().equals(start)){
                value.setDistance(0);
            }
            toExplore.add(value);
        }




        //Priority Queue with initial capacity of 50 and a customized comparator


        //traverse the graph using BFS
        while(toExplore.size() != 0){
            //top
            curr = toExplore.poll();
            if (curr.getName().equals(end)){
                break;
            }
            List<Edge> edges = curr.getEdges(); //iterate through next's getEdges
            for(int index = 0; index < edges.size(); index++ ){
                Edge currEdge = edges.get(index);
                POI otherPOI;
                if(currEdge.getPOI()[0].equals(curr.getName())) {
                    otherPOI = currEdge.getPOI()[0];
                }
                else otherPOI = currEdge.getPOI()[1];

                int dist = curr.getDistance() + currEdge.getWeight();

                if(dist < otherPOI.getDistance()) {
                    otherPOI.setDistance(dist);
                    otherPOI.setPrev(curr);
                }



            }
        }
        while (curr.getPrev() != null){
            stack.add(curr);
            curr = curr.getPrev();
        }
        while (!stack.isEmpty()){
            output += stack.pop().getName();
        }
        return output;
	}

}



class POIComparator implements Comparator<POI>
{
    @Override
    public int compare(POI x, POI y)
    {
        // Assume neither Pair is null
        if (x.getDistance() < y.getDistance())
        {
            return -1;
        }
        if (x.getDistance() > y.getDistance())
        {
            return 1;
        }
        return 0;
    }
}
