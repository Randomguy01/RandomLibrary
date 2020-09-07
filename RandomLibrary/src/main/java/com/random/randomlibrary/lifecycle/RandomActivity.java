package com.random.randomlibrary.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.random.randomlibrary.network.NetworkManager;
import com.random.randomlibrary.network.NetworkManager.OnNetworkChangeListener;

/**
 * Created by Ian White on 9/5/2020
 *
 * @author Ian White
 */
public abstract class RandomActivity extends AppCompatActivity {

  /**
   * Handles boilerplate code as well as providing some very useful helper methods for activities.
   *
   * 09/05/2020
   *
   * @author Ian White
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  /**
   * When activity is started (or restarted) call {@link Persistable#update()} to retrieve stored
   * data.
   */
  @Override
  protected void onStart() {
    super.onStart();
    getViewHolder().update();
  }

  /**
   * When activity is destroyed call {@link Persistable#save()} to save data before the activity is
   * destroyed along with it's instance data. Removes any active {@link OnNetworkChangeListener}
   * that may hold a reference to this activity.
   */
  @Override
  protected void onStop() {
    super.onStop();
    getViewHolder().save();
    NetworkManager.removeNetworkListener(this);
  }

  /**
   * Starts new activity and optionally finishes previous activity.
   *
   * @param activityClass  the activity to start.
   * @param finishActivity true/false stop calling activity.
   */
  public void startActivity(@NonNull RandomActivity activityClass, boolean finishActivity) {
    if (finishActivity) {
      finish();
    }
    startActivity(new Intent(this, activityClass.getClass()));
  }

  /**
   * Used to get subclass's view holder so that it can be called when the activity starts {@link
   * #onStart()} and when it is destroyed {@link #onDestroy()}.
   *
   * @return {@link PersistableViewHolder} associated with activity.
   */
  @NonNull
  protected abstract PersistableViewHolder getViewHolder();


}
