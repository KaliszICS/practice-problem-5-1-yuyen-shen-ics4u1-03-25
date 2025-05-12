class RectPrism extends Rectangle { //add inhertiance to the class by using the keyword "extends"

	/*
	private int length;
	private int width;
	*/
	
	//new instance variables - aren't in Rectangle
	private int height;

	//
	public RectPrism(int length, int width, int height) {
		/*
		this.length = length;
		this.width = width;
		*/

		//super() runs the constructor of our superclass - what we inherited from
		super(length, width); //sets the length and width of RectPrism
		this.height = height;
	}

	//getters and setters - 

	/* These methods have already been inhertied

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
	    return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	*/

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
}