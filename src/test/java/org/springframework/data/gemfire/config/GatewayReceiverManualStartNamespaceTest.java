/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.gemfire.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.gemfire.test.GemfireProfileValueSource;
import org.springframework.data.gemfire.test.GemfireTestApplicationContextInitializer;
import org.springframework.data.gemfire.wan.GatewayReceiverFactoryBean;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.cache.wan.GatewayReceiver;

/**
 * The GatewayReceiverManualStartNamespaceTest class is a test suite of test cases testing the contract
 * and functionality of Gateway Receiver configuration in Spring Data GemFire using the XML namespace (XSD).
 * This test class tests the manual start configuration of the GatewayReceiver Gemfire Component in SDG.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.junit.runner.RunWith
 * @see org.springframework.data.gemfire.test.GemfireTestApplicationContextInitializer
 * @see org.springframework.data.gemfire.wan.GatewayReceiverFactoryBean
 * @see org.springframework.test.context.ActiveProfiles
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 * @see com.gemstone.gemfire.cache.wan.GatewayReceiver
 * @since 1.5.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "GatewayReceiverNamespaceTest-context.xml",
	initializers = GemfireTestApplicationContextInitializer.class)
@IfProfileValue(name = GemfireProfileValueSource.PRODUCT_NAME_KEY,
	value = GemfireProfileValueSource.PIVOTAL_GEMFIRE_PRODUCT_NAME)
@ProfileValueSourceConfiguration(GemfireProfileValueSource.class)
@ActiveProfiles("manualStart")
@SuppressWarnings("unused")
public class GatewayReceiverManualStartNamespaceTest {

	@Resource(name = "&Manual")
	private GatewayReceiverFactoryBean manualGatewayReceiverFactory;

	@Test
	public void testManual() throws Exception {
		assertNotNull("The 'Manual' GatewayReceiverFactoryBean was not properly configured and initialized!",
			manualGatewayReceiverFactory);
		assertFalse(manualGatewayReceiverFactory.isAutoStartup());

		GatewayReceiver manualGatewayReceiver = manualGatewayReceiverFactory.getObject();

		assertNotNull(manualGatewayReceiver);
		assertEquals("192.168.0.1", manualGatewayReceiver.getBindAddress());
		assertEquals("theOne", manualGatewayReceiver.getHost());
		assertEquals(100, manualGatewayReceiver.getStartPort());
		assertEquals(900, manualGatewayReceiver.getEndPort());
		assertEquals(1000, manualGatewayReceiver.getMaximumTimeBetweenPings());
		assertFalse(manualGatewayReceiver.isRunning());
		assertEquals(8192, manualGatewayReceiver.getSocketBufferSize());
	}

}
