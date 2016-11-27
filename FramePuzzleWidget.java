package a7;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FramePuzzleWidget extends JPanel implements MouseListener, KeyListener{
	private PictureView[][] divided_pics = new PictureView[5][5];
	private int x_divide_value, y_divide_value, blue_pic_x, blue_pic_y;
	
	
	
	public FramePuzzleWidget(Picture p){
		
		setLayout( new GridLayout(5,5));
		
		x_divide_value = (p.getWidth() - (p.getWidth()%5) )/ 5;
		y_divide_value = (p.getHeight() - (p.getHeight()%5))/ 5;
		// these lines determine how many pixels will be in each picture
		
		for(int a=0; a <5; a++){
			for(int i=0; i<5; i++){
				divided_pics[i][a] = new PictureView(p.extract((i*x_divide_value), (a*y_divide_value), 
							x_divide_value, y_divide_value ).createObservable() ) ;
			}
		}
		
		divided_pics[4][4] = new PictureView(
				new PictureImpl(x_divide_value, y_divide_value, true).createObservable());
		blue_pic_x=4;
		blue_pic_y=4; // these lines create a blue square in the middle 
		
		for(int a=0; a <5; a++){
			for(int i=0; i<5; i++){
				divided_pics[i][a].addMouseListener(this);
				divided_pics[i][a].addKeyListener(this);
				add(divided_pics[i][a]);	
			}
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int x_value_click =(int)Math.floor(e.getComponent().getX()/x_divide_value);
		int y_value_click =(int) Math.floor(e.getComponent().getY()/y_divide_value);
		
		
		if(x_value_click == blue_pic_x){ //verticle column shift
			
			if(y_value_click > blue_pic_y){ // if the blue square is above the column
				int loop_amount = y_value_click - blue_pic_y;
				for(int i=0; i<loop_amount; i++){
					switchpics(x_value_click, blue_pic_y+i, x_value_click , blue_pic_y+i+1 );
				}
				blue_pic_y += (y_value_click - blue_pic_y);
			}
			
			if(blue_pic_y > y_value_click ){ // if the blue square is below the column
				int loop_amount = blue_pic_y - y_value_click;
				for(int i=0; i<loop_amount; i++){
					switchpics(x_value_click, blue_pic_y-i, x_value_click, blue_pic_y-i-1);
				}
				blue_pic_y += (y_value_click - blue_pic_y);
			}			
		}
		
		if(y_value_click == blue_pic_y){//horizontal row shift
			
			if(x_value_click > blue_pic_x){ // if the blue square is right of the row
				int loop_amount = x_value_click - blue_pic_x;
				for(int i=0; i<loop_amount; i++){
					switchpics(blue_pic_x+i,y_value_click , blue_pic_x+i+1 , y_value_click );
				}
				blue_pic_x += (x_value_click - blue_pic_x);
			}
			
			if(blue_pic_x > x_value_click){ // if the blue square is left of the row
				int loop_amount = blue_pic_x - x_value_click ;
				for(int i=0; i<loop_amount; i++){
					switchpics(blue_pic_x-i, y_value_click , blue_pic_x-i-1 , y_value_click );
				}
				blue_pic_x += (x_value_click - blue_pic_x);
			}
			
			
			
			
			
		}
			
		
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
	
		
		switch(e.getKeyCode()){
				// left= 37, up=38, right= 39, down =40
		case 37:
			if(blue_pic_x-1 < 0)
				break;
			switchpics((blue_pic_x-1), (blue_pic_y), blue_pic_x, blue_pic_y);
			blue_pic_x--;
			break;
			
		case 38:
			if(blue_pic_y-1 <0)
				break;
			switchpics((blue_pic_x), (blue_pic_y-1), blue_pic_x, blue_pic_y);
			blue_pic_y--;
			break;
		
		case 39:
			if(blue_pic_x+1 > 4)
				break;
			switchpics((blue_pic_x+1), (blue_pic_y), blue_pic_x, blue_pic_y);
			blue_pic_x++;
			break;
			
		case 40: 
			if(blue_pic_y+1 > 4)
				break;
			switchpics((blue_pic_x), (blue_pic_y+1), blue_pic_x, blue_pic_y);
			blue_pic_y++;
			break;
			
		default: //incase the user presses any other button besides arrow keys
			break;
		
		}
		
		
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void switchpics(int x1, int y1, int x2, int y2){
	//this method swaps the pictures by setting the PictureView in the array with a clone of the other picture
	//it swaps any two pictures
	
		Picture holder = divided_pics[x2][y2].getPicture().pictureclone();
		divided_pics[x2][y2].setPicture(divided_pics[x1][y1].getPicture().pictureclone().createObservable()); 
					
		
		divided_pics[x1][y1].setPicture(holder.createObservable()); 
	}
	
	


}
