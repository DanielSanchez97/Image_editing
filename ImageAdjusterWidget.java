package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterWidget extends JPanel implements ChangeListener{
	private PictureView picture_view; private JPanel sliders; List<ChangeListener> change_listeners;
	private JSlider blur, saturation, brightness;
	private static Picture org_pic;
	
	
	
	public ImageAdjusterWidget(Picture p){
	
		setLayout(new BorderLayout());
	
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(6,0));
		
		JLabel blur_label = new JLabel("Blur");
		JLabel saturation_label = new JLabel("Saturation");
		JLabel brightness_label = new JLabel("Brightness");
		 blur = new JSlider(JSlider.HORIZONTAL, 0,5,0 );
		saturation = new JSlider(JSlider.HORIZONTAL, -100, 100,0);
		brightness = new JSlider(JSlider.HORIZONTAL, -100,100,0);
		
		blur.setMajorTickSpacing(1);
		blur.setPaintTicks(true);
		blur.setPaintLabels(true);
		blur.setSnapToTicks(true);
		
		saturation.setMajorTickSpacing(25);
		saturation.setPaintTicks(true);
		saturation.setPaintLabels(true);
		
		brightness.setMajorTickSpacing(25);
		brightness.setPaintTicks(true);
		brightness.setPaintLabels(true);
			
		inner.add(blur_label);
		inner.add(blur);
		inner.add(saturation_label);
		inner.add(saturation);
		inner.add(brightness_label);
		inner.add(brightness);
		picture_view = new PictureView(p.createObservable());
		org_pic = picture_view.getPicture().pictureclone();
		
		blur.addChangeListener(this);
		saturation.addChangeListener(this);
		brightness.addChangeListener(this);
		
		
		add(picture_view, BorderLayout.CENTER);
		add(inner, BorderLayout.SOUTH);
		
		
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		Picture brightness_clone = org_pic.pictureclone();
		brightness_clone = adjustBrightness(brightness.getValue(), org_pic);
		
		Picture blur_clone = brightness_clone.pictureclone();
		blur_clone = blur(blur.getValue(), brightness_clone);
		
		Picture saturation_clone = blur_clone.pictureclone();
		saturation_clone = saturation(saturation.getValue(), saturation_clone);
	
		
		
		picture_view.setPicture(saturation_clone.createObservable());
	
	}
	
	public void addChangeListener(ChangeListener l) {
		change_listeners.add(l);
	}

	public void removeChangeListener(ChangeListener l) {
		change_listeners.remove(l);
	}
	
	protected Picture adjustBrightness(int adjustment, Picture p){
		int i; int a; 
		double new_adjustment = (double)adjustment/100; 
		double adjRed=0; double adjGreen =0; double adjBlue=0;
		
		
		Pixel hold;
		
		Picture changed_picture = p.pictureclone(); //pictureclone() is a method i added to the AnyPicture class
													// and added to picture interface.
		
		for(i=0; i<p.getWidth(); i++){
			for(a=0; a<p.getHeight(); a++){
				hold = p.getPixel(i, a);
				
				adjRed = inPixelRange(hold.getRed() + new_adjustment);
				adjGreen = inPixelRange(hold.getGreen() +  new_adjustment);
				adjBlue = inPixelRange(hold.getBlue()+  new_adjustment);
				
				
				changed_picture.setPixel(i, a, new ColorPixel(adjRed, adjGreen, adjBlue));
			}
		}
		
	
		return changed_picture;
	}
	
	public Picture blur(int adjustment, Picture p){
		Picture blurred_pic = p.pictureclone();
		int i,a,b,c;
		double num_pixels=0; double red_total=0; double green_total=0; double blue_total=0;
		
		if(adjustment == 0){
			return blurred_pic;
		}
		
		for(i=0; i<p.getWidth(); i++ ){
			for(a=0; a<p.getHeight(); a++){
				
				for(b= i-adjustment; b < i+adjustment; b++){
					if(b<0 || b>(p.getWidth()-1))
						continue;
					for(c=a-adjustment; c<a+adjustment; c++){
						if( c<0 || c>(p.getHeight()-1))
							continue;
						
						if(b==i && c==a)
							continue;
						
						num_pixels++;
						red_total = red_total + p.getPixel(b, c).getRed();
						green_total = green_total + p.getPixel(b, c).getGreen();
						blue_total = blue_total + p.getPixel(b, c).getBlue();
						
					}
				}
				
				red_total = red_total / num_pixels;
				green_total = green_total/ num_pixels;
				blue_total = blue_total / num_pixels;
				blurred_pic.setPixel(i, a, new ColorPixel(red_total, green_total, blue_total));
				red_total=0; green_total=0; blue_total=0; num_pixels=0;
			}
		}
		
		
		
		return blurred_pic;
	}
	
	public Picture saturation(double adjustment, Picture p){
		Picture saturation_pic = p.pictureclone();
		int i,a;
		double new_red, new_green, new_blue, calculated_change, largest_value;
		
		if(adjustment == 0){
			return saturation_pic;
		}
		
		for(i=0; i<p.getWidth(); i++){
			for(a=0; a<p.getHeight(); a++){
				if(adjustment < 0){
					calculated_change = (1.0 + (adjustment / 100.0) ) - 
							  			(p.getPixel(i, a).getIntensity() * adjustment / 100.0);
					new_red = p.getPixel(i, a).getRed() * calculated_change; 
					new_green = p.getPixel(i, a).getGreen() *calculated_change;
					new_blue = p.getPixel(i, a).getBlue() *calculated_change;
					
					saturation_pic.setPixel(i, a, new ColorPixel(new_red, new_green, new_blue));
				}
				
				if (adjustment >0){
					largest_value = Math.max(p.getPixel(i, a).getRed(), 
										Math.max(p.getPixel(i, a).getGreen(),p.getPixel(i, a).getBlue()));
					
					if(largest_value ==0){
						continue;
					}
					
					calculated_change =((largest_value + ((1.0 - largest_value) * 
										(adjustment / 100.0))) / largest_value);
					new_red = p.getPixel(i, a).getRed() * calculated_change; 
					new_green = p.getPixel(i, a).getGreen() *calculated_change;
					new_blue = p.getPixel(i, a).getBlue() *calculated_change;
					
					saturation_pic.setPixel(i, a, new ColorPixel(new_red, new_green, new_blue));
					
					
				}
				
			}
		}
		
		return saturation_pic;
	}
	
	
	protected double inPixelRange(double number){
		if(number > 1){
			return 1;
		}
		
		if(number < 0){
			return 0;
		}
		
		else{
			return number;
		}
	}
	
}
