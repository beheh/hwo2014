package gwbot.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import gwbot.message.MessageWrapper;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class MessageWrapperExclStrat implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getDeclaringClass() == MessageWrapper.class && !f.getName().equals("msgType") && !f.getName().equals("data") && !f.getName().equals("gameTick"));
	}

}
