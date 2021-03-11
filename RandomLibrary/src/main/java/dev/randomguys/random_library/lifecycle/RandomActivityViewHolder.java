package dev.randomguys.random_library.lifecycle;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

/**
 * 9/5/2020
 * <p>
 * RandomActivityViewHolder manages all UI related tasks in an app. Each {@link RandomActivity} must
 * have an associated viewHolder.
 *
 * @author Ian White
 */
public abstract class RandomActivityViewHolder<T extends ViewBinding> implements Persistable {

  /**
   * The {@link RandomActivity} this viewHolder belongs to.
   */
  private final RandomActivity ACTIVITY;

  /**
   * {@link ViewBinding} used to inflate views
   */
  private final T VIEW_BINDING;

  /**
   * Constructor shows activity's layout
   *
   * @param activity    the parent activity
   * @param viewBinding the activities {@link ViewBinding}
   */
  public RandomActivityViewHolder(@NonNull RandomActivity activity, @NonNull T viewBinding) {
    ACTIVITY = activity;
    VIEW_BINDING = viewBinding;

    //Show layout
    ACTIVITY.setContentView(VIEW_BINDING.getRoot());
  }

  /**
   * @return the current context from the {@link #ACTIVITY}
   */
  @NonNull
  public Context getContext() {
    return ACTIVITY.getApplicationContext();
  }

  /**
   * @return the parent {@link RandomActivity}
   */
  @NonNull
  public RandomActivity getActivity() {
    return ACTIVITY;
  }

  /**
   * @return viewBinding object to find views
   */
  @NonNull
  public T getViewBinding() {
    return VIEW_BINDING;
  }

}