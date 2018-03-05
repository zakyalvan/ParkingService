package parking.space;

/**
 * Hold current created space. Please note, this holder will be populated only if space created
 * by calling static factory method in {@link Space} interface.
 *
 * @author zakyalvan
 */
public class CurrentSpaceHolder {
    private static final ThreadLocal<Space> CONTAINER = new ThreadLocal<>();

    private CurrentSpaceHolder() {
    }

    public Space get() {
        Space space = CONTAINER.get();
        return space;
    }
    public void set(Space space) {
        if(space == null) {
            throw new IllegalArgumentException("Space parameter must be provided");
        }
        CONTAINER.set(space);
    }
    public boolean exists() {
        return CONTAINER.get() != null;
    }
    public void unset() {
        CONTAINER.set(null);
    }

    private static CurrentSpaceHolder instance;

    public static CurrentSpaceHolder instance() {
        if(instance == null) {
            instance = new CurrentSpaceHolder();
        }
        return instance;
    }
}
