package Lifecycle;

import java.util.Map;

/**
 * Created by armand on 3/15/15.
 *
 * Interface that represents aLifecycle (could be an Activity/Fragment/etc). The user of this interface needs to
 * determine the ordering of the Lifecycle.
 */
public interface Lifecycle {
    public Map<String, String> sort();
}
