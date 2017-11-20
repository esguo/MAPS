package com.ttmaps.maps;
import android.util.Log;

import java.util.List;
import java.lang.Integer;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


class Dijkstra {

    //Comparator class that compares the distance of the POI
    private final Comparator<POI> comparator = new POIComparator();
    private final PriorityQueue<POI> queue = new PriorityQueue<>(50, comparator);
    private final Stack<String> stack = new Stack<>();
    private final DBHandler database;
    private String output = "";

    Dijkstra(DBHandler d) {
        database = d;
    }

    String dijkstra(String start, String end) {

        int inf = Integer.MAX_VALUE;
        List<POI> allPOI = database.getPOIs();

        //Initialize each POI's fields for Dijkstra
        for (POI value : allPOI) {
            value.setDistance(inf); // set distance to infinity
            value.setPrev(null);
            if (value.getName().equalsIgnoreCase(start)) {
                value.setDistance(0);
            }
            queue.add(value);
            Log.d("Added: ", value.getName());
        }

        //Priority Queue with initial capacity of 50 and a customized comparator


        //traverse the graph using BFS
        POI curr = queue.peek();

        while (queue.size() != 0) {
            Log.d("POI name: ", curr.getName());
            //top
            curr = queue.poll();
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
                    queue.add(currPair.getPOI());
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
