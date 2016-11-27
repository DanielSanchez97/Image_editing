package a7;


public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}	
	
	public Pixel blend(Pixel p, double weight){	
		return new ColorPixel ((this.getRed()*weight + p.getRed()*(weight-1)),
							   (this.getGreen()*weight + p.getGreen()*(weight-1)),
							   (this.getBlue()*weight + p.getBlue()*(weight-1)));
	}
	
	public Pixel lighten(double factor){
	    return this.blend(new ColorPixel(1,1,1), (1-factor));
	}
	
	public Pixel darken(double factor){
	    return this.blend(new ColorPixel(0,0,0), (1-factor));
	}
}
