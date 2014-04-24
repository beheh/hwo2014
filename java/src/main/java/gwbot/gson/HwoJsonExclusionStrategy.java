package gwbot.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import gwbot.message.Message;
import gwbot.message.MessageWrapper;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class HwoJsonExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (f.getDeclaringClass() == MessageWrapper.class) {
			// only send msgType, data and gameTick
			if (!f.getName().equals("msgType") && !f.getName().equals("data") && !f.getName().equals("gameTick")) {
				return true;
			}
		}
		if (f.getDeclaringClass() == Message.class) {
			// don't set gametick in Message (corresponds to MessageWrapper.data)
			if (f.getName().equals("gameTick")) {
				return true;
			}
		}
		return false;
	}

}
