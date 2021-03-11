package dev.randomguys.random_library.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created 9/19/2020.
 * <p>
 * Wraps {@link Fragment} class, adding commonly used functionality and extra helper methods to make
 * working with fragments easier.
 *
 * @author Ian White
 */
public abstract class RandomFragment extends Fragment {
  // TODO: Ian White 9/19/2020 Firebase analytics possibly make this api random_library_core then create secondary library with firebase

  /**
   * Used to identify the class during debug
   */
  private static final String TAG = RandomFragment.class.getSimpleName();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    final View VIEW = inflater.inflate(getLayoutResource(), container, false);

    if (container != null) {
      onCreateViewHolder(inflater, container);
    } else {
      Log.w(TAG, "onCreateView: Container is null! There is no UI attached to this fragment");
    }

    return VIEW;
  }

  /**
   * Called by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} when the view has been
   * inflated and is ready for a {@link RandomFragmentViewHolder} to be created.
   *
   * @param inflater  {@link LayoutInflater} used to inflate the layout.
   * @param container {@link ViewGroup} containing views
   */
  protected abstract void onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup container);

  @LayoutRes
  protected abstract int getLayoutResource();

  public final RandomActivity getRandomActivity() {
    final Context CONTEXT = getContext();

    if (CONTEXT == null) {
      //Panic
      throw new NullPointerException("Context cannot be null");
    }

    if (CONTEXT instanceof RandomActivity) {
      return ((RandomActivity) CONTEXT);
    } else {
      throw new ClassCastException("Parent activity of a RandomFragment must be a RandomActivity!");
    }
  }
}
