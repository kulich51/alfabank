package ru.exercise.alfabank.model;

import java.util.Map;

public class ImagesObject {

    private String type;
    private String id;
    private Map<String, ImageProperties> images;

    public ImagesObject() {
    }

    public ImagesObject(String type, String id, Map<String, ImageProperties> images) {
        this.type = type;
        this.id = id;
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Map<String, ImageProperties> getImages() {
        return images;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImages(Map<String, ImageProperties> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ImagesObject{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", images=" + images +
                '}';
    }
}
