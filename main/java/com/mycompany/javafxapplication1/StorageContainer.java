package com.mycompany.javafxapplication1;

import java.util.HashMap;
import java.util.Map;

public class StorageContainer {
    private String containerId;
    private Map<String, byte[]> files;
    private int activeConnections;
    private int weight = 1; // Default weight


    public StorageContainer(String containerId) {
        this.containerId = containerId;
        this.files = new HashMap<>();
        this.activeConnections = 0;
    }

    public StorageContainer(String containerId, int weight) {
        this.containerId = containerId;
        this.files = new HashMap<>();
        this.activeConnections = 0;
        this.weight = weight;
    }


    public void storeFile(String filename, byte[] fileData) {
        files.put(filename, fileData);
        activeConnections++;
    }

    public byte[] retrieveFile(String filename) {
        activeConnections++;
        return files.get(filename);
    }

    public boolean deleteFile(String filename) {
        if (files.containsKey(filename)) {
            files.remove(filename);
            return true;
        }
        return false;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public String getContainerId() {
        return containerId;
    }

    public int getFileCount() {
        return files.size();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
