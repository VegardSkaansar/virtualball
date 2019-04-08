package e.vegard.virtualball.Fragment;

public class ScoreModel {
    private String name;
    private double score;
    private double distance;
    private double seconds;

    ScoreModel(String name, double score, double distance, double seconds) {
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
