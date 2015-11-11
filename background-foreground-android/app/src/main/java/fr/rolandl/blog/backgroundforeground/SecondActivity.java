package fr.rolandl.blog.backgroundforeground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * @author Ludovic Roland
 * @since 2015.11.11
 */
public final class SecondActivity
    extends AppCompatActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
  }

}
