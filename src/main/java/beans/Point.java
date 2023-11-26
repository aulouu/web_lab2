package beans;

import java.io.Serializable;

public class Point implements Serializable {
    private double x;
    private double y;
    private double radius;
    private boolean status;
    private String time;
    private long scriptTime;

    public Point() {

    }

    public Point(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public boolean getStatus(){ return status; }
    public void setStatus(boolean status){ this.status = status; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public long getScriptTime() { return scriptTime; }

    public void setScriptTime(long scriptTime) { this.scriptTime = scriptTime; }

}
