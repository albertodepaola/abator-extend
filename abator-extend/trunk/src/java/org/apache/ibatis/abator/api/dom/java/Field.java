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
package org.apache.ibatis.abator.api.dom.java;

import java.util.Iterator;

import org.apache.ibatis.abator.api.dom.OutputUtilities;

/**
 * @author Jeff Butler
 */
public class Field extends JavaElement {
    private FullyQualifiedJavaType type;

    private String name;
    private String initializationString;

    /**
     *  
     */
    public Field() {
        super();
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the type.
     */
    public FullyQualifiedJavaType getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(FullyQualifiedJavaType type) {
        this.type = type;
    }
    /**
     * @return Returns the initializationString.
     */
    public String getInitializationString() {
        return initializationString;
    }
    /**
     * @param initializationString The initializationString to set.
     */
    public void setInitializationString(String initializationString) {
        this.initializationString = initializationString;
    }
    
    public String getFormattedContent(int indentLevel) {
        StringBuffer sb = new StringBuffer();

        Iterator iter = getJavaDocLines().iterator();
        while (iter.hasNext()) {
            OutputUtilities.javaIndent(sb, indentLevel);
            sb.append(iter.next());
            OutputUtilities.newLine(sb);
        }
        
        iter = getAnnotations().iterator();
        while (iter.hasNext()) {
            OutputUtilities.javaIndent(sb, indentLevel);
            sb.append(iter.next());
            OutputUtilities.newLine(sb);
        }
        
        OutputUtilities.javaIndent(sb, indentLevel);
        if (getVisibility() == JavaVisibility.PRIVATE) {
            sb.append("private "); //$NON-NLS-1$
        } else if (getVisibility() == JavaVisibility.PROTECTED) {
            sb.append("protected "); //$NON-NLS-1$
        } else if (getVisibility() == JavaVisibility.PUBLIC) {
            sb.append("public "); //$NON-NLS-1$
        }
        
        if (isModifierStatic()) {
            sb.append("static "); //$NON-NLS-1$
        }
        
        if (isModifierFinal()) {
            sb.append("final "); //$NON-NLS-1$
        }
        
        sb.append(type.getShortName());

        sb.append(' ');
        sb.append(name);
        
        if (initializationString != null && initializationString.length() > 0) {
            sb.append(" = "); //$NON-NLS-1$
            sb.append(initializationString);
        }
        
        sb.append(';');
        
        return sb.toString();
    }
}
