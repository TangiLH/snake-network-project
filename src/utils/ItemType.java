package utils;


public enum ItemType {
	APPLE,BOX,SICK_BALL,INVINCIBILITY_BALL;

	public static ItemType getRandomEffect(int random) {
		if(random==1) return APPLE;
		return ItemType.values()[random];
	}

	
}