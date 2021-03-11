package dev.randomguys.random_library.lifecycle;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

/**
 * Created 9/19/2020.
 * <p>
 *
 * ViewHolder controls all UI related functions for an activity and utilizes each activity's view
 * binding.
 *
 * @author Ian White
 */
public abstract class RandomFragmentViewHolder<T extends ViewBinding>
    extends RandomActivityViewHolder<T> {

  private final RandomFragment FRAGMENT;

  /**
   * Constructor shows activity's layout
   *
   * @param activity    the parent activity
   * @param viewBinding the activity's {@link ViewBinding}
   */
  public RandomFragmentViewHolder(@NonNull RandomActivity activity,
      @NonNull RandomFragment fragment, @NonNull T viewBinding) {
    super(activity, viewBinding);
    FRAGMENT = fragment;
  }

  /**
   * @return {@link #FRAGMENT}
   */
  public RandomFragment getFragment() {
    return FRAGMENT;
  }
}
