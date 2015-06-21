package fr.openclassrooms.geolocalisation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ludovic Roland
 * @since 2015.03.12
 */
public final class MainActivity
    extends ActionBarActivity
{

  public final static class PlaceholderFragment
      extends Fragment
      implements OnMapReadyCallback, OnInfoWindowClickListener
  {

    public PlaceholderFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      final MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
      return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
      googleMap.getUiSettings().set
      googleMap.setOnInfoWindowClickListener(this);
      googleMap.addMarker(new MarkerOptions().position(new LatLng(48.8534100, 2.3488000)).title("Paris"));
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
      if ("Paris".equals(marker.getTitle()) == true)
      {
        Toast.makeText(getActivity().getApplicationContext(), "Paris", Toast.LENGTH_LONG).show();
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    if (savedInstanceState == null)
    {
      getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
    }
  }

}
