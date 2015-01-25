package fr.rolandl.blog.twitteroauth;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Ludovic Roland
 * @since 2015.01.25
 */
public class MainActivity
    extends Activity
{

  public static class PlaceholderFragment
      extends Fragment
      implements OnClickListener
  {

    private Twitter twitter;

    public PlaceholderFragment()
    {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
      if (resultCode == Activity.RESULT_OK && requestCode == 3)
      {
        final String oauthVerifierParam = data.getStringExtra("oauth_verifier");

        if (oauthVerifierParam != null)
        {
          new Thread(new Runnable()
          {
            @Override
            public void run()
            {
              try
              {
                final AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifierParam);
                final String name = accessToken.getScreenName();

                getActivity().runOnUiThread(new Runnable()
                {
                  @Override
                  public void run()
                  {
                    Toast.makeText(getActivity(), "Bonjour " + name, Toast.LENGTH_SHORT).show();

                  }
                });

              }
              catch (TwitterException exception)
              {
                exception.printStackTrace();
              }
            }
          }).start();

        }
        else
        {
          Toast.makeText(getActivity(), "Erreur d'identification", Toast.LENGTH_SHORT).show();
        }
      }

      super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      final Button twitterConnect = (Button) rootView.findViewById(R.id.twitterConnect);
      twitterConnect.setOnClickListener(this);
      return rootView;
    }

    @Override
    public void onClick(View view)
    {
      new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
          configurationBuilder.setOAuthConsumerKey(getString(R.string.consumer_key));
          configurationBuilder.setOAuthConsumerSecret(getString(R.string.consumer_secret));

          final TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
          twitter = twitterFactory.getInstance();

          try
          {
            final RequestToken requestToken = twitter.getOAuthRequestToken("twitter-callback:///");
            final String url = requestToken.getAuthenticationURL();
            final Intent intent = new Intent(getActivity(), LoginTwitterActivity.class);
            intent.putExtra("AuthenticationURL", url);

            startActivityForResult(intent, 3);
          }
          catch (TwitterException exception)
          {
            exception.printStackTrace();
          }
        }
      }).start();
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    if (savedInstanceState == null)
    {
      getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
    }
  }

}