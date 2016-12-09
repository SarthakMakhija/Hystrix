package org.tw.spike.hystrix.exception.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;

@Component
public class ExceptionWrapper {

    public HttpStatusCodeWrapperException wrapException(Throwable th){
        if ( th instanceof HttpStatusCodeWrapperException ) return (HttpStatusCodeWrapperException)th;
        else                                                return new HttpStatusCodeWrapperException(th, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
