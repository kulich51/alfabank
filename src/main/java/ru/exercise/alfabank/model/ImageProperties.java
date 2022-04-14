package ru.exercise.alfabank.model;

public class ImageProperties {

    private String height;
    private String width;
    private String size;
    private String url;

    public ImageProperties() {
    }

    public ImageProperties(String height, String width, String size, String url) {
        this.height = height;
        this.width = width;
        this.size = size;
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageProperties{" +
                "height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", size='" + size + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
