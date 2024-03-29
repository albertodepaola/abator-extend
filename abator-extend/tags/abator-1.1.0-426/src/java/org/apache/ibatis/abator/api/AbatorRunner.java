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
package org.apache.ibatis.abator.api;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ibatis.abator.config.AbatorConfiguration;
import org.apache.ibatis.abator.config.xml.AbatorConfigurationParser;
import org.apache.ibatis.abator.exception.InvalidConfigurationException;
import org.apache.ibatis.abator.exception.XMLParserException;
import org.apache.ibatis.abator.internal.DefaultShellCallback;
import org.apache.ibatis.abator.internal.util.messages.Messages;

/**
 * This class allows Abator to be run from the command line.
 * 
 * @author Jeff Butler
 */
public class AbatorRunner {
    private static final String ARGUMENT_PREFIX = "-"; //$NON-NLS-1$
    private static final String CONFIG_FILE = "-configfile"; //$NON-NLS-1$
    private static final String OVERWRITE = "-overwrite"; //$NON-NLS-1$
    private static final String CONTEXT_IDS = "-contextids"; //$NON-NLS-1$
    private static final String TABLES = "-tables"; //$NON-NLS-1$

	public static void main(String[] args) {
        Map arguments = parseCommandLine(args);
        
        if (!arguments.containsKey(CONFIG_FILE)) {
            writeLine(Messages.getString("RuntimeError.0")); //$NON-NLS-1$
            return;
        }
        
        List warnings = new ArrayList();

        String configfile = (String) arguments.get(CONFIG_FILE);
        File configurationFile = new File(configfile);
        if (!configurationFile.exists()) {
            writeLine(Messages.getString("RuntimeError.1", configfile)); //$NON-NLS-1$
            return;
        }

        Set fullyqualifiedTables = new HashSet();
        if (arguments.containsKey(TABLES)) {
            StringTokenizer st = new StringTokenizer((String)arguments.get(TABLES), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }
        
        Set contexts = new HashSet();
        if (arguments.containsKey(CONTEXT_IDS)) {
            StringTokenizer st = new StringTokenizer((String)arguments.get(CONTEXT_IDS), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contexts.add(s);
                }
            }
        }
        
        try {
            AbatorConfigurationParser cp = new AbatorConfigurationParser(
                warnings);
            AbatorConfiguration config = cp.parseAbatorConfiguration(configurationFile);
            
            DefaultShellCallback callback = new DefaultShellCallback(arguments.containsKey(OVERWRITE));
            
            Abator abator = new Abator(config, callback, warnings);
            
            abator.generate(null, contexts, fullyqualifiedTables);
            
        } catch (XMLParserException e) {
        	writeLine(Messages.getString("Progress.3")); //$NON-NLS-1$
        	writeLine();
            List errors = e.getErrors();
            Iterator iter = errors.iterator();
            while (iter.hasNext()) {
                writeLine((String) iter.next());
            }
            
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
            ;
        }
        
        Iterator iter = warnings.iterator();
        while (iter.hasNext()) {
            writeLine((String) iter.next());
        }
        
        if (warnings.size() == 0) {
        	writeLine(Messages.getString("Progress.4")); //$NON-NLS-1$
        } else {
        	writeLine();
        	writeLine(Messages.getString("Progress.5")); //$NON-NLS-1$
        }
	}
	
	private static void usage() {
        String lines = Messages.getString("Usage.Lines"); //$NON-NLS-1$
        int iLines = Integer.parseInt(lines);
        for (int i = 0; i < iLines; i++) {
            String key = "Usage." + i; //$NON-NLS-1$
            writeLine(Messages.getString(key));
        }
	}
	
	private static void writeLine(String message) {
		System.out.println(message);
	}

	private static void writeLine() {
		System.out.println();
	}
    
    private static Map parseCommandLine(String[] args) {
        if (args.length == 0) {
            usage();
            System.exit(0);
            return null;  // only to satisfy compiler, never returns
        } else if (args.length == 2) {
            if (args[0].startsWith(ARGUMENT_PREFIX)) {
                return parseNewCommandLine(args);
            } else {
                return parseOldCommandLine(args);
            }
        } else {
            return parseNewCommandLine(args);
        }
    }
    
    private static Map parseOldCommandLine(String[] args) {
        Map arguments = new HashMap();
        arguments.put(CONFIG_FILE, args[0]);
        if ("true".equalsIgnoreCase(args[1])) { //$NON-NLS-1$
            arguments.put(OVERWRITE, "Y"); //$NON-NLS-1$
        }
        
        return arguments;
    }
    
    private static Map parseNewCommandLine(String[] args) {
        List errors = new ArrayList();
        Map arguments = new HashMap();
        
        for (int i = 0; i < args.length; i++) {
            if (CONFIG_FILE.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONFIG_FILE, args[i + 1]);
                } else {
                    errors.add(Messages.getString("RuntimeError.19", CONFIG_FILE)); //$NON-NLS-1$
                }
                i++;
            } else if (OVERWRITE.equalsIgnoreCase(args[i])) {
                arguments.put(OVERWRITE, "Y"); //$NON-NLS-1$
            } else if (CONTEXT_IDS.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONTEXT_IDS, args[i + 1]);
                } else {
                    errors.add(Messages.getString("RuntimeError.19", CONTEXT_IDS)); //$NON-NLS-1$
                }
                i++;
            } else if (TABLES.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(TABLES, args[i + 1]);
                } else {
                    errors.add(Messages.getString("RuntimeError.19", TABLES)); //$NON-NLS-1$
                }
                i++;
            } else {
                errors.add(Messages.getString("RuntimeError.20", args[i])); //$NON-NLS-1$
            }
        }
        
        if (!errors.isEmpty()) {
            for (int i = 0; i < errors.size(); i++) {
                writeLine((String) errors.get(i));
            }
            
            System.exit(-1);
        }
        
        return arguments;
    }
}
