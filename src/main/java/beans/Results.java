package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

@Stateful
@SessionScoped
public class Results implements Serializable {

    private List<Hit> results;
    private long startTime; // Добавляем поле для хранения времени начала

    public Results() {
        results = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Hit> getResults() {
        return results;
    }

    public void setResults(List<Hit> results) {
        this.results = results;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Results{" +
                "results=" + results +
                '}';
    }

    public void addHit(Hit hit) {
        results.add(hit);
    }
}
