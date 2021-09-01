package Exceptions;

public class OperationSizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	//
	public OperationSizeException(String msg) {
		super(msg);
	}
}
