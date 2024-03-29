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
package org.apache.ibatis.abator.internal.util;

import java.util.StringTokenizer;

/**
 * 
 * @author Jeff Butler
 */
public class StringUtility {

	/**
	 * Utility class.  No instances allowed
	 */
	private StringUtility() {
		super();
	}

	public static boolean stringHasValue(String s) {
		return s != null && s.length() > 0;
	}

    public static String composeFullyQualifiedTableName(String catalog, String schema,
            String tableName, char separator) {
        StringBuffer sb = new StringBuffer();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }

        sb.append(tableName);

        return sb.toString();
    }
    
    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }
        
        return sb.toString();
    }

    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("&quot;"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }
        
        return sb.toString();
    }
    
    /** EXTEND: ����ĸ��д (charr 2008-08-22) */
    public static String toInitCap(String s){
    	if(s == null)
    		return s;
    	if(s.length() < 2)
    		return s.toUpperCase();
    	return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
