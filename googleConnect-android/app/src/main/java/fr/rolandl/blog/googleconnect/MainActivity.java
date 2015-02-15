package fr.rolandl.blog.googleconnect;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;


public class MainActivity
    extends ActionBarActivity
{

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    getSupportFragmentManager().findFragmentById(R.id.container).onActivityResult(requestCode, resultCode, data);
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

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment
      extends Fragment
      implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener
  {

    private static final int GOOGLE_SIGN_IN = 9000;

    private Button btnConnect;

    private Button btnDisconnect;

    private Button btnRevoke;

    private GoogleApiClient googleApiClient;

    private ConnectionResult googleConnectionResult;

    private boolean googleIntentInProgress;

    private boolean btnConnectClicked;

    public PlaceholderFragment()
    {
    }

    @Override
    public void onStart()
    {
      super.onStart();

      if (googleApiClient != null)
      {
        googleApiClient.connect();
      }
    }

    @Override
    public void onStop()
    {
      super.onStop();

      if (googleApiClient != null && googleApiClient.isConnected() == true)
      {
        googleApiClient.disconnect();
      }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == PlaceholderFragment.GOOGLE_SIGN_IN)
      {
        btnConnectClicked = false;
        googleIntentInProgress = false;

        if (googleApiClient.isConnecting() == false)
        {
          googleApiClient.connect();
        }
      }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

      btnConnect = (Button) rootView.findViewById(R.id.btnConnect);
      btnDisconnect = (Button) rootView.findViewById(R.id.btnDisconnect);
      btnRevoke = (Button) rootView.findViewById(R.id.btnRevoke);

      btnConnect.setOnClickListener(this);
      btnDisconnect.setOnClickListener(this);
      btnRevoke.setOnClickListener(this);

      final Builder googleApiClientBuilder = new GoogleApiClient.Builder(getActivity());
      googleApiClientBuilder.addConnectionCallbacks(this);
      googleApiClientBuilder.addOnConnectionFailedListener(this);
      googleApiClientBuilder.addApi(Plus.API);
      googleApiClientBuilder.addScope(Plus.SCOPE_PLUS_LOGIN);

      googleApiClient = googleApiClientBuilder.build();

      return rootView;
    }

    @Override
    public void onClick(View view)
    {
      if (view.equals(btnConnect) == true)
      {
        if (googleApiClient.isConnecting() == false)
        {
          btnConnectClicked = true;
          connect();
        }
      }
      else if (view.equals(btnDisconnect) == true)
      {
        disconnect();
      }
      else if (view.equals(btnRevoke) == true)
      {
        revoke();
      }
    }

    private void revoke()
    {
      if (googleApiClient.isConnected() == true)
      {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient);
        Toast.makeText(getActivity(), "Révocation réussie", Toast.LENGTH_SHORT).show();
      }
    }

    private void disconnect()
    {
      if (googleApiClient.isConnected() == true)
      {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
        googleApiClient.disconnect();

        Toast.makeText(getActivity(), "Deconnexion réussie", Toast.LENGTH_SHORT).show();
      }
    }

    private void connect()
    {
      if (googleConnectionResult != null && googleConnectionResult.hasResolution() == true)
      {
        try
        {
          googleIntentInProgress = true;
          googleConnectionResult.startResolutionForResult(getActivity(), PlaceholderFragment.GOOGLE_SIGN_IN);
        }
        catch (SendIntentException exception)
        {
          googleIntentInProgress = false;
          googleApiClient.connect();
        }
      }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
      btnConnectClicked = false;
      getProfile();
    }

    private void getProfile()
    {
      try
      {
        if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null)
        {
          final String email = Plus.AccountApi.getAccountName(googleApiClient);

          if (email != null && "".equals(email) == false)
          {
            Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
          }
          else
          {
            Toast.makeText(getActivity(), "E-mail vide", Toast.LENGTH_SHORT).show();
          }
        }
        else
        {
          Toast.makeText(getActivity(), "Impossible de récupérer le profil", Toast.LENGTH_SHORT).show();
        }
      }
      catch (Exception exception)
      {
        Toast.makeText(getActivity(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
      googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
      if (connectionResult.hasResolution() == false)
      {
        GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), getActivity(), 0).show();
      }
      else
      {
        if (googleIntentInProgress == false)
        {
          googleConnectionResult = connectionResult;

          if (btnConnectClicked == true)
          {
            connect();
          }
        }
      }
    }
  }
}