package fr.rolandl.blog.twitteroauth;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Ludovic Roland
 * @since 2015.01.25
 */
public class LoginTwitterActivity
    extends Activity
{

  public static class LoginTwitterFragment
      extends Fragment
  {

    public LoginTwitterFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      final View rootView = inflater.inflate(R.layout.fragment_login_twitter, container, false);
      final WebView webview = (WebView) rootView.findViewById(R.id.loginWebView);

      final String url = getActivity().getIntent().getStringExtra("AuthenticationURL");
      webview.setWebViewClient(new WebViewClient()
      {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
          if (url.contains("twitter-callback:///") == true)
          {
            final Uri uri = Uri.parse(url);
            final String oauthVerifierParam = uri.getQueryParameter("oauth_verifier");
            final Intent intent = new Intent();
            intent.putExtra("oauth_verifier", oauthVerifierParam);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();

            return true;
          }
          return false;
        }
      });
      webview.loadUrl(url);

      return rootView;
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    if (savedInstanceState == null)
    {
      getFragmentManager().beginTransaction().add(R.id.container, new LoginTwitterFragment()).commit();
    }
  }

}