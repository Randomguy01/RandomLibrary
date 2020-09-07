package com.random.randomlibrary.lifecycle;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * 9/5/2020
 * <p>
 * Insert description...
 *
 * @author Ian White
 */
public abstract class PersistableViewHolder implements Persistable {

  /**
   * The {@link Context} is the current state of the application
   */
  @NonNull
  private final Context CONTEXT;

  public PersistableViewHolder(@NonNull Context context) {
    CONTEXT = context;
    ButterKnife.bind(this, ((AppCompatActivity) context));
  }

  /**
   * @return {@link #CONTEXT}
   */
  @NonNull
  public Context getCONTEXT() {
    return CONTEXT;
  }

  /**
   * Get's the parent {@link RandomActivity} by casting {@link #CONTEXT} to an instance of {@link
   * RandomActivity}.
   */
  @NonNull
  public RandomActivity getActivity() {
    return ((RandomActivity) CONTEXT);
  }

}
