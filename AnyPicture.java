package a7;

abstract public class AnyPicture implements Picture {

	abstract public int getWidth();
	abstract public int getHeight();

	abstract public Pixel getPixel(int x, int y);
	
	@Override
	public Pixel getPixel(Coordinate c) {
		if (c == null) {
			throw new IllegalArgumentException("Coordinate is null");
		}
		return this.getPixel(c.getX(), c.getY());
	}

	abstract public void setPixel(int x, int y, Pixel p);
	
	@Override
	public void setPixel(Coordinate c, Pixel p) {
		if (c == null) {
			throw new IllegalArgumentException("Coordinate is null");
		}
		this.setPixel(c.getX(), c.getY(), p);
	}
	
	@Override
	public SubPicture extract(int xoff, int yoff, int width, int height) {
		return new SubPictureImpl(this, xoff, yoff, width, height);
	}

	@Override 
	public SubPicture extract(Coordinate corner_a, Coordinate corner_b) {
		if (corner_a == null || corner_b == null) {
			throw new IllegalArgumentException("One or both coordinates is null");
		}
		
		int min_x = corner_a.getX() < corner_b.getX() ? corner_a.getX() : corner_b.getX();
		int min_y = corner_a.getY() < corner_b.getY() ? corner_a.getY() : corner_b.getY();
		int max_x = corner_a.getX() > corner_b.getX() ? corner_a.getX() : corner_b.getX();
		int max_y = corner_a.getY() > corner_b.getY() ? corner_a.getY() : corner_b.getY();
		
		return extract(min_x, min_y, (max_x-min_x)+1, (max_y-min_y)+1);
	}
	
	@Override
	public SubPicture extract(Region r) {
		if (r == null) {
			throw new IllegalArgumentException("Region argument is null");
		}
		return extract(r.getUpperLeft(), r.getLowerRight());
	}
	
	@Override
	public ObservablePicture createObservable() {
		return new ObservablePictureImpl(this);
	}
	
	public Picture pictureclone(){
		//I use this method to clone pictures in this assignment. 
		//I added it to the picture Interface so that i could use it with anything that has an is-a
		//relationship with picture. 
		Picture clone = new PictureImpl(this.getWidth(), this.getHeight());
		int i,a; double red,green,blue;
		
		for(i=0; i< clone.getWidth(); i++){
			for(a=0; a<clone.getHeight(); a++){
				red = this.getPixel(i, a).getRed();
				green = this.getPixel(i, a).getGreen();
				blue = this.getPixel(i, a).getBlue();
				clone.setPixel(i,a, new ColorPixel(red,green,blue));
			}
		}
			
		
		
		return clone;
	}
	
}
