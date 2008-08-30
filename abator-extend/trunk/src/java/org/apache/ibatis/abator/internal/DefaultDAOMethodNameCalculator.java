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

package org.apache.ibatis.abator.internal;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.abator.api.DAOMethodNameCalculator;
import org.apache.ibatis.abator.api.IntrospectedTable;
import org.apache.ibatis.abator.internal.db.ColumnDefinition;
import org.apache.ibatis.abator.internal.rules.AbatorRules;
import org.apache.ibatis.abator.internal.util.StringUtility;

/**
 * EXTEND:增加外键查询和索引查询的DAO接口名计算方法
 * 
 * @author Jeff Butler
 *
 */
public class DefaultDAOMethodNameCalculator implements DAOMethodNameCalculator {

    /**
     * 
     */
    public DefaultDAOMethodNameCalculator() {
        super();
    }

    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        return "insert"; //$NON-NLS-1$
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the
     *    result should be updateByPrimaryKey.
     * 2. If the other method is enabled, but there are seperate
     *    base and blob classes, then the method name should be
     *    updateByPrimaryKey
     * 3. Else the method name should be updateByPrimaryKeyWithoutBLOBs
     */
    public String getUpdateByPrimaryKeyWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else {
            return "updateByPrimaryKeyWithoutBLOBs"; //$NON-NLS-1$
        }
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the
     *    result should be updateByPrimaryKey.
     * 2. If the other method is enabled, but there are seperate
     *    base and blob classes, then the method name should be
     *    updateByPrimaryKey
     * 3. Else the method name should be updateByPrimaryKeyWithBLOBs
     */
    public String getUpdateByPrimaryKeyWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else {
            return "updateByPrimaryKeyWithBLOBs"; //$NON-NLS-1$
        }
    }
    
    public String getDeleteByExampleMethodName(IntrospectedTable introspectedTable) {
        return "deleteByExample"; //$NON-NLS-1$
    }

    public String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "deleteByPrimaryKey"; //$NON-NLS-1$
    }

    /**
     * 1. if this will be the only selectByExample, then the
     *    result should be selectByExample.
     * 2. Else the method name should be selectByExampleWithoutBLOBs
     */
    public String getSelectByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateSelectByExampleWithBLOBs()) {
            return "selectByExample"; //$NON-NLS-1$
        } else {
            return "selectByExampleWithoutBLOBs"; //$NON-NLS-1$
        }
    }

    /**
     * 1. if this will be the only selectByExample, then the
     *    result should be selectByExample.
     * 2. Else the method name should be selectByExampleWithBLOBs
     */
    public String getSelectByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateSelectByExampleWithoutBLOBs()) {
            return "selectByExample"; //$NON-NLS-1$
        } else {
            return "selectByExampleWithBLOBs"; //$NON-NLS-1$
        }
    }
    
    public String getQueryByForeignKeyMethodName(IntrospectedTable introspectedTable, List foreignKeyColumns){
    	Iterator iter = foreignKeyColumns.iterator();
    	String answer = "";
    	while(iter.hasNext()){
    		ColumnDefinition cd = (ColumnDefinition)iter.next();
    		if("".equals(answer))
    			answer += StringUtility.toInitCap(cd.getJavaProperty());
    		else
    			answer += "And" + StringUtility.toInitCap(cd.getJavaProperty());
    	}
    	if("".equals(answer))
    		answer = "queryByForeignKey";
    	else
    		answer = "queryBy" + answer;
    	
    	return answer;
    }
    
    public String getCountByForeignKeyMethodName(IntrospectedTable introspectedTable, List foreignKeyColumns){
    	Iterator iter = foreignKeyColumns.iterator();
    	String answer = "";
    	while(iter.hasNext()){
    		ColumnDefinition cd = (ColumnDefinition)iter.next();
    		if("".equals(answer))
    			answer += StringUtility.toInitCap(cd.getJavaProperty());
    		else
    			answer += "And" + StringUtility.toInitCap(cd.getJavaProperty());
    	}
    	if("".equals(answer))
    		answer = "countByForeignKey";
    	else
    		answer = "countBy" + answer;
    	
    	return answer;
    }
    
    public String getQueryByNonUniqueIndexMethodName(IntrospectedTable introspectedTable, List indexColumns){
    	Iterator iter = indexColumns.iterator();
    	String answer = "";
    	while(iter.hasNext()){
    		ColumnDefinition cd = (ColumnDefinition)iter.next();
    		if("".equals(answer))
    			answer += StringUtility.toInitCap(cd.getJavaProperty());
    		else
    			answer += "And" + StringUtility.toInitCap(cd.getJavaProperty());
    	}
    	if("".equals(answer))
    		answer = "queryByNonUniqueIndex";
    	else
    		answer = "queryBy" + answer;
    	
    	return answer;
    }
    
    public String getCountByNonUniqueIndexMethodName(IntrospectedTable introspectedTable, List indexColumns){
    	Iterator iter = indexColumns.iterator();
    	String answer = "";
    	while(iter.hasNext()){
    		ColumnDefinition cd = (ColumnDefinition)iter.next();
    		if("".equals(answer))
    			answer += StringUtility.toInitCap(cd.getJavaProperty());
    		else
    			answer += "And" + StringUtility.toInitCap(cd.getJavaProperty());
    	}
    	if("".equals(answer))
    		answer = "countByNonUniqueIndex";
    	else
    		answer = "countBy" + answer;
    	
    	return answer;
    }
    
    public String getSelectByUniqueIndexMethodName(IntrospectedTable introspectedTable, List indexColumns){
    	Iterator iter = indexColumns.iterator();
    	String answer = "";
    	while(iter.hasNext()){
    		ColumnDefinition cd = (ColumnDefinition)iter.next();
    		if("".equals(answer))
    			answer += StringUtility.toInitCap(cd.getJavaProperty());
    		else
    			answer += "And" + StringUtility.toInitCap(cd.getJavaProperty());
    	}
    	if("".equals(answer))
    		answer = "selectByUniqueIndex";
    	else
    		answer = "selectBy" + answer;
    	
    	return answer;
    }

    public String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "selectByPrimaryKey"; //$NON-NLS-1$
    }

    public String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable) {
        return "updateByPrimaryKeySelective"; //$NON-NLS-1$
    }

    public String getCountByExampleMethodName(IntrospectedTable introspectedTable) {
        return "countByExample"; //$NON-NLS-1$
    }

    public String getUpdateByExampleSelectiveMethodName(IntrospectedTable introspectedTable) {
        return "updateByExampleSelective"; //$NON-NLS-1$
    }

    public String getUpdateByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByExampleWithoutBLOBs()) {
            return "updateByExample"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByExample"; //$NON-NLS-1$
        } else {
            return "updateByExampleWithBLOBs"; //$NON-NLS-1$
        }
    }

    public String getUpdateByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        AbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByExampleWithBLOBs()) {
            return "updateByExample"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByExample"; //$NON-NLS-1$
        } else {
            return "updateByExampleWithoutBLOBs"; //$NON-NLS-1$
        }
    }
}
