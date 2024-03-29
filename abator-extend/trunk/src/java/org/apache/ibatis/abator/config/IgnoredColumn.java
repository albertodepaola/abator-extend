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

package org.apache.ibatis.abator.config;

import org.apache.ibatis.abator.api.dom.xml.Attribute;
import org.apache.ibatis.abator.api.dom.xml.XmlElement;
import org.apache.ibatis.abator.internal.util.StringUtility;

/**
 * @author Jeff Butler
 *
 */
public class IgnoredColumn {

    private String columnName;

    private boolean isColumnNameDelimited;

    private String configuredDelimitedColumnName;
    
    /**
     * 
     */
    public IgnoredColumn(String columnName) {
        super();
        this.columnName = columnName;
        isColumnNameDelimited = StringUtility.stringContainsSpace(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
        configuredDelimitedColumnName = isColumnNameDelimited ? "true" : "false"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IgnoredColumn)) {
            return false;
        }
        
        return columnName.equals(((IgnoredColumn)obj).getColumnName());
    }

    public int hashCode() {
        return columnName.hashCode();
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("ignoreColumn"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("column", columnName)); //$NON-NLS-1$
        
        if (StringUtility.stringHasValue(configuredDelimitedColumnName)) {
            xmlElement.addAttribute(new Attribute("delimitedColumnName", configuredDelimitedColumnName)); //$NON-NLS-1$
        }
        
        return xmlElement;
    }

}
