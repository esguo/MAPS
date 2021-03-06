package com.ttmaps.maps;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

import static com.ttmaps.maps.MainActivity.POIList;


class Dijkstra {

    //Comparator class that compares the distance of the POI
    private final Comparator<POI> comparator = new POIComparator();
    private final PriorityQueue<POI> queue = new PriorityQueue<>(50, comparator);
    private final Stack<String> stack = new Stack<>();
    private String output = "";
    private ArrayList<String> o;


    ArrayList<String> dijkstra(String start, String end, boolean[] opt) {
        /* opt
            0 - safe
            1 - well lit
            2 - wheelchair
         */

        o = new ArrayList<String>();
        int inf = Integer.MAX_VALUE;

        //Initialize each POI's fields for Dijkstra
        for (POI value : POIList.values()) {
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

                //skip this edge if does not match options selected
                if(opt[0] && !(currPair.isWellLit() || currPair.hasSafeBox()))
                    continue;
                if(opt[1] && !currPair.isWellLit())
                    continue;
                if(opt[2] && !currPair.noStairs())
                    continue;

                //Log.d("int: ", "" + index);
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
                o.add(start);
                if(stack.size() > 50 || stack.size() == 0) {
                    output = "Sorry, no path could be found.";
                    o.add(output);
                    return o;
                }
            }
            output += start + "\n";
            while (!stack.isEmpty()) {
                String path = stack.pop();
                String poi = stack.pop();
                output += "--> " + path + " -->\n" + poi + "\n";
                o.add(poi);
            }
        }
        catch (OutOfMemoryError e){
            output = "Error while creating path: \n\n" + e.toString();
        }
        o.add(output); //return output;
        return o;
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
