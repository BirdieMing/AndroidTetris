package com.mygdx.game.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import TetrisGame.TetrisGame;
import TetrisGame.ICloseAd;

public class AndroidLauncher extends AndroidApplication implements ICloseAd {
	private static final String tag = "AndroidLauncher";
	protected AdView adView;
	//private InterstitialAd mInterstitialAd;
	private RelativeLayout layout;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobileAds.initialize(this, "ca-app-pub-3664977011511772~2010121873");

		//mInterstitialAd = new InterstitialAd(this);
		//mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice("ECBA3F3B42CB88E7C20911B476F2C0A6");

		//mInterstitialAd.loadAd(builder.build());
		//mInterstitialAd.show();

		layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new TetrisGame(this), config);
		layout.addView(gameView);

		adView = new AdView(this);

		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				Log.i(tag, "Ad Loaded..");
			}
		});
		adView.setAdSize(AdSize.BANNER);
		//adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		adView.setAdUnitId("ca-app-pub-3664977011511772/7122962692");

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);

		adView.loadAd(builder.build());
		layout.addView(adView, adParams);

		adView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				//now we can retrieve the width and height
				int width = adView.getWidth();
				int height = adView.getHeight();

				int lW = layout.getWidth();
				int aV = adView.getWidth();
				int w = Gdx.graphics.getWidth();
				int indent = (layout.getWidth() - adView.getWidth()) / 2;
				adView.setX(indent);

				//...
				//do whatever you want with them
				//...
				//this is an important step not to keep receiving callbacks:
				//we should remove this listener
				//I use the function to remove it based on the api level!

				if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
					adView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				else
					adView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});


		setContentView(layout);

		//initialize(new TetrisGame(), config);
	}

	public void CloseAd()
	{
		try
		{
			/*
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					int children = layout.getChildCount();
					if (children == 2) {
						adView.setVisibility(View.GONE);
					}
				}
			});
			*/
		}
		catch (Exception e)
		{
			Log.d("Closing Ad", e.getMessage());
		}
		//layout.removeView(adView);
		//if (adView != null) {
			//adView.destroy();
		//	adView.setVisibility(View.GONE);
		//}
	}
}
