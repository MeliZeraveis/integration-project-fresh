package br.dh.meli.integratorprojectfresh.exception;

/**
 * The type Action not allowed exception.
 */
public class ActionNotAllowedException extends  RuntimeException{

    /**
     * Instantiates a new Action not allowed exception.
     *
     * @param message the message
     */
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
