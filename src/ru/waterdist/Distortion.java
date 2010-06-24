package ru.waterdist;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Distortion extends Activity {
	
	//------------------
	  
	String str;
	int width, height, hwidth, hheight;
	Bitmap image, offImage;
	int i, a, b;
	int MouseX, MouseY;
	int fps, delay, size;

	short ripplemap[];
	int texture[];
	int srctexture[];
	int ripple[];
	int oldind, newind, mapind;
	int riprad;
	Bitmap im;

	Thread animatorThread;
	boolean frozen = false;
	
	//------------------
	
	
	
	GameTimer timer;
	
	Bitmap bm;
	
	int lastX;
	int lastY;
	Bitmap currBitmap;
	Bitmap temp_bm;
	
    /** Called when the activity is first created. */
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
         
        im = BitmapFactory.decodeResource(getResources(), R.drawable.sand);
        
        currBitmap = Bitmap.createBitmap(im, 0, 0, im.getWidth(), im.getHeight());
        
        width = im.getWidth();
		height = im.getHeight();
		temp_bm = Bitmap.createBitmap(width, height, Config.RGB_565); 
		hwidth = width >> 1;
		hheight = height >> 1; 
		riprad = 3;

		size = width * (height + 2) * 2;
		ripplemap = new short[size];
		ripple = new int[width * height];
		texture = new int[width * height];
		srctexture = new int[width * height];
		oldind = width;
		newind = width * (height + 3);

		im.getPixels(texture, 0, width, 0, 0, width, height);
		im.getPixels(srctexture, 0, width, 0, 0, width, height);
		
		System.arraycopy(texture, 0, ripple, 0, width * height);
		
		Ripple rip = new Ripple(this);
		setContentView(rip);
		timer = new GameTimer(rip);
//		timer.start();
		
    }
    
    @Override
	protected void onStart() {
    	Debug.startMethodTracing("Ripple");
    	
		super.onStart();
	}

	@Override
	protected void onPause() {
		timer.mRunning = false;
		
		super.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		disturb((int)event.getX(), (int)event.getY());
		lastX = (int)event.getX();
		lastY = (int)event.getY();
		return super.onTouchEvent(event);
	}

	public void disturb(int dx, int dy) {
		for (int j = dy - riprad; j < dy + riprad; j++) {
			for (int k = dx - riprad; k < dx + riprad; k++) {
				if (j >= 0 && j < height && k >= 0 && k < width) {
					ripplemap[oldind + (j * width) + k] += 512;
				}
			}
		}
	}

	public void newframe(int bx, int by) {
		
//		
		
		// Toggle maps each frame
		i = oldind;
		oldind = newind;
		newind = i;

		i = 0;
		mapind = oldind;
		long s = System.currentTimeMillis();
		
		temp_bm.getPixels(texture, 0, width, 0, 0, width, height);
		System.arraycopy(texture, 0, ripple, 0, width * height);
		int lolo = mapind; 
		
		for (int y = by-30; y < by+30; y++) {
			for (int x = bx-30; x < bx+30; x++) {
				
				i = x+y*width;
				if (i < 0) {
					continue;   
				}
				//Log.d("IIII", ""+i);  
				mapind = lolo + i;
				short data = (short) ((ripplemap[mapind - width]
						+ ripplemap[mapind + width] + ripplemap[mapind - 1] + ripplemap[mapind + 1]) >> 1);
				data -= ripplemap[newind + i];  //вычесть волну на пред шаге
				data -= data >> 3;
				ripplemap[newind + i] = data;

				// where data=0 then still, where data>0 then wave
				data = (short) (1024 - data);

				// offsets  
				a = ((x - hwidth) * data / 1024) + hwidth;
				b = ((y - hheight) * data / 1024) + hheight;

				// bounds check
				if (a >= width)
					a = width - 1;
				if (a < 0)
					a = 0;
				if (b >= height)
					b = height - 1;
				if (b < 0)
					b = 0;

				ripple[i] = texture[a + (b * width)];
						
			}
		}
		
		
		
		
		
		
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (y<by-30 || y>by+30 || x<bx-30 || x>bx+30) {
//					//ripple[i] = texture[x + (y * width)];
//					mapind++;
//					i++;
//					continue;
//					
//				}
//				
//				short data = (short) ((ripplemap[mapind - width]
//						+ ripplemap[mapind + width] + ripplemap[mapind - 1] + ripplemap[mapind + 1]) >> 1);
//				data -= ripplemap[newind + i];  //вычесть волну на пред шаге
//				data -= data >> 4;
//				ripplemap[newind + i] = data;
//
//				// where data=0 then still, where data>0 then wave
//				data = (short) (1024 - data);
//
//				// offsets  
//				a = ((x - hwidth) * data / 1024) + hwidth;
//				b = ((y - hheight) * data / 1024) + hheight;
//
//				// bounds check
//				if (a >= width)
//					a = width - 1;
//				if (a < 0)
//					a = 0;
//				if (b >= height)
//					b = height - 1;
//				if (b < 0)
//					b = 0;
//
//				ripple[i] = texture[a + (b * width)];
//				mapind++;
//				i++;
//			}
//		}
		Log.d("!!!!", "" + (System.currentTimeMillis() - s));
	
		
	}
    
    class Ripple extends View {

    	Paint p = new Paint();
    	public Ripple(Context context) {
    		super(context);
    		
    	}

    	
    	int qq=0;
    	
    	@Override
    	protected void onDraw(Canvas canvas) {
    		qq++; 
    		Canvas c = new Canvas();
    		c.setBitmap(temp_bm);
    		c.drawBitmap(currBitmap, 0, 0, p);
    		
    		//------
    		Paint p = new Paint();
    		p.setAntiAlias(true);
    		p.setColor(Color.RED);
    		//c.drawCircle(40, qq/2, 30, p);
    		
    		   
    		
    		canvas.drawBitmap(im, 0, 0, p);
    		
//    		super.onDraw(canvas);
    		
			newframe(lastX, lastY); 
			im.setPixels(ripple, 0, width, 0, 0, width, height);
    		invalidate();
    	}
    }
    
    class GameTimer extends Thread {

    	boolean  mRunning = true;
    	private Ripple mView;
    	
    	public GameTimer(Ripple v) {
    		mView = v;
    	}
    	
   	@Override
    	public void run() {
   		
   		//
    		while ( !isInterrupted() ) {
    			if (mRunning) {
    				newframe(lastX, lastY); 
    				
//    				long s = System.currentTimeMillis();
					im.setPixels(ripple, 0, width, 0, 0, width, height);
//					Log.d("!!!!", "" + (System.currentTimeMillis() - s));	
					
    				mView.postInvalidate();
    			} else {
//    				try {
//						Debug.dumpHprofData("/sdcard/HHOOO.hprof");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
    				Debug.stopMethodTracing();
    			}
    			
    			mView.qq++;
//				try {
//					sleep(50);
//				} catch (InterruptedException e) {
//					return;
//				}
    		}
    		
    		//
    	}
    	
    }
}

