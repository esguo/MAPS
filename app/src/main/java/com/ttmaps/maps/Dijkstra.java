package com.ttmaps.maps;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class Dijkstra {

    public static List<POI> allPOI = new ArrayList<POI>();

    //Comparator class that compares the distance of the POI
    Comparator<POI> comparator = new POIComparator();
    PriorityQueue<POI> toExplore = new PriorityQueue<POI>(50, comparator);
    Stack<String> stack = new Stack<String>();
    POI curr;
    String output = "";
    DBHandler database;

    public Dijkstra(DBHandler d) {
        database = d;
    }

    public String dijkstra(String start, String end) {

        int inf = Integer.MAX_VALUE;
        allPOI = database.getPOIs();

        //Initialize each POI's fields for Dijkstra
        for (POI value : allPOI) {
            POI temp = value;       //get the POI object
            value.setDistance(inf); // set distance to infinity
            value.setDone(false);   // shows that the node is not done yet
            value.setPrev(null);
            if (value.getName().equalsIgnoreCase(start)) {
                value.setDistance(0);
            }
            toExplore.add(value);
            Log.d("Added: ", value.getName());
        }

        //Priority Queue with initial capacity of 50 and a customized comparator


        //traverse the graph using BFS
        curr = toExplore.peek();

        while (toExplore.size() != 0) {
            Log.d("POI name: ", curr.getName());
            //top
            curr = toExplore.poll();
            if (curr.getName().equalsIgnoreCase(end)) {
                break;
            }
            List<Pair> pairs = curr.getNeighbors(); //iterate through next's getEdges
            for (int index = 0; index < pairs.size(); index++) {
                Pair currPair = pairs.get(index);
                Log.d("int: ", "" + index);
                int dist = curr.getDistance() + currPair.getEdge().getWeight();

                if (dist < currPair.getPOI().getDistance()) {
                    currPair.getPOI().setDistance(dist);
                    currPair.getPOI().setPrev(curr);
                    currPair.getPOI().setPrevEdge(currPair.getEdge());
                    toExplore.add(currPair.getPOI());
                }


            }
        }
        while (curr != null && curr.getPrev() != null) {
            stack.add(curr.getName());
            stack.add(curr.getPrevEdge().getName());
            curr = curr.getPrev();
        }
        output += start + "\n";
        while (!stack.isEmpty()) {
            output += "--> " + stack.pop() + " -->\n" + stack.pop() + "\n";
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
