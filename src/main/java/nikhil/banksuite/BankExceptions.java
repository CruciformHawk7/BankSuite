package nikhil.banksuite;

import java.lang.Exception;

class InvalidNameException extends Exception { 
    private static final long serialVersionUID = -2493494058537721323L;

    InvalidNameException() {
        super("Name is Invalid!");
    }}