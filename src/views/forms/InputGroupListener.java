/**
 * 
 */
package views.forms;

import java.awt.event.KeyListener;

/**
 * @author sissoko
 *
 */
public interface InputGroupListener {
	Object getValue();
	void setError(String error);
	void setKeyListener(KeyListener listener);
	void validate(Validator validator);
}
