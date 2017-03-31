package org.elixer.core.Entity;

import java.util.List;

/**
 * Created by aweso on 3/24/2017.
 */
public interface IHierarchical<T> {

    public List<T> getChildren();

    public T getParent();
}
