package utils;

public class SensHoraire implements Sens {

    private static SensHoraire cache;
    private SensHoraire(){
        //constructeur privé sur singleton
    }
    @Override
    public AgentAction nextAction(AgentAction lastAction) {
        switch (lastAction) {
            case MOVE_DOWN:
                return AgentAction.MOVE_LEFT;
            case MOVE_LEFT:
                return AgentAction.MOVE_UP;
            case MOVE_RIGHT:
                return AgentAction.MOVE_DOWN;
            case MOVE_UP:
                return AgentAction.MOVE_RIGHT;
            default:
                return AgentAction.MOVE_UP;
        }
    }

    public static SensHoraire getSensHoraire(){
        cache=cache==null?new SensHoraire():cache;
        return cache;
    }
    
}
