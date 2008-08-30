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
package org.apache.ibatis.abator.internal.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds the results of introspecting the database table.
 * EXTEND: 增加了外键foreignKeysColumns, 唯一索引uniqueIndicesColumns, 非唯一索引nonUniqueIndicesColumns (charr 2008-08-22)
 * @author Jeff Butler
 */
public class ColumnDefinitions {
	
    private List primaryKeyColumns;
	private List baseColumns;
    private List blobColumns;
    private boolean hasJDBCDateColumns;
    private boolean hasJDBCTimeColumns;
    
    private List foreignKeysColumns;
    private List uniqueIndicesColumns;
    private List nonUniqueIndicesColumns;

	public ColumnDefinitions() {
		super();
		primaryKeyColumns = new ArrayList();
        baseColumns = new ArrayList();
        blobColumns = new ArrayList();
        
        foreignKeysColumns = new ArrayList();
        uniqueIndicesColumns = new ArrayList();
        nonUniqueIndicesColumns = new ArrayList();
	}
	
	public void addForeignKey(List fk){
//		System.out.println("============ add foreign key for " + fk.size() + " columns.");

		/** 检查是否是主键，检查是否是索引（唯一和不唯一） */
		List primaryKeyColumnNameList = getColumnNameListFromCDList(primaryKeyColumns);
		if(isListContentEquals(fk, primaryKeyColumnNameList)){
			System.out.println("-------------和主键一致的外键，丢弃");
			return;
		}
		Iterator iter = uniqueIndicesColumns.iterator();
		while(iter.hasNext()){
			List uiColumnNameList = getColumnNameListFromCDList((List)iter.next());
			if(isListContentEquals(fk, uiColumnNameList)){
				System.out.println("-------------和唯一索引一致的外键，丢弃");
				return;
			}
		}
		iter = nonUniqueIndicesColumns.iterator();
		while(iter.hasNext()){
			List nuiColumnNameList = getColumnNameListFromCDList((List)iter.next());
			if(isListContentEquals(fk, nuiColumnNameList)){
				System.out.println("-------------和非唯一索引一致的外键，丢弃");
				return;
			}
		}

		List cdList = new ArrayList();
		iter = fk.iterator();
		while(iter.hasNext()){
			String cname = (String)iter.next();
			ColumnDefinition cd = getColumn(cname);
			if(cd != null)
				cdList.add(cd);
		}
		
		foreignKeysColumns.add(cdList);
	}
	public void addUniqueIndex(List index){
//		System.out.println("============ add unique index for " + index.size() + " columns.");

		/** 检查是否是主键 */
		List primaryKeyColumnNameList = getColumnNameListFromCDList(primaryKeyColumns);
		if(isListContentEquals(index, primaryKeyColumnNameList)){
			System.out.println("-------------和主键一致的索引，丢弃");
			return;
		}
		
		List cdList = new ArrayList();
		Iterator iter = index.iterator();
		while(iter.hasNext()){
			String cname = (String)iter.next();
			ColumnDefinition cd = getColumn(cname);
			if(cd != null)
				cdList.add(cd);
		}
		
		uniqueIndicesColumns.add(cdList);
	}
	public void addNonUniqueIndex(List index){
//		System.out.println("============ add nonunique index for " + index.size() + " columns.");

		List cdList = new ArrayList();
		Iterator iter = index.iterator();
		while(iter.hasNext()){
			String cname = (String)iter.next();
			ColumnDefinition cd = getColumn(cname);
			if(cd != null)
				cdList.add(cd);
		}
		
		nonUniqueIndicesColumns.add(cdList);
	}
	private List getColumnNameListFromCDList(List cdList){
		List nameList = new ArrayList();
		Iterator iter = cdList.iterator();
		while(iter.hasNext()){
			ColumnDefinition cd = (ColumnDefinition)iter.next();
			nameList.add(cd.getActualColumnName());
		}
		Collections.sort(nameList);
		return nameList;
	}
	private boolean isListContentEquals(List a, List b){
		if(a == null && b == null)
			return true;
		if(a == null || b == null)
			return false;
		
		Collections.sort(a);
		Collections.sort(b);
		if(a.size() != b.size())
			return false;
		Iterator ia = a.iterator();
		Iterator ib = b.iterator();
		while(ia.hasNext() && ib.hasNext()){
			Object oa = ia.next();
			Object ob = ib.next();
			if(!oa.equals(ob))
				return false;
		}
		if(ia.hasNext() || ib.hasNext())
			return false;
		return true;
	}
	
	public List getForeignKeysColumns() {
		return foreignKeysColumns;
	}

	public List getUniqueIndicesColumns() {
		return uniqueIndicesColumns;
	}

	public List getNonUniqueIndicesColumns() {
		return nonUniqueIndicesColumns;
	}



	public List getBLOBColumns() {
        return blobColumns;
	}

	public List getBaseColumns() {
        return baseColumns;
	}
	
	public List getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	public void addColumn(ColumnDefinition cd) {
        if (cd.isBLOBColumn()) {
            blobColumns.add(cd);
        } else {
            baseColumns.add(cd);
        }
        
        if (cd.isJDBCDateColumn()) {
            hasJDBCDateColumns = true;
        }
        
        if (cd.isJDBCTimeColumn()) {
            hasJDBCTimeColumns = true;
        }
	}

	public void addPrimaryKeyColumn(String columnName) {
        boolean found = false;
        // first search base columns
        Iterator iter = baseColumns.iterator();
        while (iter.hasNext()) {
            ColumnDefinition cd = (ColumnDefinition) iter.next();
            if (cd.getActualColumnName().equals(columnName)) {
                primaryKeyColumns.add(cd);
                iter.remove();
                found = true;
                break;
            }
        }
        
        // search blob columns in the wierd event that a blob is the primary key
        if (!found) {
            iter = blobColumns.iterator();
            while (iter.hasNext()) {
                ColumnDefinition cd = (ColumnDefinition) iter.next();
                if (cd.getActualColumnName().equals(columnName)) {
                    primaryKeyColumns.add(cd);
                    iter.remove();
                    found = true;
                    break;
                }
            }
        }
    }

	public boolean hasPrimaryKeyColumns() {
		return primaryKeyColumns.size() > 0;
	}

	public boolean hasBLOBColumns() {
        return blobColumns.size() > 0;
	}

	public boolean hasBaseColumns() {
        return baseColumns.size() > 0;
	}
	
	public ColumnDefinition getColumn(String columnName) {
        if (columnName == null) {
            return null;
        } else {
            // search primary key columns
            Iterator iter = primaryKeyColumns.iterator();
            while (iter.hasNext()) {
                ColumnDefinition cd = (ColumnDefinition) iter.next();
                if (cd.isColumnNameDelimited()) {
                    if (cd.getActualColumnName().equals(columnName)) {
                        return cd;
                    }
                } else {
                    if (cd.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return cd;
                    }
                }
            }
            
            // search base columns
            iter = baseColumns.iterator();
            while (iter.hasNext()) {
                ColumnDefinition cd = (ColumnDefinition) iter.next();
                if (cd.isColumnNameDelimited()) {
                    if (cd.getActualColumnName().equals(columnName)) {
                        return cd;
                    }
                } else {
                    if (cd.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return cd;
                    }
                }
            }

            // search bblob columns
            iter = blobColumns.iterator();
            while (iter.hasNext()) {
                ColumnDefinition cd = (ColumnDefinition) iter.next();
                if (cd.isColumnNameDelimited()) {
                    if (cd.getActualColumnName().equals(columnName)) {
                        return cd;
                    }
                } else {
                    if (cd.getActualColumnName().equalsIgnoreCase(columnName)) {
                        return cd;
                    }
                }
            }
            
            return null;
        }
	}
    
    public boolean hasJDBCDateColumns() {
        return hasJDBCDateColumns;
    }
    
    public boolean hasJDBCTimeColumns() {
        return hasJDBCTimeColumns;
    }
    
    public boolean hasAnyColumns() {
        return primaryKeyColumns.size() > 0
            || baseColumns.size() > 0
            || blobColumns.size() > 0;
    }
}
