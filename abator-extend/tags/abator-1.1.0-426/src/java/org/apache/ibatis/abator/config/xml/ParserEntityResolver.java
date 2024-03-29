/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.abator.config.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.abator.internal.sqlmap.XmlConstants;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Jeff Butler
 */
public class ParserEntityResolver implements EntityResolver {

	/**
	 *  
	 */
	public ParserEntityResolver() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
	 *      java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		if (XmlConstants.ABATOR_CONFIG_PUBLIC_ID.equals(publicId)) {
			InputStream is = getClass()
					.getClassLoader()
					.getResourceAsStream(
							"org/apache/ibatis/abator/config/xml/abator-config_1_0.dtd"); //$NON-NLS-1$
			InputSource ins = new InputSource(is);

			return ins;
		} else {
            return null;
		}
	}
}
