import java.time.*;

//updated in animation handler
public class Clock {

    private static long startTime;
    public static double elapsedTime;

    public Clock(long startTime, double elapsedTime) {
        this.startTime = startTime;
        this.elapsedTime = elapsedTime;
    }

    public Clock(){}

    public static double getElapsedTime() {
        return elapsedTime;
    }

    public static void setElapsedTime(double elapsedTime){
        Clock.elapsedTime = elapsedTime;
    }

    // Static getter method for startTime
    public static long getStartTime() {
        return startTime;
    }

    // Static setter method for startTime
    public static void setElapsedTime(long startTime) {
        Clock.startTime = startTime;
    }
}