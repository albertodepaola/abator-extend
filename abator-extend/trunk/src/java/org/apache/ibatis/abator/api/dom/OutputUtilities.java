/*
 *  Copyright 2006 The Apache Software Foundation
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
package org.apache.ibatis.abator.api.dom;

/**
 * @author Jeff Butler
 */
public class OutputUtilities {
    private static final String lineSeparator;

    static {
        String ls = System.getProperty("line.separator"); //$NON-NLS-1$
        if (ls == null) {
            ls = "\n"; //$NON-NLS-1$
        }
        lineSeparator = ls;
    }
    
    /**
     * Utility class - no instances allowed
     */
    private OutputUtilities() {
        super();
    }

    /**
     * Utility method that indents the buffer by the default amount for Java
     * (four spaces per indent level).
     * 
     * @param sb
     *            a StringBuffer to append to
     * @param indentLevel
     *            the required indent level
     */
    public static void javaIndent(StringBuffer sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("    "); //$NON-NLS-1$
        }
    }

    /**
     * Utility method that indents the buffer by the default amount for XML
     * (two spaces per indent level).
     * 
     * @param sb
     *            a StringBuffer to append to
     * @param indentLevel
     *            the required indent level
     */
    public static void xmlIndent(StringBuffer sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  "); //$NON-NLS-1$
        }
    }

    /**
     * Utility method. Adds a newline character to a StringBuffer.
     * 
     * @param sb
     *            the StringBuffer to be appended to
     */
    public static void newLine(StringBuffer sb) {
        sb.append(lineSeparator);
    }
}
