package dev.randomguys.random_library;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import dev.randomguys.random_library.lifecycle.RandomFragment;

/**
 * Created 11/8/2020.
 * <p>
 * Collection of utility methods to simplify navigation
 *
 * @author Ian White
 */
public final class RandomNavigation {

  /**
   * @param fragment   {@link RandomFragment} within an activity with a NavHostFragment
   * @param directions {@link NavDirections} specifying the destination and arguments to pass
   */
  public static void navigate(@NonNull RandomFragment fragment, @NonNull NavDirections directions) {
    NavController controller = NavHostFragment.findNavController(fragment);
    controller.navigate(directions);
  }
}
