package fr.rolandl.blog.backgroundforeground;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * @author Ludovic ROLAND
 * @since 2015.11.11
 */
public final class MyApplication
    extends Application
    implements ActivityLifecycleCallbacks
{

  private static final class VisibilityCallback
      implements Handler.Callback
  {

    private Context context;

    private int previousVisibility;

    public VisibilityCallback(Context context)
    {
      this.context = context;
      //Change this variable value if you want to be notified of the first launch
      previousVisibility = 0;
    }

    @Override
    public boolean handleMessage(Message msg)
    {
      if (previousVisibility != msg.arg1)
      {
        previousVisibility = msg.arg1;
        if (msg.what == MyApplication.APP_VISIBLE)
        {
          Toast.makeText(context, "App is in foreground", Toast.LENGTH_SHORT).show();
        }
        else
        {
          Toast.makeText(context, "App is in background", Toast.LENGTH_SHORT).show();
        }
      }

      return true;
    }

  }

  private static final int APP_VISIBLE = 0;

  private static final int APP_HIDDEN = 1;

  private static final int VISIBILITY_DELAY_IN_MS = 300;

  private Handler visibilityHandler;

  private int visibilityCounter;

  private boolean isAppInForeground;

  @Override
  public void onCreate()
  {
    super.onCreate();
    registerActivityLifecycleCallbacks(this);
    visibilityCounter = 0;
    isAppInForeground = false;
    visibilityHandler = new Handler(new VisibilityCallback(getApplicationContext()));
  }

  @Override
  public void onTerminate()
  {
    super.onTerminate();
    unregisterActivityLifecycleCallbacks(this);
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState)
  {

  }

  @Override
  public void onActivityStarted(Activity activity)
  {

  }

  @Override
  public void onActivityResumed(Activity activity)
  {
    visibilityCounter++;

    if (isAppInForeground == false)
    {
      isAppInForeground = true;

      final Message msg = new Message();
      msg.what = MyApplication.APP_VISIBLE;
      msg.arg1 = 1;

      visibilityHandler.removeMessages(MyApplication.APP_HIDDEN);
      visibilityHandler.sendMessageDelayed(msg, MyApplication.VISIBILITY_DELAY_IN_MS);
    }
  }

  @Override
  public void onActivityPaused(Activity activity)
  {
    visibilityCounter--;

    if (visibilityCounter <= 0)
    {
      visibilityCounter = 0;

      if (isAppInForeground == true)
      {
        isAppInForeground = false;

        final Message msg = new Message();
        msg.what = MyApplication.APP_HIDDEN;
        msg.arg1 = 0;

        visibilityHandler.removeMessages(MyApplication.APP_VISIBLE);
        visibilityHandler.sendMessageDelayed(msg, MyApplication.VISIBILITY_DELAY_IN_MS);
      }
    }
  }

  @Override
  public void onActivityStopped(Activity activity)
  {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState)
  {

  }

  @Override
  public void onActivityDestroyed(Activity activity)
  {

  }

}
