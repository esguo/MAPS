package com.ttmaps.maps;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


class Dijkstra {

    private static List<POI> allPOI = new ArrayList<>();

    //Comparator class that compares the distance of the POI
    private Comparator<POI> comparator = new POIComparator();
    private PriorityQueue<POI> toExplore = new PriorityQueue<>(50, comparator);
    private Stack<String> stack = new Stack<>();
    private POI curr;
    private String output = "";
    private DBHandler database;

    Dijkstra(DBHandler d) {
        database = d;
    }

    String dijkstra(String start, String end) {

        int inf = Integer.MAX_VALUE;
        allPOI = database.getPOIs();

        //Initialize each POI's fields for Dijkstra
        for (POI value : allPOI) {
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
        try {
            while (curr != null && curr.getPrev() != null) {
                stack.add(curr.getName());
                stack.add(curr.getPrevEdge().getName());
                curr = curr.getPrev();
            }
            output += start + "\n";
            while (!stack.isEmpty()) {
                //noinspection StringConcatenationInLoop
                output += "--> " + stack.pop() + " -->\n" + stack.pop() + "\n";
            }
        }
        catch (OutOfMemoryError e){
            output = "Error while creating path: \n\n" + e.toString();
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
