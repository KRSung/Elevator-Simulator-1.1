import java.util.*;

public class Elevator {
    static int elevatorNum = 1;
    private int mCurrentFloor = 1;
    private int mDestinationFloor;
    private int mElevatorNum;
    private Set<Integer> passengers = new HashSet<Integer>();
    private currentState mCurrentState;
    private currentDirection mCurrentDirection;
    private Building mReference;

    public Elevator(int mElevatorNum, Building reference){
        mCurrentState = currentState.IDLE_STATE;
        mCurrentDirection = currentDirection.NOT_MOVING;
        mElevatorNum = elevatorNum;
        elevatorNum++;
    }

    public int getmCurrentFloor() {
        return mCurrentFloor;
    }

    public int getmElevatorNum(){
        return mElevatorNum;
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
//    TODO: finish the switch cases
    public void tick(){
        switch (mCurrentState){
            case IDLE_STATE:
                if(passengers.size() > 0){
                    mCurrentState = currentState.ACCELERATING;
                }
//                else if(mReference.getFloor(mCurrentFloor).size() > 0){
//                    mCurrentState = currentState.DOORS_OPENING;
//                }
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
                mCurrentDirection + " - Passengers " + passengers;
    }
}
