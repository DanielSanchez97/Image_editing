package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorWidget extends JPanel implements MouseListener {

	private PictureView picture_view; 
	private Picture original_picture;
	private int pixel_xvalue, pixel_yvalue;
	private double pixel_rvalue, pixel_gvalue, pixel_bvalue, pixel_brightvalue;
	private JLabel Jlab_information;
	
	public PixelInspectorWidget(Picture picture) {
		pixel_xvalue =0; pixel_yvalue =0; pixel_rvalue=0; pixel_gvalue =0; pixel_bvalue=0; pixel_brightvalue=0;
		original_picture = picture;
		setLayout(new BorderLayout());
		
		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);
		
		add(picture_view, BorderLayout.CENTER);
		
		String information = "x:" + pixel_xvalue + " y:" + pixel_yvalue + " Red:" + pixel_rvalue+
							" Green:" + pixel_gvalue + " Blue:"+ pixel_bvalue + " Brightness:"+ pixel_brightvalue;
		
		 
		Jlab_information = new JLabel (information);
		
		add(Jlab_information, BorderLayout.NORTH);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		pixel_xvalue = e.getX();
		pixel_yvalue = e.getY();
		pixel_rvalue = roundTwoDecimals(original_picture.getPixel(pixel_xvalue, pixel_yvalue).getRed());
		pixel_gvalue = roundTwoDecimals(original_picture.getPixel(pixel_xvalue, pixel_yvalue).getGreen());
		pixel_bvalue = roundTwoDecimals(original_picture.getPixel(pixel_xvalue, pixel_yvalue).getBlue());
		pixel_brightvalue = roundTwoDecimals(original_picture.getPixel(pixel_xvalue, pixel_yvalue).getIntensity());
		
		
		
		Jlab_information.setText("x:" + pixel_xvalue + " y:" + pixel_yvalue + " Red:" + pixel_rvalue+
				" Green:" + pixel_gvalue + " Blue:"+ pixel_bvalue + " Brightness:"+ pixel_brightvalue);
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public double roundTwoDecimals(double num){
		num *= 100;
		num = Math.floor(num);
		num = num/100;
		
		return num;
		
		
		
	}

}
