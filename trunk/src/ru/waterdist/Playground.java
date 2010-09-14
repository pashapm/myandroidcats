package ru.waterdist;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;


public class Playground extends Activity {

	Bitmap mStad;
	Bitmap p1;
	Bitmap p2;
	Stadium mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrProps.initialize(this);
		mStad = BitmapFactory.decodeResource(getResources(), R.drawable.stadium);
		p1 = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
		p2 = BitmapFactory.decodeResource(getResources(), R.drawable.p2);
		
		mView = new Stadium(this);
		setContentView(mView);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {

					mView.postInvalidate();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
		
	}
	
	class Stadium extends View {

		Matrix mMat = new Matrix();
		int dx = 0;
		boolean right = true;
		Paint p = new Paint();
		
		public Stadium(Context context) {
			super(context);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			mMat.reset();
			
			dx += right ? 1 : -1;
			if (dx > 0 || dx < -500) {
				right =! right;
			}
			
			int sx = dx;
			int sy = (ScrProps.screenHeight - mStad.getHeight())/2;
			
			mMat.setTranslate(sx, sy);
			canvas.drawBitmap(mStad, mMat, p);
			
			mMat.reset();
			mMat.postTranslate(ScrProps.screenWidth/2, ScrProps.screenHeight/2);
			canvas.drawBitmap(Math.abs(dx % 30 )> 15 ? p1 : p2, mMat, p);
			
		}
	}
}
