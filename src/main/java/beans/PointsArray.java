package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

@Stateful
@SessionScoped
public class PointsArray implements Serializable {

    private List<Point> points;

    public PointsArray() {
        this.points = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }
}
