/*
 Copyright 2011 Anton Kraievoy akraievoy@gmail.com
 This file is part of org.akraievoy:base.

 org.akraievoy:base is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 org.akraievoy:base is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with org.akraievoy:base. If not, see <http://www.gnu.org/licenses/>.
 */

package org.akraievoy.base;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @see com.google.common.util.concurrent.Service
 */
public class Executors {
  protected Executors() {
    //	sealed
  }

  //	TODO depend on general TreadFactory please

  public static ScheduledExecutorService scheduledExecutor(final int corePoolSize, final ThreadFactory threadFactory) {
    //	http://java.akraievoy.org/2009/02/scheduledthreadpoolexecutor-gotcha.html
    return new AwareScheduledThreadPoolExecutor(corePoolSize, threadFactory);
  }

  public static ScheduledExecutorService scheduledExecutor(final int corePoolSize, final String baseName, final boolean daemon) {
    return scheduledExecutor(corePoolSize, new ThreadFactory(daemon, baseName));
  }

  public static class ThreadFactory implements java.util.concurrent.ThreadFactory {
    protected final boolean daemon;
    protected final String baseName;
    protected int counter;

    public ThreadFactory(final boolean daemon, final String baseName) {
      this.daemon = daemon;
      this.baseName = baseName;
      counter = 0;
    }

    public Thread newThread(Runnable r) {
      final Thread thread = new Thread(r, baseName + " #" + counter);

      thread.setDaemon(daemon);
      return thread;
    }
  }

  static class AwareScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor implements Startable {
    public AwareScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
      super(corePoolSize, threadFactory);
    }

    public void start() {
      //	nothing to do
    }

    public void stop() {
      shutdown();
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      super.setCorePoolSize(super.getCorePoolSize() + 1);
      return super.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      super.setCorePoolSize(super.getCorePoolSize() + 1);
      return super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
  }

  public static abstract class Task implements Startable, Runnable {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    protected final ScheduledExecutorService executor;
    protected final Object stateMutex = new Object();

    protected Thread thread;

    public Task(ScheduledExecutorService executor) {
      this.executor = executor;
    }

    protected long getInitialDelay() {
      return 0;
    }

    protected long getRestartDelay() {
      return 0;
    }

    public void start() {
      final long initialDelay = Math.max(0, getInitialDelay());
      final long restartDelay = Math.max(0, getRestartDelay());

      if (restartDelay == 0) {
        log.info("[" + getName() + "] initial delay: " + initialDelay + " ms");
        executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
      } else {
        log.info("[" + getName() + "] initial delay: " + initialDelay + " ms, restart delay: " + restartDelay + " ms");
        executor.scheduleWithFixedDelay(this, initialDelay, restartDelay, TimeUnit.MILLISECONDS);
      }
    }

    public void stop() {
      synchronized (stateMutex) {
        if (thread != null) {
          thread.interrupt();
          //      LATER a barrier here would be nice (to guarantee worker stopped before return)
          thread = null;
        } else {
          log.warn("no thread to stop");
        }
      }
    }

    protected String getName() {
      return this.getClass().getName();
    }

    public boolean isRunning() {
      synchronized (stateMutex) {
        return thread != null;
      }
    }

    /**
     * returning not-null from here results in executor service dropping the task forever
     *
     * @param t to shield or provoke
     * @return actual exception, wrapped or nulled-out
     */
    protected RuntimeException handleRunError(final Throwable t) {
      log.info(Task.this.getClass().getName() + " failed and ignored: " + Throwables.getRootCause(t).toString(), t);

      final Throwable rootCause = Throwables.getRootCause(t);
      return rootCause instanceof InterruptedException ? new RuntimeException(rootCause) : null;
    }

    public Thread getThread() {
      return thread;
    }

    public void run() {
      synchronized (stateMutex) {
        if (thread == null) {
          //      no other way to spot and label the executor service worker
          thread = Thread.currentThread();
          thread.setName(getName());
        }
      }

      try {

        Die.ifFalse("invalid running thread", thread == Thread.currentThread());
        if (thread.isInterrupted()) {
          //      and then this gets propagated as usual
          throw new InterruptedException();
        }

        runInternal();

      } catch (Throwable t) {

        final RuntimeException re = handleRunError(t);
        if (re != null) {
          throw re;
        }

      } finally {

        synchronized (stateMutex) {
          thread = null;
        }

      }
    }

    protected abstract void runInternal() throws Throwable;
  }
}