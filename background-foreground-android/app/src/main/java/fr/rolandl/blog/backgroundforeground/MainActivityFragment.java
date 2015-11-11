package fr.rolandl.blog.backgroundforeground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author Ludovic Roland
 * @since 2015.11.11
 */
public final class MainActivityFragment
    extends Fragment
    implements OnClickListener
{

  private Button button;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    button = (Button) rootView.findViewById(R.id.button);
    button.setOnClickListener(this);
    return rootView;
  }

  @Override
  public void onClick(View view)
  {
    startActivity(new Intent(getActivity(), SecondActivity.class));
  }

}
