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

import java.util.List;

import org.apache.ibatis.abator.internal.util.messages.Messages;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Jeff Butler
 */
public class ParserErrorHandler implements ErrorHandler {
	private List warnings;

	private List errors;

	/**
	 *  
	 */
	public ParserErrorHandler(List warnings, List errors) {
		super();
		this.warnings = warnings;
		this.errors = errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
	 */
	public void warning(SAXParseException exception) throws SAXException {
		warnings.add(Messages.getString("Warning.7", //$NON-NLS-1$
		        Integer.toString(exception.getLineNumber()),
		        exception.getMessage()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException exception) throws SAXException {
		errors.add(Messages.getString("RuntimeError.4", //$NON-NLS-1$
		        Integer.toString(exception.getLineNumber()),
		        exception.getMessage()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
		errors.add(Messages.getString("RuntimeError.4", //$NON-NLS-1$
		        Integer.toString(exception.getLineNumber()),
		        exception.getMessage()));
	}
}
