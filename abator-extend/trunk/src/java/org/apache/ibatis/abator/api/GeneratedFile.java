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
package org.apache.ibatis.abator.api;

/**
 * Abstract class that holds information common to all generated files.
 * 
 * @author Jeff Butler
 */
public abstract class GeneratedFile {
	private String targetProject;

	/**
	 *  
	 */
	public GeneratedFile(String targetProject) {
		super();
		this.targetProject = targetProject;
	}

	/**
	 * This method returns the entire contents of the generated file.  Clients
	 * can simply save the value returned from this method as the file contents.
	 * Subclasses such as @see org.apache.ibatis.abator.api.GeneratedJavaFile
	 * offer more fine grained access to file parts, but still implement this method
	 * in the event that the entire contents are desired.
	 * 
	 * @return Returns the content.
	 */
	public abstract String getFormattedContent();
	
	/**
	 * Get the file name (without any path).  Clients should use this method to determine how to save
	 * the results.
	 * 
	 * @return Returns the file name.
	 */
	public abstract String getFileName();

	/**
	 * Gets the target project.  Clients can call this method to determine
	 * how to save the results.
	 * 
	 * @return the target project
	 */
	public String getTargetProject() {
		return targetProject;
	}

	/**
	 * Get the target package for the file.  Clients should use this method to determine how to save
	 * the results.
	 * 
	 * @return Returns the target project.
	 */
	public abstract String getTargetPackage();

    public String toString() {
        return getFormattedContent();
    }
}
