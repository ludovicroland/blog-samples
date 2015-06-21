package fr.rolandl.blog.googleadmob;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A placeholder fragment containing a simple view.
 */
public final class MainActivityFragment
    extends Fragment
{

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    final AdView adView = (AdView) rootView.findViewById(R.id.banner);
    adView.loadAd(new AdRequest.Builder().build());

    final InterstitialAd interstitialAd = new InterstitialAd(getActivity());
    interstitialAd.setAdUnitId("ca-app-pub-***/***");
    interstitialAd.setAdListener(new AdListener()
    {

      @Override
      public void onAdLoaded()
      {
        interstitialAd.show();
      }
    });

    interstitialAd.loadAd(new AdRequest.Builder().build());

    return rootView;
  }
}
