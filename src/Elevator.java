import java.util.*;

public class Elevator {
    static int elevatorNum = 0;
    private int mCurrentFloor;
    private int mDestinationFloor;
    private int mElevatorNum;
    private Set<Integer> passengers = new HashSet<Integer>();
    private currentState mCurrentState;
    private currentDirection mCurrentDirection;

    public Elevator(int mElevatorNum, String a){
        mCurrentState = currentState.IDLE_STATE;
        mCurrentDirection = currentDirection.NOT_MOVING;
        elevatorNum++;
    }

    public int getmCurrentFloor() {
        return mCurrentFloor;
    }

    enum currentDirection {
        UP,
        DOWN,
        NOT_MOVING
    }

    enum currentState{
        IDLE_STATE,
        DOORS_OPENING,
        UNLOADING_PASSENGERS,
        LOADING_PASSENGERS,
        DOORS_CLOSING,
        ACCELERATING,
        MOVING,
        DECELERATING
    }

    // updates the current state of the elevator
    public void tick(){
        switch (mCurrentState){
            case IDLE_STATE:
                mCurrentState = currentState.ACCELERATING;
                break;
            case DOORS_OPENING:
                mCurrentState = currentState.UNLOADING_PASSENGERS;
                break;
            case UNLOADING_PASSENGERS:
                mCurrentState = currentState.ACCELERATING;
                mCurrentDirection = currentDirection.NOT_MOVING;
                break;
            case DECELERATING:
                mCurrentState = currentState.DOORS_OPENING;
                break;
            default:
                break;
        }
    }

    // represents the overall state of the elevator
    public String toString(){
        return "Elevator " + mElevatorNum + " - Floor " + mCurrentFloor + " - " + mCurrentState + " - " +
                mCurrentDirection + " - Passengers ";
    }
}
