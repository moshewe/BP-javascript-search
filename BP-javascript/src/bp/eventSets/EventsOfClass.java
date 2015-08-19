package bp.eventSets;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A set containing all event instances from a class list.
 */

public class EventsOfClass extends ArrayList<Class<?>> implements EventSetInterface {

    private String name;

    /**
     * Constructor.
     */
    public EventsOfClass(Class<?>... clsList) {
        super();
        name = "";
        boolean first_flag = true;
        for (Class<?> cls : clsList) {
            add(cls);
            if (first_flag)
                first_flag = false;
            else
                name += "+";
            name += cls.getSimpleName();
        }

    }

    public EventsOfClass(String name, Class<?>... clsList) {
        this(clsList);
        this.name = name;
    }

    /**
     * Contains
     * <p/>
     * Overrides 'contains' inherited from ArrayList
     */
    public boolean contains(Object o) {

        Iterator<Class<?>> itr = this.iterator();

        while (itr.hasNext()) {
            Class<?> cls = itr.next();
            if (cls.isInstance(o) && testFields(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests whether the fields of the object match the required definition. By
     * default, we don't pose any requirements for the fields (always return
     * true).
     *
     * @param o Object to test (always of the expected class).
     * @return true if the fields of the given object math the criterion
     * represented by this filter.
     */
    public boolean testFields(Object o) {
        return true;
    }

    public void setName(String name) {
        this.name = name + "s";
    }

    public String toString() {
        return name + "'s";
    }

}
