package fr.rolandl.blog.facebookaudiencenetwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

/**
 * A placeholder fragment containing a simple view.
 */
public final class MainActivityFragment
    extends Fragment
{

  private AdView banner;

  private InterstitialAd interstitial;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    banner = new AdView(getActivity(), "***_***", AdSize.BANNER_HEIGHT_50);
    final RelativeLayout bannerWrapper = (RelativeLayout) rootView.findViewById(R.id.bannerWrapper);

    bannerWrapper.addView(banner);
    banner.loadAd();

    interstitial = new InterstitialAd(getActivity(), "***_***");
    interstitial.setAdListener(new InterstitialAdListener()
    {
      @Override
      public void onInterstitialDisplayed(Ad ad)
      {

      }

      @Override
      public void onInterstitialDismissed(Ad ad)
      {

      }

      @Override
      public void onError(Ad ad, AdError adError)
      {

      }

      @Override
      public void onAdLoaded(Ad ad)
      {
        interstitial.show();
      }

      @Override
      public void onAdClicked(Ad ad)
      {

      }
    });

    interstitial.loadAd();

    return rootView;
  }

  @Override
  public void onDestroy()
  {
    if (banner != null)
    {
      banner.destroy();
    }

    if (interstitial != null)
    {
      interstitial.destroy();
    }

    super.onDestroy();
  }
}
