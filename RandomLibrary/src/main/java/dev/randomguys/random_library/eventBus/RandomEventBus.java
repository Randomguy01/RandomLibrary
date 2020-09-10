package dev.randomguys.random_library.eventBus;

import com.google.common.eventbus.EventBus;

/**
 * Created 9/5/2020.
 * <p>
 * Singleton allows for communication between components.
 *
 * @author Ian White
 * @see EventBus
 */
@SuppressWarnings("UnstableApiUsage")
public final class RandomEventBus extends EventBus {

  /**
   * Single instance of {@link RandomEventBus}
   */
  private static final RandomEventBus EVENT_BUS = new RandomEventBus();

  /**
   * Private constructor prevents outside instantiation of class.
   */
  private RandomEventBus() {
    //Intentionally left blank by Ian White on 9/5/2020
  }

  /**
   * @return {@link #EVENT_BUS}
   */
  public static RandomEventBus getEventBus() {
    return EVENT_BUS;
  }
}
