package th.co.nttdata.tki.blogic.fng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import th.co.nttdata.tki.bean.Message;

/**
 * A multi-lot rule was broken. RuntimeException on purpose: the tx advice only rolls back on
 * RuntimeException, and everything saved before the check must be undone.
 */
public class MultiLotValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final List<Message> errors = new ArrayList<Message>();

	/** @param codeAndArgs each entry is {code, arg0, arg1, ...} */
	public MultiLotValidationException(List<String[]> codeAndArgs) {
		super(codeAndArgs.isEmpty() ? "" : codeAndArgs.get(0)[0]);
		for (String[] e : codeAndArgs) {
			errors.add(new Message(e[0], Arrays.copyOfRange(e, 1, e.length)));
		}
	}

	public List<Message> getErrors() {
		return errors;
	}
}
