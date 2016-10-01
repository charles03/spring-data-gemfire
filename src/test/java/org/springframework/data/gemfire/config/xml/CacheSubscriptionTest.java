/*
 * Copyright 2010-2013 the original author or authors.
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
 */

package org.springframework.data.gemfire.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.gemstone.gemfire.cache.InterestPolicy;
import com.gemstone.gemfire.cache.RegionAttributes;
import com.gemstone.gemfire.cache.SubscriptionAttributes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.gemfire.RegionFactoryBean;
import org.springframework.data.gemfire.TestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test to ensure subscription policy can be applied to server regions.
 *
 * @author Lyndon Adams
 * @author John Blum
 * @since 1.3.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("subscription-ns.xml")
@SuppressWarnings("unused")
public class CacheSubscriptionTest{

	@Autowired
	private ApplicationContext context;

	@Test
	public void testReplicatedRegionSubscriptionAllPolicy() throws Exception {
		assertTrue(context.containsBean("replicALL"));

		RegionFactoryBean regionFactoryBean = context.getBean("&replicALL", RegionFactoryBean.class);
		RegionAttributes regionAttributes = TestUtils.readField("attributes", regionFactoryBean);

		assertNotNull(regionAttributes);

		SubscriptionAttributes subscriptionAttributes = regionAttributes.getSubscriptionAttributes();

		assertNotNull(subscriptionAttributes);
		assertEquals(InterestPolicy.ALL, subscriptionAttributes.getInterestPolicy());
	}

	@Test
	public void testPartitionRegionSubscriptionCacheContentPolicy() throws Exception {
		assertTrue(context.containsBean("partCACHE_CONTENT"));

		RegionFactoryBean regionFactoryBean = context.getBean("&partCACHE_CONTENT", RegionFactoryBean.class);
		RegionAttributes regionAttributes = TestUtils.readField("attributes", regionFactoryBean);

		assertNotNull(regionAttributes);

		SubscriptionAttributes subscriptionAttributes = regionAttributes.getSubscriptionAttributes();

		assertNotNull(subscriptionAttributes);
		assertEquals(InterestPolicy.CACHE_CONTENT, subscriptionAttributes.getInterestPolicy());
	}

	@Test
	public void testPartitionRegionSubscriptionDefaultPolicy() throws Exception {
		assertTrue(context.containsBean("partDEFAULT"));

		RegionFactoryBean regionFactoryBean = context.getBean("&partDEFAULT", RegionFactoryBean.class);
		RegionAttributes regionAttributes = TestUtils.readField("attributes", regionFactoryBean);

		assertNotNull(regionAttributes);

		SubscriptionAttributes subscriptionAttributes = regionAttributes.getSubscriptionAttributes();

		assertNotNull(subscriptionAttributes);
		assertEquals(InterestPolicy.DEFAULT, subscriptionAttributes.getInterestPolicy());
	}

}