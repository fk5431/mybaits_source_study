/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache.decorators;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple blocking decorator 
 * 
 * Simple and inefficient version of EhCache's BlockingCache decorator.
 * It sets a lock over a cache key when the element is not found in cache.
 * This way, other threads will wait until this element is filled instead of hitting the database.
 * 简单低效的实现，如果缓存没找到元素，就是加一个锁在缓存的key上
 * 这样，其他的线程等待这个元素有值，而不是直接命中到数据库上
 * @author Eduardo Macarron
 *
 */
//阻塞 缓存（主要体现在获取锁的方面）
public class BlockingCache implements Cache {

  private long timeout;
  private final Cache delegate;//里面又存了一个cache的实例。装饰器
  //线程安全的hashmap，里面存放了可重入锁
  private final ConcurrentHashMap<Object, ReentrantLock> locks;

  public BlockingCache(Cache delegate) {
    this.delegate = delegate;
    this.locks = new ConcurrentHashMap<>();
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  @Override
  public void putObject(Object key, Object value) {
    try {
      delegate.putObject(key, value);
    } finally {
      releaseLock(key);
    }
  }

  @Override
  public Object getObject(Object key) {
    acquireLock(key);//取值的时候加锁，这样在读的时候就不能修改了
    Object value = delegate.getObject(key);
    if (value != null) {
      releaseLock(key);//如果有这个值，把锁释放
    }        
    return value;
  }

  @Override
  public Object removeObject(Object key) {
    // despite of its name, this method is called only to release locks
    //虽然叫了个remove 但是还是就释放了下锁
    releaseLock(key);
    return null;
  }

  @Override
  public void clear() {
    delegate.clear();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return null;
  }
  //得到这个key的锁
  private ReentrantLock getLockForKey(Object key) {
    ReentrantLock lock = new ReentrantLock();
    ReentrantLock previous = locks.putIfAbsent(key, lock);
    return previous == null ? lock : previous;//如果这个key有锁，返回之前的锁
  }
  //获得锁
  private void acquireLock(Object key) {
    Lock lock = getLockForKey(key);
    if (timeout > 0) {
      try {
        //尝试去获取锁,拿不到锁等待timeout时间，还拿不到,就返回false
        boolean acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
        if (!acquired) {
          throw new CacheException("Couldn't get a lock in " + timeout + " for the key " +  key + " at the cache " + delegate.getId());  
        }
      } catch (InterruptedException e) {
        throw new CacheException("Got interrupted while trying to acquire lock for key " + key, e);
      }
    } else {
      lock.lock();//获取锁，获取不到就等待把
    }
  }
  
  private void releaseLock(Object key) {
    ReentrantLock lock = locks.get(key);
    if (lock.isHeldByCurrentThread()) {//看看当前线程是否还有持有这个锁
      lock.unlock();
    }
  }

  public long getTimeout() {
    return timeout;
  }

  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }  
}