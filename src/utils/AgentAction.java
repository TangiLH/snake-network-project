package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

public enum AgentAction {
	MOVE_UP(0,-1),MOVE_DOWN(0,1),MOVE_LEFT(-1,0),MOVE_RIGHT(1,0);

	public final int x;
	public final int y;

	private AgentAction(int x, int y){
		this.x=x;
		this.y=y;
	}

	/**
	 * determine si l'action effectuée est un demi tour
	 * @param agentAction la future action
	 * @return true si c'est un demi tour, false sinon
	 */
	public boolean isReverse(AgentAction agentAction){
		return agentAction.x+this.x==0 && agentAction.y+this.y==0;
	}

	public String toJson() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(this);
			return json;
		} 
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";
	}
	
	public static AgentAction fromJson(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(AgentAction.class);
		ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			return (AgentAction)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}
	
	public String toString() {
		return this.name();
	}
}
