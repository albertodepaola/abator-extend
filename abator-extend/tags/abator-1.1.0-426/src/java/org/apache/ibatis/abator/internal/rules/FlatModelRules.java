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
 * a table using the flat model.
 * 
 * @author Jeff Butler
 *
 */
public class FlatModelRules extends AbatorRules {

    /**
     * 
     */
    public FlatModelRules(TableConfiguration tableConfiguration,
            ColumnDefinitions columnDefinitions) {
        super(tableConfiguration, columnDefinitions);
    }

    /**
     * We never generate a primary key in the flat model.
     * 
     * @return true if the primary key should be generated
     */
    public boolean generatePrimaryKeyClass() {
        return false;
    }

    /**
     * We always generate a base record in the flat model.
     * 
     * @return true if the class should be generated
     */
    public boolean generateBaseRecordClass() {
        return true;
    }

    /**
     * We never generate a record with BLOBs class in the flat model. 
     * 
     * @return true if the record with BLOBs class should be generated
     */
    public boolean generateRecordWithBLOBsClass() {
        return false;
    }
}
