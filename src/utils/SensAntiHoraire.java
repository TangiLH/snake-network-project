package utils;

public class SensAntiHoraire implements Sens {
    private static SensAntiHoraire cache;
    private SensAntiHoraire(){

    }
    @Override
    public AgentAction nextAction(AgentAction lastAction) {
        switch (lastAction) {
            case MOVE_DOWN:
                return AgentAction.MOVE_RIGHT;
            case MOVE_LEFT:
                return AgentAction.MOVE_DOWN;
            case MOVE_RIGHT:
                return AgentAction.MOVE_UP;
            case MOVE_UP:
                return AgentAction.MOVE_LEFT;
            default:
                return AgentAction.MOVE_UP;
        }
    }

    public SensAntiHoraire getSensAntiHoraire(){
        cache=cache==null?new SensAntiHoraire():cache;
        return cache;
    }

}
