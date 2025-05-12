class Rectangle {
	//instance variables
	private int length;
	private int width;

	//constructor - THIS DOES NOT GET INHERITED
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}


	//Methods - usually setters and getters
	public int getWidth() {
		return this.width;
	}

	public int getLength() {
		return this.length;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setLength(int length) {
		this.length = length;
	}
}