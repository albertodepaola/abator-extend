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

package org.apache.ibatis.abator.internal.rules;

import org.apache.ibatis.abator.config.TableConfiguration;
import org.apache.ibatis.abator.internal.db.ColumnDefinitions;

/**
 * This class encapsulates all the code generation rules for 
 * a table using the hierarchical model.
 * 
 * @author Jeff Butler
 *
 */
public class HierarchicalModelRules extends AbatorRules {

    /**
     * 
     */
    public HierarchicalModelRules(TableConfiguration tableConfiguration,
            ColumnDefinitions columnDefinitions) {
        super(tableConfiguration, columnDefinitions);
    }

    /**
     * Implements the rule for determining whether to generate a primary key
     * class. If the physical table has a primary key, then we generate the
     * class.
     * 
     * @return true if the primary key should be generated
     */
    public boolean generatePrimaryKeyClass() {
        return columnDefinitions.hasPrimaryKeyColumns();
    }

    /**
     * Implements the rule for generating a base record.  If the table
     * has fields that are not in the primary key, and non-BLOB fields,
     * then generate the class.
     * 
     * @return true if the class should be generated
     */
    public boolean generateBaseRecordClass() {
        return columnDefinitions.hasBaseColumns();
    }

    /**
     * Implements the rule for generating a record with BLOBs.  A record
     * with BLOBs is generated if the table contains any BLOB fields. 
     * 
     * @return true if the record with BLOBs class should be generated
     */
    public boolean generateRecordWithBLOBsClass() {
        return columnDefinitions.hasBLOBColumns();
    }
}
