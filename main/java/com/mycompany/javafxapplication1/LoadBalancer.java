package com.mycompany.javafxapplication1;

import java.util.List;

public class LoadBalancer {
    private List<StorageContainer> containers;
    private int currentAlgorithm = 0; // 0 = Round Robin, 1 = Least Connections, 2 = Weighted
    private int roundRobinIndex = 0;


    public LoadBalancer(List<StorageContainer> containers) {
        this.containers = containers;
    }

    public StorageContainer getNextContainer() {
        switch(currentAlgorithm) {
            case 0:
                return roundRobin();
            case 1:
                return leastConnections();
            case 2:
                return weighted();
            default:
                return roundRobin();
        }
    }

    private StorageContainer roundRobin() {
        StorageContainer container = containers.get(roundRobinIndex);
        roundRobinIndex = (roundRobinIndex + 1) % containers.size();
        return container;
    }


    private StorageContainer leastConnections() {
        StorageContainer selected = containers.get(0);
        for (StorageContainer container : containers) {
            if (container.getActiveConnections() < selected.getActiveConnections()) {
                selected = container;
            }
        }
        return selected;
    }


    private StorageContainer weighted() {
        int totalWeight = 0;
        for (StorageContainer container : containers) {
            totalWeight += container.getWeight();
        }
        
        int randomWeight = (int)(Math.random() * totalWeight);
        for (StorageContainer container : containers) {
            randomWeight -= container.getWeight();
            if (randomWeight < 0) {
                return container;
            }
        }
        return containers.get(0);
    }


    public void setAlgorithm(int algorithm) {
        this.currentAlgorithm = algorithm;
    }
}
