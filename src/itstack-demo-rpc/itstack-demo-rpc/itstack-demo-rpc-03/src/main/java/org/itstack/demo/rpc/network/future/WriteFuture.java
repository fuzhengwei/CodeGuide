package org.itstack.demo.rpc.network.future;

import org.itstack.demo.rpc.network.msg.Response;

import java.util.concurrent.Future;

/**
 * Created by fuzhengwei1 on 2016/10/20.
 */
public interface WriteFuture<T> extends Future<T> {

    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String requestId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();


}
