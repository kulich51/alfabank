package ru.exercise.alphabank.model;

import java.util.List;

public class GifObject {

    private List<ImagesObject> data;

    public GifObject() {
    }

    public GifObject(List<ImagesObject> data) {
        this.data = data;
    }


    public List<ImagesObject> getData() {
        return data;
    }

    public void setData(List<ImagesObject> data) {
        this.data = data;
    }

    public ImagesObject getRandomGif() {
        return data.get(getRandomNumber());
    }

    private int getRandomNumber() {
        int min = 0;
        int max = data.size() - 1;
        int randomNumber = min + (int) (Math.random() * max);
        return randomNumber;
    }

    @Override
    public String toString() {
        return "GifObject{" +
                "data=" + data +
                '}';
    }
}
