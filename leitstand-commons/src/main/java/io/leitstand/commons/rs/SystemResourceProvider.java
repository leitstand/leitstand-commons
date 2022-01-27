package io.leitstand.commons.rs;

import java.util.Set;

/**
 * Returns all system resources provided by a Leitstand module.
 * <p>
 * The provider must be a CDI managed bean.
 * <code>{@literal @Dependent}</code> scope is recommended, as the bean is only required at boot time.
 * </p>
 * @see ApiResources
 */
public interface SystemResourceProvider {

    /**
     * Returns the set of system resources provided by a single module.
     * @return an immutable set of system resources
     */
    Set<Class<?>> getResources();
    
}
