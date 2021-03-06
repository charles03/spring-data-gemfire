/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.data.gemfire.util;

import org.springframework.util.StringUtils;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheClosedException;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.distributed.DistributedSystem;
import com.gemstone.gemfire.internal.cache.GemFireCacheImpl;

/**
 * CacheUtils is an abstract utility class encapsulating common operations for working with GemFire Cache
 * and ClientCache instances.
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.util.DistributedSystemUtils
 * @see com.gemstone.gemfire.cache.Cache
 * @see com.gemstone.gemfire.cache.CacheFactory
 * @see com.gemstone.gemfire.cache.GemFireCache
 * @see com.gemstone.gemfire.cache.Region
 * @see com.gemstone.gemfire.cache.client.ClientCache
 * @see com.gemstone.gemfire.cache.client.ClientCacheFactory
 * @see com.gemstone.gemfire.distributed.DistributedSystem
 * @see com.gemstone.gemfire.internal.cache.GemFireCacheImpl
 * @since 1.8.0
 */
@SuppressWarnings("unused")
public abstract class CacheUtils extends DistributedSystemUtils {

	public static final String DEFAULT_POOL_NAME = "DEFAULT";

	/* (non-Javadoc) */
	@SuppressWarnings("all")
	public static boolean isClient(GemFireCache cache) {
		boolean client = (cache instanceof ClientCache);

		if (cache instanceof GemFireCacheImpl) {
			client &= ((GemFireCacheImpl) cache).isClient();
		}

		return client;
	}

	/* (non-Javadoc) */
	public static boolean isDurable(ClientCache clientCache) {
		DistributedSystem distributedSystem = getDistributedSystem(clientCache);

		// NOTE technically the following code snippet would be more useful/valuable but is not "testable"!
		//((InternalDistributedSystem) distributedSystem).getConfig().getDurableClientId();

		return (isConnected(distributedSystem) && StringUtils.hasText(distributedSystem.getProperties()
			.getProperty(DURABLE_CLIENT_ID_PROPERTY_NAME, null)));
	}

	/* (non-Javadoc) */
	@SuppressWarnings("all")
	public static boolean isPeer(GemFireCache cache) {
		boolean peer = (cache instanceof Cache);

		if (cache instanceof GemFireCacheImpl) {
			peer &= !((GemFireCacheImpl) cache).isClient();
		}

		return peer;
	}

	/* (non-Javadoc) */
	public static boolean closeCache() {
		try {
			CacheFactory.getAnyInstance().close();
			return true;
		}
		catch (Exception ignore) {
			return false;
		}
	}

	/* (non-Javadoc) */
	public static boolean closeClientCache() {
		try {
			ClientCacheFactory.getAnyInstance().close();
			return true;
		}
		catch (Exception ignore) {
			return false;
		}
	}

	/* (non-Javadoc) */
	public static Cache getCache() {
		try {
			return CacheFactory.getAnyInstance();
		}
		catch (CacheClosedException ignore) {
			return null;
		}
	}

	/* (non-Javadoc) */
	public static ClientCache getClientCache() {
		try {
			return ClientCacheFactory.getAnyInstance();
		}
		catch (CacheClosedException ignore) {
			return null;
		}
	}
}
