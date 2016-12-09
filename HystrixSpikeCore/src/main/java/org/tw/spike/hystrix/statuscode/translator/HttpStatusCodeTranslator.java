package org.tw.spike.hystrix.statuscode.translator;

import fj.data.Either;
import org.springframework.stereotype.Component;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;

import java.io.Serializable;

@Component
public class HttpStatusCodeTranslator<A extends HttpStatusCodeWrapperException, B extends Serializable> {

    public Either<A, B> translateSuccessStatusCodeTo( B body ){
        return Either.right(body);
    }

    public Either<A, B> translateFailureStatusCodeTo(A httpStatusCodeWrapperException, B body ){
        if ( httpStatusCodeWrapperException.httpStatus().is2xxSuccessful() ) return translateSuccessStatusCodeTo(body);
        else                                                                 return Either.left(httpStatusCodeWrapperException);
    }
}