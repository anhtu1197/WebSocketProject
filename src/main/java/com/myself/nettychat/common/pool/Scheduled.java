package com.myself.nettychat.common.pool;

import java.util.concurrent.ScheduledFuture;


@FunctionalInterface
public interface Scheduled {

    ScheduledFuture<?> submit(Runnable runnable);

}
