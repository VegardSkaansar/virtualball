package e.vegard.virtualball.Model;

public class ScoreModel {
    private String name;
    private double score;
    private double distance;
    private double seconds;

    ScoreModel(String name, int score, int distance, int seconds) {
        this.name = name;
        this.score = score;
        this.distance = distance;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public double getDistance() {
        return distance;
    }

    public double getSeconds() {
        return seconds;
    }
}
