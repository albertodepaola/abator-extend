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
package org.apache.ibatis.abator.api.dom.xml;

/**
 * @author Jeff Butler
 */
public class Attribute {
    private String name;
    private String value;

    /**
     * 
     */
    public Attribute(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }
    
    public String getFormattedContent() {
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append("=\""); //$NON-NLS-1$
        sb.append(value);
        sb.append('\"');
        
        return sb.toString();
    }
}
