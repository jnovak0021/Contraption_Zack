import java.time.*;


public class Clock {

    private static Instant startTime;

    public Clock() {
        startTime = Instant.now();
    }

    public Duration getElapsedTime() {
        return Duration.between(startTime, Instant.now());
    }
}