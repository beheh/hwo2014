package gwbot.message;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class ThrottleMessage extends Message {
    private final double value;

    public ThrottleMessage(double value) {
        this.value = value;
    }

    @Override
    protected Object msgData() {
        return value;
    }

    @Override
    protected String msgType() {
        return "throttle";
    }
}
