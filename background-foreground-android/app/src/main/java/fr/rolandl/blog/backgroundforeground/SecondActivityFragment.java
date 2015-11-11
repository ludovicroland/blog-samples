package fr.rolandl.blog.backgroundforeground;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Ludovic Roland
 * @since 2015.11.11
 */
public final class SecondActivityFragment
    extends Fragment
{

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    return inflater.inflate(R.layout.fragment_second, container, false);
  }


}
