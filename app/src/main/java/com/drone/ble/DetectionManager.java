package com.drone.ble;

import java.util.ArrayList;
import java.util.List;

public class DetectionManager {
    private static DetectionManager instance;
    private final List<DetectionData> detections = new ArrayList<>();

    private DetectionManager() {}

    public static synchronized DetectionManager getInstance() {
        if (instance == null) {
            instance = new DetectionManager();
        }
        return instance;
    }

    public void addDetection(String type, int id, double lat, double lon) {
        // Check if this detection already exists (by ID) to prevent overwriting or duplicate entries if desired.
        // For now, the requirement is just "once the pin is set it doesn't erase".
        // Since we append to a list, we never erase unless clear() is called.
        
        // OPTIONAL: Prevent duplicates of the same ID
        for (DetectionData d : detections) {
            if (d.id == id && d.type.equals(type)) {
                // Already exists, don't add again or maybe update? 
                // Requirement says "doesn't erase", implying persistence.
                // If we receive the same detection again, we can ignore it or update it.
                // Let's ignore duplicates to keep the map clean.
                return;
            }
        }
        detections.add(new DetectionData(type, id, lat, lon));
    }

    public List<DetectionData> getDetections() {
        return new ArrayList<>(detections);
    }

    public void clear() {
        detections.clear();
    }

    public static class DetectionData {
        public final String type;
        public final int id;
        public final double lat;
        public final double lon;

        public DetectionData(String type, int id, double lat, double lon) {
            this.type = type;
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }
    }
}

