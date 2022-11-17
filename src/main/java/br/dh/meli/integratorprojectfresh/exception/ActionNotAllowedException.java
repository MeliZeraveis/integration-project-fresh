package br.dh.meli.integratorprojectfresh.exception;

public class ActionNotAllowedException extends  RuntimeException{

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
