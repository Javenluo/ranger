package com.javenluo.ranger.common.security.shiro.cache;

import com.javenluo.ranger.common.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义授权缓存管理类
 * @author gulong
 * @version 2014-7-21
 */
public class SessionCacheManager implements CacheManager {

	
	/**
	 * 根据缓存名字获取一个Cache
	 * @param name 缓存名字
	 * @return Cache
	 */
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new SessionCache<K, V>(name);
	}

	/**
	 * SESSION缓存管理类
	 */
	public class SessionCache<K, V> implements Cache<K, V> {

		private Logger logger = LoggerFactory.getLogger(getClass());

		private String cacheKeyName = null;

		public SessionCache(String cacheKeyName) {
			this.cacheKeyName = cacheKeyName;
		}

		public Session getSession() {
			Session session = null;
			try {
				Subject subject = SecurityUtils.getSubject();
				session = subject.getSession(false);
				if (session == null) {
					session = subject.getSession();
				}
			} catch (InvalidSessionException e) {
				logger.error("Invalid session error", e);
			} catch (UnavailableSecurityManagerException e2) {
				logger.error("Unavailable SecurityManager error", e2);
			}
			return session;
		}

		/**
		 * 根据Key获取缓存中的值
		 **/
		@SuppressWarnings("unchecked")
		public V get(K key) throws CacheException {
			if (key == null) {
				return null;
			}

			V v = null;
			HttpServletRequest request = Servlets.getRequest();
			if (request != null) {
				v = (V) request.getAttribute(cacheKeyName);
				if (v != null) {
					return v;
				}
			}

			V value = null;
			value = (V) getSession().getAttribute(cacheKeyName);
			logger.debug("get {} {} {}", cacheKeyName, key, request != null ? request.getRequestURI() : "");

			if (request != null && value != null) {
				request.setAttribute(cacheKeyName, value);
			}
			return value;
		}

		/**
		 * 往缓存中放入key-value，返回缓存中之前的值 
		 **/
		public V put(K key, V value) throws CacheException {
			if (key == null) {
				return null;
			}

			getSession().setAttribute(cacheKeyName, value);

			if (logger.isDebugEnabled()) {
				HttpServletRequest request = Servlets.getRequest();
				logger.debug("put {} {} {}", cacheKeyName, key, request != null ? request.getRequestURI() : "");
			}

			return value;
		}

		/**
		 * 移除缓存中key对应的值，返回该值 
		 **/
		@SuppressWarnings("unchecked")
		public V remove(K key) throws CacheException {

			V value = null;
			value = (V) getSession().removeAttribute(cacheKeyName);
			logger.debug("remove {} {}", cacheKeyName, key);

			return value;
		}

		/**
		 * 清空整个缓存 
		 **/
		public void clear() throws CacheException {
			getSession().removeAttribute(cacheKeyName);
			logger.debug("clear {}", cacheKeyName);
		}

		/**
		 * 返回缓存大小 
		 */
		public int size() {
			logger.debug("invoke session size abstract size method not supported.");
			return 0;
		}

		/**
		 * 获取缓存中所有的key 
		 */
		public Set<K> keys() {
			logger.debug("invoke session keys abstract size method not supported.");
			return new HashSet<K>();
		}

		/**
		 * 获取缓存中所有的value  
		 */
		public Collection<V> values() {
			logger.debug("invoke session values abstract size method not supported.");
			return Collections.emptyList();
		}
	}
}
