//package FlowSkeleton;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Color;

public class FlowPanel extends JPanel implements Runnable{
	Terrain land;
	BufferedImage waterLayer; //overlay
	boolean state;

	FlowPanel(Terrain terrain) {
		land=terrain;
		waterLayer = new BufferedImage(land.dimx, land.dimy, BufferedImage.TYPE_INT_ARGB);
		state = false;
	}

	public Terrain getLand(){
		return land;
	}


	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		
		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}

		if (waterLayer != null){
			g.drawImage(waterLayer, 0, 0, null);
		}
	}

	public void addWater(int x, int y){
		land.surface[x][y].addWaterClick();
		run();
	}

	
	public void run() {	
		waterLayer = new BufferedImage(land.dimx, land.dimy, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < land.dim(); i++){
			
			int[] ind = new int[2]; 
			land.locate(land.permute.get(i),ind);

			Water current = land.surface[ind[0]][ind[1]];

			if (current.hasWater()){
				waterLayer.setRGB(ind[0],ind[1],new Color(0,0,255).getRGB());
			}

		}
		
	    repaint();
	}

}
