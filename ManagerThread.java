import java.net.*;
import java.awt.*;
import HashMap.MyHashMap;

import java.io.*;

public class ManagerThread implements Runnable {
    private Manager manager;
    private MyHashMap<Thread, ServerThread> threadList;
    private boolean running = true;
    private MyHashMap<String, Pair<Vector, Integer[]>> clients;// [Name], {Vector, [Xpos, Ypos, up, down, left, right, mouseState, mouseX, mouseY]}
    private MyHashMap<String, Integer[]> sendData;
    private Map map;
    private Rectangle[] walls;
    private int pWidth, pHeight;

    public ManagerThread(Manager manager) {
        this.manager = manager;
        map = manager.getMap();
        walls = map.getWalls();
        pWidth = 10;// player width
        pHeight = 50;// player height
    }

    public void run() {
        while (running) {
            // each player
            for (String each : clients.keySet()) {
                Pair<Vector, Integer[]> pair = clients.get(each);
                Vector v = pair.getKey();
                Integer[] nums = pair.getValue();
                // check if touching hitbox
                for (Rectangle r : walls) {
                    // if touching side, xDirection = 0, x pos subtract or add
                    int pX = nums[0];
                    
                }

                v.setYDirection(v.getYDirection() + .1);
                System.out.println("Gravitating " + each + " " + v.getYDirection());
                pair.getValue()[0] += (int) v.getXDirection();
            }
            // send out all information
            manager.broadcast(new Pair<String, Object>("GameData", clients), Thread.currentThread());
            try {
                Thread.sleep(15);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setThreads(MyHashMap<Thread, ServerThread> threadList) {
        this.threadList = threadList;
        for (Thread each : threadList.keySet()) {// setup clients (hashmap)
            clients.put(each.getName(), new Pair<Vector, Integer[]>(new Vector(0, 0), new Integer[] {50, 0, 0, 0, 0, 0, 0, 0, 0 }));
        }
    }
    public void updateThread( int[] keys, Thread thread){
        for(int i=0; i<keys.length; i++){
            clients.get(thread.getName()).getValue()[i+2] = keys[i];
        }
    }
}