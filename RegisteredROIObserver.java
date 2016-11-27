package a7;

public interface RegisteredROIObserver extends ROIObserver {

	public Region getROI();
	public ROIObserver unwrap();
}
