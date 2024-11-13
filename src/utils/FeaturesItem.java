package utils;

public class FeaturesItem {

	private ItemType itemType;
	

	private Position position;


	public FeaturesItem(int x, int y, ItemType itemType) {
		this.itemType = itemType;
		this.position=new Position(x,y);
	
	}
	
	
	public FeaturesItem clone(){
		return new FeaturesItem(this.getX(), this.getY(), this.itemType);
	}
	
	public int getX() {
		return this.position.getX();
	}


	public void setX(int x) {
		this.position.setX(x);
	}


	public int getY() {
		return this.position.getY();
	}


	public void setY(int y) {
		this.position.setY(y);
	}


	public ItemType getItemType() {
		return itemType;
	}


	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public void setPosition(Position position){
		this.position=position;
	}
	public Position getPosition(){
		return this.position;
	}


	
	
}
