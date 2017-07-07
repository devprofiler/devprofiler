
package com.devprofiler.utils;

public class Configuration {
    private static final String devprofiler_dev_pu = "devprofiler_dev_pu";
    private static final String devprofiler_pu = "devprofiler_pu";
    private static final String os = System.getProperty("os.name").toLowerCase();
    public static String getPU(){
        if(os.contains("linux")){
                  return devprofiler_pu;
        }
        return devprofiler_dev_pu;
 
    }
    
}
